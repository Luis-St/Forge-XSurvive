package net.luis.xsurvive.mixin.block;

import net.luis.xsurvive.XSurvive;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPattern.BlockPatternMatch;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(WitherSkullBlock.class)
public abstract class WitherSkullBlockMixin {
	
	@Shadow
	private static BlockPattern getOrCreateWitherFull() {
		return null;
	}
	
	@Inject(method = "checkSpawn", at = @At("HEAD"), cancellable = true)
	private static void checkSpawn(Level level, BlockPos pos, SkullBlockEntity blockEntity, CallbackInfo callback) {
		if (!level.isClientSide) {
			BlockState state = blockEntity.getBlockState();
			boolean witherSkull = state.is(Blocks.WITHER_SKELETON_SKULL) || state.is(Blocks.WITHER_SKELETON_WALL_SKULL);
			if (witherSkull && pos.getY() >= level.getMinBuildHeight() && level.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern pattern = Objects.requireNonNull(getOrCreateWitherFull());
				BlockPatternMatch patternMatch = pattern.find(level, pos);
				if (patternMatch != null) {
					if (level.getBiome(pos).is(Biomes.THE_END)) {
						XSurvive.LOGGER.debug("Can not spawn the wither in the end biome");
					} else if (level.getBiome(pos).is(BiomeTags.IS_NETHER) && !checkNetherSpawn(pos)) {
						XSurvive.LOGGER.debug("Can not spawn the wither in the nether at height {}", pos.getY());
					} else if (level.getBiome(pos).is(BiomeTags.IS_OVERWORLD) && -59 > pos.getY()) {
						XSurvive.LOGGER.debug("Can not spawn the wither in the overworld at height {}", pos.getY());
					} else if (checkSpawnArea(level, patternMatch)) {
						XSurvive.LOGGER.debug("Can not spawn the wither at position {}, since there is not enough space around", patternMatch.getBlock(1, 1, 0).getPos().toShortString());
					} else {
						for (int width = 0; width < pattern.getWidth(); ++width) {
							for (int height = 0; height < pattern.getHeight(); ++height) {
								BlockInWorld inWorld = patternMatch.getBlock(width, height, 0);
								level.setBlock(inWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
								level.levelEvent(2001, inWorld.getPos(), Block.getId(inWorld.getState()));
							}
						}
						WitherBoss wither = Objects.requireNonNull(EntityType.WITHER.create(level));
						BlockPos spawnPos = patternMatch.getBlock(1, 2, 0).getPos();
						wither.moveTo((double) spawnPos.getX() + 0.5, spawnPos.getY() + 0.55, spawnPos.getZ() + 0.5, patternMatch.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
						wither.yBodyRot = patternMatch.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
						wither.makeInvulnerable();
						for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, wither.getBoundingBox().inflate(75.0))) {
							CriteriaTriggers.SUMMONED_ENTITY.trigger(player, wither);
						}
						level.addFreshEntity(wither);
						for (int width = 0; width < pattern.getWidth(); ++width) {
							for (int height = 0; height < pattern.getHeight(); ++height) {
								level.blockUpdated(patternMatch.getBlock(width, height, 0).getPos(), Blocks.AIR);
							}
						}
					}
				}
			}
		}
		callback.cancel();
	}
	
	private static boolean checkNetherSpawn(BlockPos pos) {
		if (pos.getY() > 128) {
			return true;
		} else if (128 >= pos.getY() && pos.getY() >= 121) {
			return false;
		} else {
			return 5 <= pos.getY();
		}
	}
	
	private static boolean checkSpawnArea(Level level, BlockPatternMatch patternMatch) {
		BlockPos pos = patternMatch.getBlock(1, 1, 0).getPos();
		return level.getBlockStates(new AABB(pos.below().south().west(), pos.above().north().east())).anyMatch((state) -> {
			return !state.isAir() && !state.is(Blocks.WITHER_SKELETON_SKULL) && !state.is(Blocks.WITHER_SKELETON_WALL_SKULL) && !state.is(BlockTags.WITHER_SUMMON_BASE_BLOCKS);
		});
	}
}
