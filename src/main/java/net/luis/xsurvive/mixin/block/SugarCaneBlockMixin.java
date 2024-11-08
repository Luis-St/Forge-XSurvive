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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.SugarCaneBlock.*;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(SugarCaneBlock.class)
@SuppressWarnings({ "DataFlowIssue", "UnstableApiUsage" })
public abstract class SugarCaneBlockMixin extends Block implements BonemealableBlock {
	
	//region Mixin
	private SugarCaneBlockMixin(@NotNull Properties properties) {
		super(properties);
	}
	//endregion
	
	@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rng, @NotNull CallbackInfo callback) {
		if (level.isEmptyBlock(pos.above())) {
			int height;
			for (height = 1; level.getBlockState(pos.below(height)).is((SugarCaneBlock) (Object) this); ++height) ;
			if (height < 5) {
				int j = state.getValue(AGE);
				if (ForgeHooks.onCropsGrowPre(level, pos, state, true)) {
					if (j == 15) {
						level.setBlockAndUpdate(pos.above(), this.defaultBlockState());
						ForgeHooks.onCropsGrowPost(level, pos.above(), this.defaultBlockState());
						level.setBlock(pos, state.setValue(AGE, 0), 4);
					} else {
						level.setBlock(pos, state.setValue(AGE, j + 1), 4);
					}
				}
			}
			callback.cancel();
		}
	}
	
	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
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
