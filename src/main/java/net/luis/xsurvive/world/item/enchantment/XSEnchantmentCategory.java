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
import net.luis.xsurvive.XSurvive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSEnchantmentCategory {
	
	public static final EnchantmentCategory HOE = EnchantmentCategory.create(new ResourceLocation(XSurvive.MOD_ID, "hoe").toString(), (item) -> {
		return item instanceof HoeItem;
	});
	public static final EnchantmentCategory TOOLS = EnchantmentCategory.create(new ResourceLocation(XSurvive.MOD_ID, "tools").toString(), (item) -> {
		return item instanceof TieredItem;
	});
	public static final EnchantmentCategory WEAPONS = EnchantmentCategory.create(new ResourceLocation(XSurvive.MOD_ID, "weapons").toString(), (item) -> {
		return item instanceof SwordItem || item instanceof BowItem || item instanceof CrossbowItem;
	});
	public static final EnchantmentCategory TOOLS_AND_WEAPONS = EnchantmentCategory.create(new ResourceLocation(XSurvive.MOD_ID, "tools_and_weapons").toString(), (item) -> {
		return TOOLS.canEnchant(item) || WEAPONS.canEnchant(item);
	});
	public static final EnchantmentCategory ELYTRA = EnchantmentCategory.create(new ResourceLocation(XSurvive.MOD_ID, "elytra").toString(), (item) -> {
		return item instanceof ElytraItem || item instanceof ElytraChestplateItem;
	});
}
