package net.luis.xsurvive.jei;

import java.util.List;

import mezz.jei.api.recipe.category.IRecipeCategory;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

/**
 *
 * @author Luis-st
 *
 */

public class XSRecipes {
	
	private final RecipeManager recipeManager;
	
	public XSRecipes() {
		Minecraft minecraft = Minecraft.getInstance();
		this.recipeManager = minecraft.level.getRecipeManager();
	}
	
	public List<SmeltingRecipe> getSmeltingRecipes(IRecipeCategory<SmeltingRecipe> smeltingCategory) {
		return getValidHandledRecipes(this.recipeManager, XSRecipeTypes.SMELTING.get());
	}
	
	private static <C extends Container, T extends Recipe<C>> List<T> getValidHandledRecipes(RecipeManager recipeManager, RecipeType<T> recipeType) {
		return recipeManager.getAllRecipesFor(recipeType);
	}
	
}
