package net.luis.xsurvive.world.item.crafting;

import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * 
 * @author Luis-st
 *
 */

public class SmeltingRecipe extends AbstractCookingRecipe {

	public SmeltingRecipe(ResourceLocation location, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
		super(XSRecipeTypes.SMELTING.get(), location, group, category, ingredient, result, experience, cookingTime);
	}
	
	@Override
	public @NotNull ItemStack getToastSymbol() {
		return new ItemStack(XSBlocks.SMELTING_FURNACE.get());
	}
	
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return XSRecipeSerializers.SMELTING_RECIPE.get();
	}

}
