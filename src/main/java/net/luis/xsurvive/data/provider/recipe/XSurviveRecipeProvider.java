package net.luis.xsurvive.data.provider.recipe;

import java.util.function.Consumer;

import net.luis.xsurvive.XSurvive;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public class XSurviveRecipeProvider extends RecipeProvider {
	
	public XSurviveRecipeProvider(DataGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.CHORUS_FRUIT), Items.POPPED_CHORUS_FRUIT);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(ItemTags.SAND), Items.GLASS);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.NETHERRACK), Items.NETHER_BRICK);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.SANDSTONE), Items.SMOOTH_SANDSTONE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.CLAY), Items.TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.WET_SPONGE), Items.SPONGE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.RED_SANDSTONE), Items.SMOOTH_RED_SANDSTONE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.STONE_BRICKS), Items.CRACKED_STONE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.STONE), Items.SMOOTH_STONE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.QUARTZ_BLOCK), Items.SMOOTH_QUARTZ);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.COBBLESTONE), Items.STONE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.BASALT), Items.SMOOTH_BASALT);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(ItemTags.LOGS), Items.CHARCOAL);
		this.furnaceRecipe(consumer, Ingredient.of(Items.CHARCOAL), Items.COAL);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.CHARCOAL), Items.COAL);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.CACTUS), Items.GREEN_DYE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.SEA_PICKLE), Items.LIME_DYE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.CLAY_BALL), Items.BRICK);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.NETHER_BRICKS), Items.CRACKED_NETHER_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.DEEPSLATE_TILES), Items.CRACKED_DEEPSLATE_TILES);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.COBBLED_DEEPSLATE), Items.DEEPSLATE);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.POLISHED_BLACKSTONE_BRICKS), Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.DEEPSLATE_BRICKS), Items.CRACKED_DEEPSLATE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.PURPLE_TERRACOTTA), Items.PURPLE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.WHITE_TERRACOTTA), Items.WHITE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.ORANGE_TERRACOTTA), Items.ORANGE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.PINK_TERRACOTTA), Items.PINK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.MAGENTA_TERRACOTTA), Items.MAGENTA_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.BLACK_TERRACOTTA), Items.BLACK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.BROWN_TERRACOTTA), Items.BROWN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.YELLOW_TERRACOTTA), Items.YELLOW_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.BLUE_TERRACOTTA), Items.BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.RED_TERRACOTTA), Items.RED_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.GREEN_TERRACOTTA), Items.GREEN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.LIME_TERRACOTTA), Items.LIME_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.CYAN_TERRACOTTA), Items.CYAN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.GRAY_TERRACOTTA), Items.GRAY_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.LIGHT_BLUE_TERRACOTTA), Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Ingredient.of(Items.LIGHT_GRAY_TERRACOTTA), Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
	}
	
	private void furnaceRecipe(Consumer<FinishedRecipe> consumer, Ingredient input, Item result) {
		SimpleCookingRecipeBuilder.smelting(input, result, 1.0F, 200).unlockedBy("has_" + getId(result), has(result)).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result)));
	}
	
	private void smeltingFurnaceRecipe(Consumer<FinishedRecipe> consumer, Ingredient input, Item result) {
		SmeltingRecipeBuilder.of(input, result, 0.5F, 100).unlockedBy("has_" + getId(result), has(result)).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
	}
	
	private static String getId(Item item) {
		return ForgeRegistries.ITEMS.getKey(item).getPath();
	}
	
	@Override
	public String getName() {
		return "XSurvive Recipes";
	}
	
}