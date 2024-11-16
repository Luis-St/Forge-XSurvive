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

package net.luis.xsurvive.mixin.entity.villager;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
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
	@Shadow private ItemCost baseCostA;
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
		ItemStack stack = this.baseCostA.itemStack();
		int baseCount = stack.getCount();
		int demandCount = (int) Math.max(0, (double) (baseCount * this.demand) * this.priceMultiplier);
		int count = Mth.clamp(baseCount + demandCount + this.specialPriceDiff, (int) (baseCount * 0.85), (int) (baseCount * 1.2));
		int clamped = Mth.clamp(count, 1, stack.getMaxStackSize());
		callback.setReturnValue(stack.copyWithCount(clamped));
	}
	
	@Inject(method = "getMaxUses", at = @At("HEAD"), cancellable = true)
	public void getMaxUses(@NotNull CallbackInfoReturnable<Integer> callback) {
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
