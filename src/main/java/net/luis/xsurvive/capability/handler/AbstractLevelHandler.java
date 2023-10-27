package net.luis.xsurvive.capability.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.world.level.ILevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static net.minecraft.core.registries.BuiltInRegistries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("deprecation")
public abstract class AbstractLevelHandler implements ILevel {
	
	private final Level level;
	protected final List<BlockPos> beaconPositions = Lists.newArrayList();
	protected final Map<BlockPos, Pair<Integer, Integer>> beaconEffects = Maps.newHashMap();
	
	protected AbstractLevelHandler(Level level) {
		this.level = level;
	}
	
	@Override
	public @NotNull Level getLevel() {
		return this.level;
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area) {
		List<BlockPos> positions = Lists.newArrayList();
		for (BlockPos position : this.beaconPositions) {
			if (area.contains(position.getX(), position.getY(), position.getZ())) {
				positions.add(position);
			}
		}
		return positions;
	}
	
	@Override
	public @NotNull Pair<@Nullable MobEffect, @Nullable MobEffect> getBeaconEffects(@NotNull BlockPos pos) {
		if (!this.beaconEffects.containsKey(pos)) {
			return new Pair<>(null, null);
		}
		Pair<Integer, Integer> effects = this.beaconEffects.get(pos);
		return new Pair<>(MOB_EFFECT.byId(effects.getFirst()), MOB_EFFECT.byId(effects.getSecond()));
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		CompoundTag tag = new CompoundTag();
		ListTag beaconPositions = new ListTag();
		for (BlockPos beaconPosition : this.beaconPositions) {
			beaconPositions.add(NbtUtils.writeBlockPos(beaconPosition));
		}
		tag.put("beacon_positions", beaconPositions);
		ListTag beaconEffects = new ListTag();
		for (Map.Entry<BlockPos, Pair<Integer, Integer>> beaconEffect : this.beaconEffects.entrySet()) {
			CompoundTag beaconEffectTag = new CompoundTag();
			beaconEffectTag.put("pos", NbtUtils.writeBlockPos(beaconEffect.getKey()));
			beaconEffectTag.putInt("primary_effect", beaconEffect.getValue().getFirst());
			beaconEffectTag.putInt("secondary_effect", beaconEffect.getValue().getSecond());
			beaconEffects.add(beaconEffectTag);
		}
		tag.put("beacon_effects", beaconEffects);
		return tag;
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		this.beaconPositions.clear();
		ListTag beaconPositions = tag.getList("beacon_positions", Tag.TAG_COMPOUND);
		for (Tag beaconPosition : beaconPositions) {
			this.beaconPositions.add(NbtUtils.readBlockPos((CompoundTag) beaconPosition));
		}
		this.beaconEffects.clear();
		ListTag beaconEffects = tag.getList("beacon_effects", Tag.TAG_COMPOUND);
		for (Tag beaconEffect : beaconEffects) {
			CompoundTag beaconEffectTag = (CompoundTag) beaconEffect;
			this.beaconEffects.put(NbtUtils.readBlockPos(beaconEffectTag.getCompound("pos")), new Pair<>(beaconEffectTag.getInt("primary_effect"), beaconEffectTag.getInt("secondary_effect")));
		}
	}
	//endregion
}
