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

package net.luis.xsurvive.world.item.enchantment;

import net.luis.xores.world.item.ElytraChestplateItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class VoidProtectionEnchantment extends Enchantment implements IEnchantment {
	
	public VoidProtectionEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
	
	@Override
	public int getMinCost(int level) {
		return 20;
	}
	
	@Override
	public int getMaxCost(int level) {
		return 50;
	}
	
	@Override
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		if (enchantment instanceof ProtectionEnchantment protectionEnchantment) {
			return protectionEnchantment.type == ProtectionEnchantment.Type.ALL;
		} else if (enchantment == Enchantments.THORNS) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean canEnchant(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof ElytraItem) {
			return true;
		} else if (stack.getItem() instanceof ElytraChestplateItem) {
			return true;
		}
		return super.canEnchant(stack);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isDiscoverable() {
		return false;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		return true;
	}
	
	@Override
	public int getMinUpgradeLevel() {
		return this.getMinLevel();
	}
	
	@Override
	public int getMaxUpgradeLevel() {
		return this.getMaxLevel();
	}
}
