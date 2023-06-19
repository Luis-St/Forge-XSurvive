package net.luis.xsurvive.jei;

import mezz.jei.api.recipe.category.IRecipeCategory;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public class XSRecipes {
	
	private final RecipeManager recipeManager;
	
	public XSRecipes() {
		this.recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
	}
	
	private static <C extends Container, T extends Recipe<C>> List<T> getValidHandledRecipes(RecipeManager recipeManager, RecipeType<T> recipeType) {
		return recipeManager.getAllRecipesFor(recipeType);
	}
	
	public List<SmeltingRecipe> getSmeltingRecipes(IRecipeCategory<SmeltingRecipe> smeltingCategory) {
		return getValidHandledRecipes(this.recipeManager, XSRecipeTypes.SMELTING.get());
	}
}
