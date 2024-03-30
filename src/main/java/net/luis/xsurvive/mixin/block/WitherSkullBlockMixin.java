/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(WitherSkullBlock.class)
@SuppressWarnings({ "CodeBlock2Expr", "ReturnOfNull", "DataFlowIssue" })
public abstract class WitherSkullBlockMixin {
	
	//region Mixin
	@Shadow
	private static @NotNull BlockPattern getOrCreateWitherFull() {
		return null;
	}
	//endregion
	
	@Inject(method = "checkSpawn", at = @At("HEAD"), cancellable = true)
	private static void checkSpawn(@NotNull Level level, BlockPos pos, SkullBlockEntity blockEntity, CallbackInfo callback) {
		if (!level.isClientSide) {
			BlockState state = blockEntity.getBlockState();
			boolean witherSkull = state.is(Blocks.WITHER_SKELETON_SKULL) || state.is(Blocks.WITHER_SKELETON_WALL_SKULL);
			if (witherSkull && pos.getY() >= level.getMinBuildHeight() && level.getDifficulty() != Difficulty.PEACEFUL) {
				BlockPattern pattern = getOrCreateWitherFull();
				BlockPatternMatch patternMatch = pattern.find(level, pos);
				if (patternMatch != null) {
					if (level.getBiome(pos).is(Biomes.THE_END)) {
						XSurvive.LOGGER.info("Can not spawn the wither in the end biome");
					} else if (level.getBiome(pos).is(BiomeTags.IS_NETHER) && !checkNetherSpawn(pos)) {
						XSurvive.LOGGER.info("Can not spawn the wither in the nether at height {}", pos.getY());
					} else if (level.getBiome(pos).is(BiomeTags.IS_OVERWORLD) && -59 > pos.getY()) {
						XSurvive.LOGGER.info("Can not spawn the wither in the overworld at height {}", pos.getY());
					} else if (checkSpawnArea(level, patternMatch)) {
						XSurvive.LOGGER.info("Can not spawn the wither at position {}, since there is not enough space around", patternMatch.getBlock(1, 1, 0).getPos().toShortString());
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
						wither.moveTo(spawnPos.getX() + 0.5, spawnPos.getY() + 0.55, spawnPos.getZ() + 0.5, patternMatch.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F, 0.0F);
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
	
	private static boolean checkNetherSpawn(@NotNull BlockPos pos) {
		if (pos.getY() > 128) {
			return true;
		} else if (128 >= pos.getY() && pos.getY() >= 121) {
			return false;
		} else {
			return 5 <= pos.getY();
		}
	}
	
	private static boolean checkSpawnArea(@NotNull Level level, @NotNull BlockPatternMatch patternMatch) {
		BlockPos pos = patternMatch.getBlock(1, 1, 0).getPos();
		return level.getBlockStates(new AABB(Vec3.atLowerCornerOf(pos.below().south().west()), Vec3.atLowerCornerOf(pos.above().north().east()))).anyMatch((state) -> {
			return !state.isAir() && !state.is(Blocks.WITHER_SKELETON_SKULL) && !state.is(Blocks.WITHER_SKELETON_WALL_SKULL) && !state.is(BlockTags.WITHER_SUMMON_BASE_BLOCKS);
		});
	}
}
