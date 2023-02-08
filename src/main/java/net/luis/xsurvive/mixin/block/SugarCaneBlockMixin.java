package net.luis.xsurvive.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin implements BonemealableBlock {
	
	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClientSide) {
		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(pos.getX(), this.getStartBlock(level, pos), pos.getZ());
		for (int i = 0; i < 5; i++) {
			if (!level.getBlockState(blockPos.immutable()).is(Blocks.SUGAR_CANE)) {
				return level.getBlockState(blockPos.immutable()).isAir();
			}
			blockPos.move(0, 1, 0);
		}
		return false;
	}
	
	private int getStartBlock(@NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos blockPos = pos.mutable();
		while (level.getBlockState(blockPos).is(Blocks.SUGAR_CANE)) {
			blockPos.move(0, -1, 0);
		}
		return blockPos.immutable().above().getY();
	}
	
	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource rng, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource rng, @NotNull BlockPos pos, @NotNull BlockState state) {
		for (int i = 0; i < 5; i++) {
			BlockPos blockPos = pos.above(i);
			if (level.getBlockState(blockPos.below()).is(Blocks.SUGAR_CANE) && level.getBlockState(blockPos).isAir()) {
				level.setBlock(blockPos, Blocks.SUGAR_CANE.defaultBlockState(), Block.UPDATE_ALL);
				break;
			}
		}
	}
}
