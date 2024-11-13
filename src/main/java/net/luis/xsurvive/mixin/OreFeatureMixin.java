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

package net.luis.xsurvive.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(OreFeature.class)
public abstract class OreFeatureMixin extends Feature<OreConfiguration> {
	
	//region Mixin
	private OreFeatureMixin(@NotNull Codec<OreConfiguration> codec) {
		super(codec);
	}
	
	@Shadow
	private static boolean shouldSkipAirCheck(@NotNull RandomSource rng, float chance) {
		return false;
	}
	//endregion
	
	@Inject(method = "canPlaceOre", at = @At("HEAD"), cancellable = true)
	private static void canPlaceOre(@NotNull BlockState blockState, @NotNull Function<BlockPos, BlockState> function, @NotNull RandomSource rng, @NotNull OreConfiguration config, @NotNull TargetBlockState targetState, @NotNull MutableBlockPos pos, CallbackInfoReturnable<Boolean> callback) {
		if (!targetState.target.test(blockState, rng)) {
			callback.setReturnValue(false);
		} else if (shouldSkipAirCheck(rng, config.discardChanceOnAirExposure)) {
			callback.setReturnValue(true);
		} else {
			callback.setReturnValue(!checkNeighbors(function, pos.immutable(), state -> state.isAir() || state.getBlock() instanceof LiquidBlock));
		}
	}
}
