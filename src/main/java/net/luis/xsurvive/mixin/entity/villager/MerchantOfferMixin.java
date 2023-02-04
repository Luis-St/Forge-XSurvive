package net.luis.xsurvive.mixin.entity.villager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.trading.MerchantOffer;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin {
	
	@Shadow
	private int uses;
	@Shadow
	private int maxUses;
	@Shadow
	private int xp;
	
	@Shadow
	public abstract int getMaxUses();
	
	@Inject(method = "getMaxUses", at = @At("HEAD"), cancellable = true)
	public void getMaxUses(CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(/*this.maxUses * 5*/Integer.MAX_VALUE); // testing only
	} // TODO
	
	@Inject(method = "getXp", at = @At("HEAD"), cancellable = true)
	public void getXp(CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(/*Math.max(1, (int) (this.xp * 0.5))*/Integer.MAX_VALUE); // testing only
	} // TODO
	
	@Inject(method = "isOutOfStock", at = @At("HEAD"), cancellable = true)
	public void isOutOfStock(CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(this.uses >= this.getMaxUses());
	}
	
	@Inject(method = "setToOutOfStock", at = @At("HEAD"), cancellable = true)
	public void setToOutOfStock(CallbackInfo callback) {
		this.uses = this.getMaxUses();
		callback.cancel();
	}
	
}
