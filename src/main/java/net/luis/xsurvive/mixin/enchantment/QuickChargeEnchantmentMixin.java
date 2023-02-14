package net.luis.xsurvive.mixin.enchantment;

import net.minecraft.world.item.enchantment.QuickChargeEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(QuickChargeEnchantment.class)
public abstract class QuickChargeEnchantmentMixin {
	
	@Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
	public void getMaxLevel(CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(4);
	}
	
}
