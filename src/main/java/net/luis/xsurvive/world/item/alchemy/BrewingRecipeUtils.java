package net.luis.xsurvive.world.item.alchemy;

import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

/**
 *
 * @author Luis-st
 *
 */

public class BrewingRecipeUtils {
	
	public static void addRecipes(Item baseItem, Potion potion, Potion longPotion, Potion strongPotion) {
		simpleRecipe(baseItem, potion);
		longRecipe(potion, longPotion);
		strongRecipe(potion, strongPotion);
	}
	
	public static void simpleRecipe(Item baseItem, Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(baseItem), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
	
	public static void longRecipe(Potion inputPotion, Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(Items.REDSTONE), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
	
	public static void strongRecipe(Potion inputPotion, Potion resultPotion) {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), inputPotion)), Ingredient.of(Items.GLOWSTONE_DUST), PotionUtils.setPotion(new ItemStack(Items.POTION), resultPotion));
	}
}
