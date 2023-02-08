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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(OreFeature.class)
public abstract class OreFeatureMixin extends Feature<OreConfiguration> {
	
	private OreFeatureMixin(Codec<OreConfiguration> codec) {
		super(codec);
	}
	
	@Shadow
	private static boolean shouldSkipAirCheck(RandomSource rng, float chance) {
		return false;
	}
	
	@Inject(method = "canPlaceOre", at = @At("HEAD"), cancellable = true)
	private static void canPlaceOre(BlockState blockState, Function<BlockPos, BlockState> function, RandomSource rng, OreConfiguration config, TargetBlockState targetState, MutableBlockPos pos, CallbackInfoReturnable<Boolean> callback) {
		if (!targetState.target.test(blockState, rng)) {
			callback.setReturnValue(false);
		} else if (shouldSkipAirCheck(rng, config.discardChanceOnAirExposure)) {
			callback.setReturnValue(true);
		} else {
			callback.setReturnValue(!checkNeighbors(function, pos.immutable(), (state) -> {
				return state.isAir() || state.getBlock() instanceof LiquidBlock;
			}));
		}
	}
	
}
