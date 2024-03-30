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

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface IEnchantment {
	
	static @NotNull EnchantedItem merge(@NotNull ItemStack left, @NotNull ItemStack right) {
		ItemStack result = left.copy();
		if (right.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
			Enchantment enchantment = goldenBook.getEnchantment(right);
			if (enchantment instanceof IEnchantment ench) {
				int level = result.getEnchantmentLevel(enchantment);
				GoldenEnchantmentInstance instance = ench.createGoldenInstance(level + 1);
				if (!XSEnchantmentHelper.hasEnchantment(enchantment, result)) {
					if (XSEnchantmentHelper.isEnchantmentCompatible(result, enchantment)) {
						XSEnchantmentHelper.addEnchantment(new EnchantmentInstance(enchantment, 1), result, false);
						return new EnchantedItem(result, 10);
					}
					return EnchantedItem.EMPTY;
				} else if (instance.isGolden() && instance.level > enchantment.getMaxLevel() && level >= enchantment.getMaxLevel()) {
					if (ench.getMaxGoldenBookLevel() > level) {
						XSEnchantmentHelper.replaceEnchantment(instance, result);
						return new EnchantedItem(result, ench.getGoldenCost(level));
					}
					return EnchantedItem.EMPTY;
				} else {
					XSEnchantmentHelper.increaseEnchantment(enchantment, result, false);
					return new EnchantedItem(result, 10);
				}
			}
			XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			return EnchantedItem.EMPTY;
		}
		XSurvive.LOGGER.error("Can not merge '{}' with '{}', since the right Item must be a instance of EnchantedGoldenBookItem", ForgeRegistries.ITEMS.getKey(left.getItem()), ForgeRegistries.ITEMS.getKey(left.getItem()));
		return EnchantedItem.EMPTY;
	}
	
	static @NotNull EnchantedItem upgrade(@NotNull ItemStack left, @NotNull ItemStack right) {
		ItemStack result = left.copy();
		if (right.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
			Enchantment enchantment = goldenBook.getEnchantment(right);
			if (enchantment instanceof IEnchantment ench) {
				int level = result.getEnchantmentLevel(enchantment);
				if (ench.isUpgradeEnchantment() && ench.getMaxUpgradeLevel() > level) {
					XSEnchantmentHelper.increaseEnchantment(enchantment, result, false);
					return new EnchantedItem(result, ench.getUpgradeCost());
				} else {
					return merge(left, right);
				}
			}
			XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			return EnchantedItem.EMPTY;
		}
		XSurvive.LOGGER.error("Can not upgrade '{}' with '{}', since the right Item must be a instance of EnchantedGoldenBookItem", ForgeRegistries.ITEMS.getKey(left.getItem()), ForgeRegistries.ITEMS.getKey(left.getItem()));
		return EnchantedItem.EMPTY;
	}
	
	private @NotNull Enchantment self() {
		return (Enchantment) this;
	}
	
	boolean isAllowedOnGoldenBooks();
	
	default int getMinGoldenBookLevel() {
		return this.self().getMaxLevel() + 1;
	}
	
	default int getMaxGoldenBookLevel() {
		return this.getMinGoldenBookLevel();
	}
	
	default boolean isGoldenEnchantment() {
		return this.isAllowedOnGoldenBooks() && this.getMinGoldenBookLevel() > this.self().getMaxLevel() && this.getMaxGoldenBookLevel() > this.self().getMaxLevel();
	}
	
	default boolean isGoldenLevel(int level) {
		if (!this.isGoldenEnchantment()) {
			return false;
		}
		return this.getMaxGoldenBookLevel() >= level && level >= this.getMinGoldenBookLevel();
	}
	
	default int getGoldenCost(int level) {
		return Math.max(0, level - this.self().getMaxLevel()) * 10 + 50;
	}
	
	default int getMinUpgradeLevel() {
		return -1;
	}
	
	default int getMaxUpgradeLevel() {
		return -1;
	}
	
	default boolean isUpgradeEnchantment() {
		return this.isAllowedOnGoldenBooks() && this.getMinUpgradeLevel() > 0 && this.getMaxUpgradeLevel() > 0;
	}
	
	default int getUpgradeCost() {
		return 30;
	}
	
	default @NotNull GoldenEnchantmentInstance createGoldenInstance(int level) {
		if (this.getMinGoldenBookLevel() > level) {
			return new GoldenEnchantmentInstance(this.self(), this.getMinGoldenBookLevel());
		} else if (level > this.getMaxGoldenBookLevel()) {
			return new GoldenEnchantmentInstance(this.self(), this.getMaxGoldenBookLevel());
		}
		return new GoldenEnchantmentInstance(this.self(), level);
	}
}
