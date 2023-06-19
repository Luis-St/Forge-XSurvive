package net.luis.xsurvive.jei;

import mezz.jei.api.recipe.RecipeType;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;

/**
 *
 * @author Luis-st
 *
 */

public class XSJeiRecipeTypes {
	
	public static final RecipeType<SmeltingRecipe> SMELTING = RecipeType.create(XSurvive.MOD_ID, "xsurvive_smelting", SmeltingRecipe.class);
}
