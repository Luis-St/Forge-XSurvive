package net.luis.xsurvive.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.level.block.MysticFireBlock;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
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
	
	@Inject(method = "entityInside", at = @At("TAIL"))
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo callback) {
		if (!level.isClientSide) {
			EntityProvider.getSafe(entity).ifPresent((handler) -> {
				if (state.getBlock() instanceof SoulFireBlock) {
					handler.setFireType(EntityFireType.SOUL_FIRE);
					handler.broadcastChanges();
				} else if (state.getBlock() instanceof MysticFireBlock) {
					handler.setFireType(EntityFireType.MYSTIC_FIRE);
					handler.broadcastChanges();
				} else {
					handler.setFireType(EntityFireType.FIRE);
					handler.broadcastChanges();
				}
			});
		}
	}
	
}
