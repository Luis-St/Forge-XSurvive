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

package net.luis.xsurvive.world.item.crafting;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-St
 *
 */

public class XSRecipeBookCategories {
	
	public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORY = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, XSurvive.MOD_ID);
	
	public static final RegistryObject<RecipeBookCategory> XSMELTING_FURNACE_BLOCKS = RECIPE_BOOK_CATEGORY.register("xsmelting_furnace_blocks", RecipeBookCategory::new);
	public static final RegistryObject<RecipeBookCategory> XSMELTING_FURNACE_MISC = RECIPE_BOOK_CATEGORY.register("xsmelting_furnace_misc", RecipeBookCategory::new);
}