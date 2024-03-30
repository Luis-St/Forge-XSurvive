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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-St
 *
 */

public class GoldenEnchantmentInstance extends EnchantmentInstance {
	
	public GoldenEnchantmentInstance(Enchantment enchantment, int level) {
		super(enchantment, level);
	}
	
	public boolean isGolden() {
		if (this.enchantment instanceof IEnchantment ench) {
			return ench.isAllowedOnGoldenBooks() && ench.isGoldenLevel(this.level);
		}
		XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(this.enchantment));
		return false;
	}
}
