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

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(TridentImpalerEnchantment.class)
public abstract class TridentImpalerEnchantmentMixin extends Enchantment {
	
	//region Mixin
	private TridentImpalerEnchantmentMixin(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	//endregion
	
	@Override
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		if (enchantment instanceof DamageEnchantment) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof SwordItem) {
			return true;
		} else if (stack.getItem() instanceof AxeItem) {
			return true;
		} else if (stack.getItem() instanceof TridentItem) {
			return true;
		}
		return super.canApplyAtEnchantingTable(stack);
	}
	
	@Inject(method = "getDamageBonus", at = @At("HEAD"), cancellable = true)
	public void getDamageBonus(int level, MobType mobType, @NotNull CallbackInfoReturnable<Float> callback) {
		callback.setReturnValue(0.0F);
	}
}
