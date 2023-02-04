package net.luis.xsurvive.data.provider.recipe;

import java.util.Objects;
import java.util.function.Consumer;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 * 
 * @author Luis-st
 *
 */

public class XSRecipeProvider extends RecipeProvider {
	
	public XSRecipeProvider(DataGenerator generator) {
		super(generator.getPackOutput());
	}
	
	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.MISC, Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ItemTags.SAND, Items.GLASS);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.NETHERRACK, Items.NETHER_BRICK);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.SANDSTONE, Items.SMOOTH_SANDSTONE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.CLAY, Items.TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.MISC, Items.WET_SPONGE, Items.SPONGE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.STONE_BRICKS, Items.CRACKED_STONE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.STONE, Items.SMOOTH_STONE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.COBBLESTONE, Items.STONE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.BASALT, Items.SMOOTH_BASALT);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.MISC, ItemTags.LOGS, Items.CHARCOAL);
		this.furnaceRecipe(consumer, Items.CHARCOAL, Items.COAL);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.MISC, Items.CHARCOAL, Items.COAL);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.MISC, Items.CACTUS, Items.GREEN_DYE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.MISC, Items.SEA_PICKLE, Items.LIME_DYE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.CLAY_BALL, Items.BRICK);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.NETHER_BRICKS, Items.CRACKED_NETHER_BRICKS);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.DEEPSLATE_TILES, Items.CRACKED_DEEPSLATE_TILES);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE, Items.DEEPSLATE);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.DEEPSLATE_BRICKS, Items.CRACKED_DEEPSLATE_BRICKS);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.PURPLE_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.WHITE_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.ORANGE_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.PINK_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.MAGENTA_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.BLACK_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.BROWN_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.YELLOW_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.BLUE_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.RED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.GREEN_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.LIME_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.CYAN_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.GRAY_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Items.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, XSBlocks.HONEY_MELON.get()).requires(XSItems.HONEY_MELON_SLICE.get(), 9), getGroup(XSBlocks.HONEY_MELON.get()), XSItems.HONEY_MELON_SLICE.get(),
			XSBlocks.HONEY_MELON.get()).save(consumer);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, XSItems.HONEY_MELON_SEEDS.get()).requires(XSItems.HONEY_MELON_SLICE.get()), getGroup(XSBlocks.HONEY_MELON.get()), XSItems.HONEY_MELON_SLICE.get(),
			XSItems.HONEY_MELON_SEEDS.get()).save(consumer);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, XSItems.HONEY_MELON_SLICE.get()).requires(Items.MELON_SLICE).requires(Items.HONEY_BOTTLE), getGroup(XSBlocks.HONEY_MELON.get()), Items.MELON_SLICE,
				Items.HONEY_BOTTLE, XSItems.HONEY_MELON_SLICE.get()).save(consumer);
		this.groupAndUnlock(ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, XSItems.DIAMOND_APPLE.get()).define('#', Items.DIAMOND).define('?', Items.APPLE).pattern("###").pattern("#?#").pattern("###"), getGroup(XSItems.DIAMOND_APPLE.get()), Items.DIAMOND,
			Items.APPLE).save(consumer);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NETHERITE_INGOT).requires(Items.NETHERITE_SCRAP, 9), getGroup(Items.NETHERITE_INGOT), Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT).save(consumer);
	}
	
	private void furnaceRecipe(Consumer<FinishedRecipe> consumer, Item input, Item result) {
		this.groupAndUnlock(SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, result, 1.0F, 200), getGroup(input), input, result).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result)));
	}
	
	private void smeltingFurnaceRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category,  Item input, Item result) {
		this.groupAndUnlock(SmeltingRecipeBuilder.of(category, Ingredient.of(input), result, 0.5F, 100), getGroup(input), input, result).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
	}
	
	private void smeltingFurnaceRecipe(Consumer<FinishedRecipe> consumer, RecipeCategory category,  TagKey<Item> input, Item result) {
		this.groupAndUnlock(SmeltingRecipeBuilder.of(category, Ingredient.of(input), result, 0.5F, 100), input.location().getPath(), Ingredient.of(input), result).save(consumer, new ResourceLocation(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
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
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.asItem())).getPath();
	}
	
	private static String getGroup(ItemLike item) {
		String path = getId(item);
		if (!path.contains("_")) {
			return path;
		}
		return path.split("_")[0];
	}
	
}