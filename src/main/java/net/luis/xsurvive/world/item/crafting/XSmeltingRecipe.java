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

import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSmeltingRecipe extends AbstractCookingRecipe {
	
	public XSmeltingRecipe(@NotNull String group, @NotNull CookingBookCategory category, @NotNull Ingredient ingredient, @NotNull ItemStack result, float experience, int cookingTime) {
		super(group, category, ingredient, result, experience, cookingTime);
	}
	
	@Override
	protected @NotNull Item furnaceIcon() {
		return XSBlocks.SMELTING_FURNACE.get().asItem();
	}
	
	@Override
	public @NotNull RecipeBookCategory recipeBookCategory() {
		return switch (this.category()) {
			case BLOCKS -> XSRecipeBookCategories.XSMELTING_FURNACE_BLOCKS.get();
			case FOOD, MISC -> XSRecipeBookCategories.XSMELTING_FURNACE_MISC.get();
		};
	}
	
	@Override
	public @NotNull RecipeSerializer<XSmeltingRecipe> getSerializer() {
		return XSRecipeSerializers.XSMELTING.get();
	}
	
	@Override
	public @NotNull RecipeType<XSmeltingRecipe> getType() {
		return XSRecipeTypes.XSMELTING.get();
	}
}
