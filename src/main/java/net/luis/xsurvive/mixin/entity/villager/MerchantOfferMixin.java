package net.luis.xsurvive.mixin.entity.villager;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(MerchantOffer.class)
public abstract class MerchantOfferMixin {
	
	//region Mixin
	@Shadow private ItemStack baseCostA;
	@Shadow private int uses;
	@Shadow private int maxUses;
	@Shadow private int specialPriceDiff;
	@Shadow private int demand;
	@Shadow private float priceMultiplier;
	@Shadow private int xp;
	
	@Shadow
	public abstract int getMaxUses();
	//endregion
	
	@Inject(method = "getCostA", at = @At("TAIL"), cancellable = true)
	public void getCostA(@NotNull CallbackInfoReturnable<ItemStack> callback) {
		int baseCount = this.baseCostA.getCount();
		ItemStack stack = this.baseCostA.copy();
		int demandCount = (int) Math.max(0, (double) (baseCount * this.demand) * this.priceMultiplier);
		int count = Mth.clamp(baseCount + demandCount + this.specialPriceDiff, (int) (baseCount * 0.85), (int) (baseCount * 1.2));
		stack.setCount(Mth.clamp(count, 1, this.baseCostA.getMaxStackSize()));
		callback.setReturnValue(stack);
	}
	
	@Inject(method = "getMaxUses", at = @At("HEAD"), cancellable = true)
	public void getMaxUses(CallbackInfoReturnable<Integer> callback) {
		if (this.maxUses != 1) {
			callback.setReturnValue(this.maxUses * 5);
		}
	}
	
	@Inject(method = "isOutOfStock", at = @At("HEAD"), cancellable = true)
	public void isOutOfStock(@NotNull CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(this.uses >= this.getMaxUses());
	}
	
	@Inject(method = "setToOutOfStock", at = @At("HEAD"), cancellable = true)
	public void setToOutOfStock(@NotNull CallbackInfo callback) {
		this.uses = this.getMaxUses();
		callback.cancel();
	}
}
