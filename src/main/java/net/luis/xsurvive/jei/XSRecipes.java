package net.luis.xsurvive.jei;

import mezz.jei.api.recipe.category.IRecipeCategory;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class XSRecipes {
	
	private static final RecipeManager RECIPE_MANAGER = Minecraft.getInstance().level.getRecipeManager();
	
	public static @NotNull List<SmeltingRecipe> getSmeltingRecipes(@NotNull IRecipeCategory<SmeltingRecipe> smeltingCategory) {
		return RECIPE_MANAGER.getAllRecipesFor(XSRecipeTypes.SMELTING.get());
	}
}
