package net.luis.xsurvive.world.item.crafting;

import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * 
 * @author Luis-st
 *
 */

public class SmeltingRecipe extends AbstractCookingRecipe {

	public SmeltingRecipe(ResourceLocation location, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
		super(XSurviveRecipeTypes.SMELTING.get(), location, group, ingredient, result, experience, cookingTime);
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(XSurviveBlocks.SMELTING_FURNACE.get());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return XSurviveRecipeSerializers.SMELTING_RECIPE.get();
	}

}
