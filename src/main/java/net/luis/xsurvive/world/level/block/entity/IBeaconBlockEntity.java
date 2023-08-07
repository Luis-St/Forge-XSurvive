package net.luis.xsurvive.world.level.block.entity;

import com.google.common.collect.Sets;
import net.luis.xsurvive.world.level.ILevel;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public interface IBeaconBlockEntity {
	
	static int getAmplifier(BlockPos playerPos, Level level, BlockPos current, int beaconLevel, int area, MobEffect effect, int defaultAmplifier) {
		ILevel iLevel = LevelProvider.get(level);
		List<BlockPos> positions = iLevel.getBeaconPositions(playerPos, area);
		positions.remove(current);
		int amplifier = 0;
		for (BlockPos position : positions) {
			MobEffect primary = iLevel.getPrimaryBeaconEffect(position).orElse(null);
			if (level.getBlockEntity(position) instanceof IBeaconBlockEntity beacon && !beacon.isBeaconBaseShared() && primary != null) {
				if ((primary == effect && !beacon.isBaseFullOf(Blocks.NETHERITE_BLOCK)) || beacon.isBaseFullOf(Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK)) {
					amplifier++;
				}
			}
		}
		if (amplifier == 0 || (level.getBlockEntity(current) instanceof IBeaconBlockEntity beacon && beacon.isBeaconBaseShared())) {
			return defaultAmplifier;
		}
		return Mth.clamp(amplifier, 0, beaconLevel);
	}
	
	static boolean isEffectStacked(BlockPos playerPos, Level level, BlockPos current, int beaconLevel, int area, MobEffect effect) {
		ILevel iLevel = LevelProvider.get(level);
		List<BlockPos> positions = iLevel.getBeaconPositions(playerPos, area);
		positions.remove(current);
		for (BlockPos position : positions) {
			MobEffect primary = iLevel.getPrimaryBeaconEffect(position).orElse(null);
			if (level.getBlockEntity(position) instanceof IBeaconBlockEntity beacon && !beacon.isBeaconBaseShared() && primary != null) {
				if ((primary == effect && !beacon.isBaseFullOf(Blocks.NETHERITE_BLOCK)) || beacon.isBaseFullOf(Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK)) {
					return true;
				}
			}
		}
		return false;
	}
	
	int getBeaconLevel();
	
	List<AABB> getBeaconBase();
	
	boolean isBeaconBaseShared();
	
	List<Block> getBeaconBaseBlocks();
	
	default boolean isBaseFullOf(Block block) {
		List<Block> blocks = this.getBeaconBaseBlocks();
		return blocks.size() == 1 && blocks.get(0) == block;
	}
	
	default boolean isBaseFullOf(Block block, Block... allowed) {
		List<Block> blocks = this.getBeaconBaseBlocks();
		if (blocks.size() == 1 && blocks.get(0) == block) {
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
