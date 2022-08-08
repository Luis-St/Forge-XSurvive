package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.enchantment.ArrowKnockbackEnchantment;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(ArrowKnockbackEnchantment.class)
public abstract class ArrowKnockbackEnchantmentMixin {
	
	@Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
	public void getMaxLevel(CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(3);
	}
	
}
