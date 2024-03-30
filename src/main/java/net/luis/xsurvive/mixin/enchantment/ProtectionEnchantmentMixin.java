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

package net.luis.xsurvive.mixin.enchantment;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {
	
	//region Mixin
	@Shadow public ProtectionEnchantment.Type type;
	
	private ProtectionEnchantmentMixin(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	//endregion
	
	@Override
	public boolean canEnchant(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof ElytraItem) {
			return this.type != ProtectionEnchantment.Type.FALL;
		}
		return super.canEnchant(stack);
	}
	
	@Inject(method = "checkCompatibility", at = @At("HEAD"), cancellable = true)
	public void checkCompatibility(@NotNull Enchantment enchantment, @NotNull CallbackInfoReturnable<Boolean> callback) {
		if (enchantment instanceof ProtectionEnchantment protection) {
			if (this.type == protection.type) {
				callback.setReturnValue(false);
			} else if (this.type == ProtectionEnchantment.Type.FALL || protection.type == ProtectionEnchantment.Type.FALL) {
				callback.setReturnValue(true);
			} else {
				callback.setReturnValue(this.type == ProtectionEnchantment.Type.ALL || protection.type == ProtectionEnchantment.Type.ALL);
			}
		} else {
			callback.setReturnValue(super.checkCompatibility(enchantment));
		}
	}
	
	@Inject(method = "getFireAfterDampener", at = @At("HEAD"), cancellable = true)
	private static void getFireAfterDampener(@NotNull LivingEntity entity, int fireTicks, @NotNull CallbackInfoReturnable<Integer> callback) {
		Pair<Integer, Integer> pair = XSEnchantmentHelper.getTotalEnchantmentLevel(entity, Enchantments.FIRE_PROTECTION);
		double total = pair.getFirst();
		double items = pair.getSecond();
		if (items > 0.0) {
			if (total > 16.0) {
				callback.setReturnValue(switch ((int) total) {
					case 17 -> 35;
					case 18 -> 23;
					case 19 -> 11;
					case 20 -> 0;
					default -> fireTicks - Mth.floor(fireTicks * (total / items) * 0.2);
				});
			} else if (total > 0.0) {
				callback.setReturnValue(fireTicks - Mth.floor(fireTicks * (total / items) * 0.2));
			}
		}
	}
	
	@Inject(method = "getExplosionKnockbackAfterDampener", at = @At("HEAD"), cancellable = true)
	private static void getExplosionKnockbackAfterDampener(@NotNull LivingEntity entity, double knockback, @NotNull CallbackInfoReturnable<Double> callback) {
		Pair<Integer, Integer> pair = XSEnchantmentHelper.getTotalEnchantmentLevel(entity, Enchantments.BLAST_PROTECTION);
		double total = pair.getFirst();
		double items = pair.getSecond();
		if (total > 0.0 && items > 0.0) {
			callback.setReturnValue(knockback * Mth.clamp(1.0 - (total / items) * 0.2, 0.0, 1.0));
		}
	}
}
