package net.luis.xsurvive.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
	
	@Inject(method = "getState", at = @At("HEAD"), cancellable = true)
	private static void getState(BlockGetter blockGetter, BlockPos pos, CallbackInfoReturnable<BlockState> callback) {
		if (XSBlocks.MYSTIC_FIRE.get().canSurvive(blockGetter, pos)) {
			callback.setReturnValue(XSBlocks.MYSTIC_FIRE.get().getStateForPlacement(blockGetter, pos));
		}
	}
	
}
