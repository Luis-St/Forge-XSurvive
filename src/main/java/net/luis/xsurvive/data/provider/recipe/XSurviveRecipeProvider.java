package net.luis.xsurvive.data.provider.recipe;

import java.util.function.Consumer;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSurviveItems;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveRecipeProvider extends RecipeProvider {
	
	public XSurviveRecipeProvider(DataGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		this.smeltingFurnaceRecipe(consumer, Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT);
		this.smeltingFurnaceRecipe(consumer, ItemTags.SAND, Items.GLASS);
		this.smeltingFurnaceRecipe(consumer, Items.NETHERRACK, Items.NETHER_BRICK);
		this.smeltingFurnaceRecipe(consumer, Items.SANDSTONE, Items.SMOOTH_SANDSTONE);
		this.smeltingFurnaceRecipe(consumer, Items.CLAY, Items.TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.WET_SPONGE, Items.SPONGE);
		this.smeltingFurnaceRecipe(consumer, Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE);
		this.smeltingFurnaceRecipe(consumer, Items.STONE_BRICKS, Items.CRACKED_STONE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Items.STONE, Items.SMOOTH_STONE);
		this.smeltingFurnaceRecipe(consumer, Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ);
		this.smeltingFurnaceRecipe(consumer, Items.COBBLESTONE, Items.STONE);
		this.smeltingFurnaceRecipe(consumer, Items.BASALT, Items.SMOOTH_BASALT);
		this.smeltingFurnaceRecipe(consumer, ItemTags.LOGS, Items.CHARCOAL);
		this.furnaceRecipe(consumer, Items.CHARCOAL, Items.COAL);
		this.smeltingFurnaceRecipe(consumer, Items.CHARCOAL, Items.COAL);
		this.smeltingFurnaceRecipe(consumer, Items.CACTUS, Items.GREEN_DYE);
		this.smeltingFurnaceRecipe(consumer, Items.SEA_PICKLE, Items.LIME_DYE);
		this.smeltingFurnaceRecipe(consumer, Items.CLAY_BALL, Items.BRICK);
		this.smeltingFurnaceRecipe(consumer, Items.NETHER_BRICKS, Items.CRACKED_NETHER_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Items.DEEPSLATE_TILES, Items.CRACKED_DEEPSLATE_TILES);
		this.smeltingFurnaceRecipe(consumer, Items.COBBLED_DEEPSLATE, Items.DEEPSLATE);
		this.smeltingFurnaceRecipe(consumer, Items.POLISHED_BLACKSTONE_BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Items.DEEPSLATE_BRICKS, Items.CRACKED_DEEPSLATE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, Items.PURPLE_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.WHITE_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.ORANGE_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.PINK_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.MAGENTA_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.BLACK_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.BROWN_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.YELLOW_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.BLUE_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.RED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.GREEN_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.LIME_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.CYAN_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.GRAY_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, Items.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(XSurviveBlocks.HONEY_MELON.get()).requires(XSurviveItems.HONEY_MELON_SLICE.get(), 9), getGroup(XSurviveBlocks.HONEY_MELON.get()), XSurviveItems.HONEY_MELON_SLICE.get(),
			XSurviveBlocks.HONEY_MELON.get()).save(consumer);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(XSurviveItems.HONEY_MELON_SEEDS.get()).requires(XSurviveItems.HONEY_MELON_SLICE.get()), getGroup(XSurviveBlocks.HONEY_MELON.get()), XSurviveItems.HONEY_MELON_SLICE.get(),
			XSurviveItems.HONEY_MELON_SEEDS.get()).save(consumer);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(XSurviveItems.HONEY_MELON_SLICE.get()).requires(Items.MELON_SLICE).requires(Items.HONEY_BOTTLE), getGroup(XSurviveBlocks.HONEY_MELON.get()), Items.MELON_SLICE, Items.HONEY_BOTTLE,
			XSurviveItems.HONEY_MELON_SLICE.get()).save(consumer);
	}
	
	private void furnaceRecipe(Consumer<FinishedRecipe> consumer, Item input, Item result) {
		this.groupAndUnlock(SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), result, 1.0F, 200), getGroup(input), input, result).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result)));
	}
	
	private void smeltingFurnaceRecipe(Consumer<FinishedRecipe> consumer, Item input, Item result) {
		this.groupAndUnlock(SmeltingRecipeBuilder.of(Ingredient.of(input), result, 0.5F, 100), getGroup(input), input, result).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
	}
	
	private void smeltingFurnaceRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> input, Item result) {
		this.groupAndUnlock(SmeltingRecipeBuilder.of(Ingredient.of(input), result, 0.5F, 100), input.location().getPath(), Ingredient.of(input), result).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
	}
	
	private RecipeBuilder groupAndUnlock(RecipeBuilder builder, String group, ItemLike... unlockCriterions) {
		for (ItemLike unlockCriterion : unlockCriterions) {
			builder.unlockedBy("has_" + getId(unlockCriterion), has(unlockCriterion));
		}
		return builder.group(group);
	}
	
	private RecipeBuilder groupAndUnlock(RecipeBuilder builder, String group, Ingredient ingredientCriterion, Item itemCriterion) {
		for (Ingredient.Value value : ingredientCriterion.values) {
			if (value instanceof Ingredient.ItemValue itemValue) {
				builder.unlockedBy("has_" + getId(itemValue.item.getItem()), has(itemValue.item.getItem()));
			} else if (value instanceof Ingredient.TagValue tagValue) {
				builder.unlockedBy("has_" + tagValue.tag.location().getPath(), has(tagValue.tag));
			}
		}
		return this.groupAndUnlock(builder, group, itemCriterion);
	}
	
	private static String getId(ItemLike item) {
		return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
	}
	
	private static String getGroup(ItemLike item) {
		String path = ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
		if (!path.contains("_")) {
			return path;
		}
		return path.split("_")[0];
	}
	
	@Override
	public String getName() {
		return "XSurvive Recipes";
	}
	
}