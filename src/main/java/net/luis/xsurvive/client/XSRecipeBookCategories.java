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

package net.luis.xsurvive.client;

import net.luis.xsurvive.XSurvive;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 *
 * @author Luis-St
 *
 */

public class XSRecipeBookCategories {
	
	public static final RecipeBookCategories SMELTING_FURNACE_SEARCH = RecipeBookCategories.create(XSurvive.MOD_ID + "_smelting_furnace_search", new ItemStack(Items.COMPASS));
	public static final RecipeBookCategories SMELTING_FURNACE_BLOCKS = RecipeBookCategories.create(XSurvive.MOD_ID + "_smelting_furnace_blocks", new ItemStack(Items.DEEPSLATE));
	public static final RecipeBookCategories SMELTING_FURNACE_MISC = RecipeBookCategories.create(XSurvive.MOD_ID + "_smelting_furnace_misc", new ItemStack(Items.BRICK));
	
	public static void register() {}
}
