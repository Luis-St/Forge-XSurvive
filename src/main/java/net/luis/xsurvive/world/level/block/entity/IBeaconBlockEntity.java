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

package net.luis.xsurvive.world.level.block.entity;

import com.google.common.collect.Sets;
import net.luis.xsurvive.world.level.ILevel;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public interface IBeaconBlockEntity {
	
	@SuppressWarnings("deprecation")
	static int getAmplifier(@NotNull BlockPos playerPos, @NotNull Level level, @NotNull BlockPos current, int beaconLevel, int area, Holder<MobEffect> effect, int defaultAmplifier) {
		ILevel iLevel = LevelProvider.get(level);
		List<BlockPos> positions = iLevel.getBeaconPositions(playerPos, area);
		positions.remove(current);
		int amplifier = 0;
		for (BlockPos position : positions) {
			Holder<MobEffect> primary = iLevel.getPrimaryBeaconEffect(position).orElse(null);
			if (level.getBlockEntity(position) instanceof IBeaconBlockEntity beacon && !beacon.isBeaconBaseShared() && primary != null) {
				if ((primary.is(effect) && !beacon.isBaseFullOf(Blocks.NETHERITE_BLOCK)) || beacon.isBaseFullOf(Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK)) {
					amplifier++;
				}
			}
		}
		if (amplifier == 0 || (level.getBlockEntity(current) instanceof IBeaconBlockEntity beacon && beacon.isBeaconBaseShared())) {
			return defaultAmplifier;
		}
		return Mth.clamp(amplifier, 0, beaconLevel);
	}
	
	@SuppressWarnings("deprecation")
	static boolean isEffectStacked(@NotNull BlockPos playerPos, @NotNull Level level, @NotNull BlockPos current, int area, @NotNull Holder<MobEffect> effect) {
		ILevel iLevel = LevelProvider.get(level);
		List<BlockPos> positions = iLevel.getBeaconPositions(playerPos, area);
		positions.remove(current);
		for (BlockPos position : positions) {
			Holder<MobEffect> primary = iLevel.getPrimaryBeaconEffect(position).orElse(null);
			if (level.getBlockEntity(position) instanceof IBeaconBlockEntity beacon && !beacon.isBeaconBaseShared() && primary != null) {
				if ((primary.is(effect) && !beacon.isBaseFullOf(Blocks.NETHERITE_BLOCK)) || beacon.isBaseFullOf(Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK)) {
					return true;
				}
			}
		}
		return false;
	}
	
	int getBeaconLevel();
	
	@NotNull List<AABB> getBeaconBase();
	
	boolean isBeaconBaseShared();
	
	@NotNull List<Block> getBeaconBaseBlocks();
	
	default boolean isBaseFullOf(@NotNull Block block) {
		List<Block> blocks = this.getBeaconBaseBlocks();
		return blocks.size() == 1 && blocks.getFirst() == block;
	}
	
	default boolean isBaseFullOf(@NotNull Block block, Block @NotNull ... allowed) {
		List<Block> blocks = this.getBeaconBaseBlocks();
		if (blocks.size() == 1 && blocks.getFirst() == block) {
			return true;
		}
		HashSet<Block> allowedBlocks = Sets.newHashSet(allowed);
		allowedBlocks.add(block);
		for (Block toCheck : blocks) {
			if (!allowedBlocks.contains(toCheck)) {
				return false;
			}
		}
		return true;
	}
}
