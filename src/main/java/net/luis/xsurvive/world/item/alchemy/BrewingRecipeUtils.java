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
		simpleRecipe(baseItem, potion);
		longRecipe(potion, longPotion);
		strongRecipe(potion, strongPotion);
	}
	
	public static void simpleRecipe(@NotNull Item baseItem, @NotNull Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(baseItem), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
	
	public static void longRecipe(@NotNull Potion inputPotion, @NotNull Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(Items.REDSTONE), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
	
	public static void strongRecipe(@NotNull Potion inputPotion, @NotNull Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(Items.GLOWSTONE_DUST), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
}
