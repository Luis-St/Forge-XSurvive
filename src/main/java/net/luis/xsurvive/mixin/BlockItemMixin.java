package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

	private BlockItemMixin(Properties properties) {
		super(properties);
	}
	
	@Inject(method = "canFitInsideContainerItems", at = @At("HEAD"), cancellable = true)
	public void canFitInsideContainerItems(CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(true);
	}
	
}
