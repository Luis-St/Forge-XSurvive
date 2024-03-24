package net.luis.xsurvive.mixin.block.entity;

import net.luis.xsurvive.server.capability.ServerLevelHandler;
import net.luis.xsurvive.world.level.LevelProvider;
import net.luis.xsurvive.world.level.block.entity.IBeaconBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

import static net.luis.xsurvive.world.level.block.entity.IBeaconBlockEntity.*;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(BeaconBlockEntity.class)
@SuppressWarnings({ "PatternVariableHidesField", "DataFlowIssue" })
public abstract class BeaconBlockEntityMixin extends BlockEntity implements IBeaconBlockEntity {
	
	//region Mixin
	@Shadow private int levels;
	@Shadow private MobEffect primaryPower;
	@Shadow private MobEffect secondaryPower;
	
	private BeaconBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	//endregion
	
	@Inject(method = "applyEffects", at = @At("HEAD"), cancellable = true)
	private static void applyEffects(@NotNull Level level, BlockPos pos, int beaconLevel, @Nullable MobEffect primary, @Nullable MobEffect secondary, CallbackInfo callback) {
		if (level instanceof ServerLevel && primary != null && level.getBlockEntity(pos) instanceof IBeaconBlockEntity beacon) {
			int area = beaconLevel * 20 + 20;
			if (beacon.isBaseFullOf(Blocks.NETHERITE_BLOCK) && !beacon.isBeaconBaseShared()) {
				for (Player player : level.getEntitiesOfClass(Player.class, getArea(level, pos, area * 3))) {
					for (MobEffect effect : getEffects(beaconLevel, primary == MobEffects.JUMP)) {
						player.addEffect(new MobEffectInstance(effect, (10 + beaconLevel * 10) * 20, beaconLevel, true, true));
					}
				}
			} else {
				boolean diamond = beacon.isBaseFullOf(Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK) && !beacon.isBeaconBaseShared();
				List<Player> players = level.getEntitiesOfClass(Player.class, getArea(level, pos, diamond ? area * 2 : area));
				int amplifier = beaconLevel >= 4 && primary == secondary ? 1 : 0;
				for (Player player : players) {
					player.addEffect(new MobEffectInstance(primary, (10 + beaconLevel * 10) * 20, diamond ? beaconLevel : getAmplifier(player.getOnPos(), player.level(), pos, beaconLevel, area, primary, amplifier), true, true));
				}
				if (beaconLevel >= 4 && primary != secondary && secondary != null) {
					for (Player player : players) {
						player.addEffect(new MobEffectInstance(secondary, (10 + beaconLevel * 10) * 20, diamond ? beaconLevel : 0, true, true));
					}
				}
			}
			callback.cancel();
		}
	}
	
	private static @NotNull AABB getArea(@NotNull Level level, BlockPos pos, int area) {
		return new AABB(pos).inflate(area).setMinY(level.getMinBuildHeight()).setMaxY(level.getMaxBuildHeight());
	}
	
	private static @NotNull List<MobEffect> getEffects(int beaconLevel, boolean includeJump) {
		List<MobEffect> effects = switch (beaconLevel) {
			case 1 -> List.of(MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED);
			case 2 -> List.of(MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED, MobEffects.DAMAGE_RESISTANCE);
			case 3 -> List.of(MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED, MobEffects.DAMAGE_RESISTANCE, MobEffects.DAMAGE_BOOST);
			case 4 -> List.of(MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED, MobEffects.DAMAGE_RESISTANCE, MobEffects.DAMAGE_BOOST, MobEffects.REGENERATION);
			default -> List.of();
		};
		if (includeJump && beaconLevel >= 2) {
			effects.add(MobEffects.JUMP);
		}
		return effects;
	}
	
	@Inject(method = "setLevel", at = @At("RETURN"))
	public void setLevel(Level level, CallbackInfo callback) {
		if (level instanceof ServerLevel) {
			ServerLevelHandler handler = LevelProvider.getServer(level);
			handler.addBeaconPosition(this.getBlockPos());
			handler.setBeaconEffects(this.getBlockPos(), this.primaryPower, this.secondaryPower);
		}
	}
	
	@Inject(method = "setRemoved", at = @At("HEAD"))
	public void setRemoved(CallbackInfo callback) {
		if (this.getLevel() instanceof ServerLevel level) {
			ServerLevelHandler handler = LevelProvider.getServer(level);
			handler.removeBeaconPosition(this.getBlockPos());
			handler.removeBeaconEffects(this.getBlockPos());
		}
	}
	
	@Override
	public int getBeaconLevel() {
		return this.levels;
	}
	
	@Override
	public List<AABB> getBeaconBase() {
		List<AABB> base = new ArrayList<>();
		AABB area = new AABB(Vec3.atLowerCornerOf(this.getBlockPos()), Vec3.atLowerCornerOf(this.getBlockPos()));
		for (int i = 0; i < this.levels; i++) {
			base.add(area.move(0, -(i + 1), 0).inflate(i + 1, 0, i + 1));
		}
		return base;
	}
	
	@Override
	public List<Block> getBeaconBaseBlocks() {
		HashSet<Block> blocks = new HashSet<>();
		List<AABB> base = this.getBeaconBase();
		for (AABB basePart : base) {
			this.level.getBlockStates(basePart).filter(state -> state.is(BlockTags.BEACON_BASE_BLOCKS)).map(BlockState::getBlock).forEach(blocks::add);
		}
		return new ArrayList<>(blocks);
	}
	
	@Override
	public boolean isBeaconBaseShared() {
		BlockPos p = this.getBlockPos();
		Stream<BlockPos> positions = Stream.of(BlockPos.betweenClosedStream(new AABB(p.getX() - 7, p.getY() - 1, p.getZ() - 7, p.getX() + 8, p.getY(), p.getZ() + 8)).map(BlockPos::immutable),
			BlockPos.betweenClosedStream(new AABB(p.getX() - 6, p.getY() - 2, p.getZ() - 6, p.getX() + 7, p.getY() - 2, p.getZ() + 7)).map(BlockPos::immutable),
			BlockPos.betweenClosedStream(new AABB(p.getX() - 5, p.getY() - 3, p.getZ() - 5, p.getX() + 6, p.getY() - 3, p.getZ() + 6)).map(BlockPos::immutable)).flatMap(stream -> stream);
		return positions.map(this.level::getBlockState).filter(state -> state.is(Blocks.BEACON)).count() > 1;
	}
}
