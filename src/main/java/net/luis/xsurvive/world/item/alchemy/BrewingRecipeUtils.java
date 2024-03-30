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

package net.luis.xsurvive.world.item.alchemy;

import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class BrewingRecipeUtils {
	
	public static void addRecipes(@NotNull Item baseItem, @NotNull Potion potion, @NotNull Potion longPotion, @NotNull Potion strongPotion) {
		basicRecipe(baseItem, potion);
		longRecipe(potion, longPotion);
		strongRecipe(potion, strongPotion);
	}
	
	public static void basicRecipe(@NotNull Item baseItem, @NotNull Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(baseItem), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
	
	public static void longRecipe(@NotNull Potion inputPotion, @NotNull Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(Items.REDSTONE), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
	
	public static void strongRecipe(@NotNull Potion inputPotion, @NotNull Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(Items.GLOWSTONE_DUST), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
}
