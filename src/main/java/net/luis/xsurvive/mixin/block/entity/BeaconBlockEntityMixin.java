package net.luis.xsurvive.mixin.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity {
	
	private BeaconBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void init(CallbackInfo callback) {
	
	}
	
	@Inject(method = "setRemoved", at = @At("HEAD"))
	public void setRemoved(CallbackInfo callback) {
	
	}
	
}
