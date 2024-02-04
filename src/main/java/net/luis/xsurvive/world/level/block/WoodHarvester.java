package net.luis.xsurvive.world.level.block;

import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class WoodHarvester {
	
	public static void harvest(@NotNull Level level, @NotNull BlockPos startPos, @NotNull Block block, int harvesting, @NotNull Player player) {
		List<BlockPos> positions = BlockPos.betweenClosedStream(startPos.north().east(), startPos.south().west()).map(WoodHarvester::immutable).filter(pos -> isTreePosition(level, pos, block)).toList();
		int height = getTreeHeight(level, positions, block, harvesting);
		if (height != 0) {
			AABB aabb = getHarvestingArea(startPos, positions, height);
			List<BlockPos> harvestingPositions = BlockPos.betweenClosedStream(aabb).map(WoodHarvester::immutable).filter(pos -> isTreePosition(level, pos, block)).toList();
			for (BlockPos pos : harvestingPositions) {
				BlockState state = level.getBlockState(pos);
				level.destroyBlock(pos, false);
				Block.dropResources(state, level, pos, state.hasBlockEntity() ? level.getBlockEntity(pos) : null, player, getUsedStack(player));
			}
		}
	}
	
	private static int getTreeHeight(@NotNull Level level, @NotNull List<BlockPos> positions, @NotNull Block block, int harvesting) {
		int height = 0;
		for (BlockPos pos : positions) {
			height = Math.max(getTreePositionHeight(level, pos, block), height);
		}
		return Math.min(harvesting * 4 + (harvesting / 2), height);
	}
	
	private static int getTreePositionHeight(@NotNull Level level, @NotNull BlockPos pos, @NotNull Block block) {
		int height = 0;
		for (int y = 0; y <= 256; y++) {
			if (isTreePosition(level, pos.above(y), block)) {
				++height;
			} else {
				break;
			}
		}
		return height;
	}
	
	private static @NotNull AABB getHarvestingArea(@NotNull BlockPos startPos, @NotNull List<BlockPos> positions, int height) {
		AABB aabb;
		if (positions.size() == 1) {
			aabb = new AABB(Vec3.atLowerCornerOf(startPos.north().east()), Vec3.atLowerCornerOf(startPos.south().west()));
		} else if (positions.size() == 4) {
			aabb = new AABB(Vec3.atLowerCornerOf(startPos.north(1).east(1)), Vec3.atLowerCornerOf(startPos.south(1).west(1)));
		} else {
			aabb = new AABB(Vec3.atLowerCornerOf(startPos.north(2).east(2)), Vec3.atLowerCornerOf(startPos.south(2).west(2)));
		}
		int y = startPos.getY();
		return aabb.setMinY(y - (height / 2.0)).setMaxY(y + height);
	}
	
	private static @NotNull ItemStack getUsedStack(@NotNull Player player) {
		ItemStack mainStack = player.getMainHandItem();
		ItemStack offStack = player.getOffhandItem();
		if (mainStack.getEnchantmentLevel(XSEnchantments.HARVESTING.get()) > 0) {
			return mainStack;
		} else if (offStack.getEnchantmentLevel(XSEnchantments.HARVESTING.get()) > 0) {
			return offStack;
		} else if (!mainStack.isEmpty()) {
			return mainStack;
		} else if (!offStack.isEmpty()) {
			return offStack;
		}
		return ItemStack.EMPTY;
	}
	
	private static boolean isTreePosition(@NotNull Level level, @NotNull BlockPos pos, @NotNull Block block) {
		BlockState state = level.getBlockState(pos);
		return state.is(BlockTags.LOGS) || state.is(block);
	}
	
	private static @NotNull BlockPos immutable(@NotNull BlockPos pos) {
		if (pos instanceof MutableBlockPos mutablePos) {
			return mutablePos.immutable();
		}
		return pos;
	}
}
