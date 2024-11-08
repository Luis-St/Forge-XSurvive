/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.data.provider.base;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.data.provider.SmeltingRecipeBuilder;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.HolderLookup;
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

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("SameParameterValue")
public class XSRecipeProvider extends RecipeProvider {
	
	public XSRecipeProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookup) {
		super(generator.getPackOutput(), lookup);
	}
	
	protected static @NotNull String getId(@NotNull ItemLike item) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.asItem())).getPath();
	}
	
	protected static @NotNull String getGroup(@NotNull ItemLike item) {
		String path = getId(item);
		if (!path.contains("_")) {
			return path;
		}
		return path.split("_")[0];
	}
	
	@Override
	protected void buildRecipes(@NotNull RecipeOutput output) {
		this.smeltingFurnaceRecipe(output, RecipeCategory.MISC, Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, ItemTags.SAND, Items.GLASS);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.NETHERRACK, Items.NETHER_BRICK);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.SANDSTONE, Items.SMOOTH_SANDSTONE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.CLAY, Items.TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.MISC, Items.WET_SPONGE, Items.SPONGE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.STONE_BRICKS, Items.CRACKED_STONE_BRICKS);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.STONE, Items.SMOOTH_STONE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.COBBLESTONE, Items.STONE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.BASALT, Items.SMOOTH_BASALT);
		this.smeltingFurnaceRecipe(output, RecipeCategory.MISC, ItemTags.LOGS, Items.CHARCOAL);
		this.furnaceRecipe(output, Items.CHARCOAL, Items.COAL);
		this.smeltingFurnaceRecipe(output, RecipeCategory.MISC, Items.CHARCOAL, Items.COAL);
		this.smeltingFurnaceRecipe(output, RecipeCategory.MISC, Items.CACTUS, Items.GREEN_DYE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.MISC, Items.SEA_PICKLE, Items.LIME_DYE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.CLAY_BALL, Items.BRICK);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.NETHER_BRICKS, Items.CRACKED_NETHER_BRICKS);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.DEEPSLATE_TILES, Items.CRACKED_DEEPSLATE_TILES);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE, Items.DEEPSLATE);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_BRICKS, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.DEEPSLATE_BRICKS, Items.CRACKED_DEEPSLATE_BRICKS);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.PURPLE_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.WHITE_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.ORANGE_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.PINK_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.MAGENTA_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.BLACK_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.BROWN_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.YELLOW_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.BLUE_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.RED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.GREEN_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.LIME_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.CYAN_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.GRAY_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
		this.smeltingFurnaceRecipe(output, RecipeCategory.BUILDING_BLOCKS, Items.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, XSBlocks.HONEY_MELON.get()).requires(XSItems.HONEY_MELON_SLICE.get(), 9), getGroup(XSBlocks.HONEY_MELON.get()), XSItems.HONEY_MELON_SLICE.get(),
			XSBlocks.HONEY_MELON.get()).save(output);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, XSItems.HONEY_MELON_SEEDS.get()).requires(XSItems.HONEY_MELON_SLICE.get()), getGroup(XSBlocks.HONEY_MELON.get()), XSItems.HONEY_MELON_SLICE.get(),
			XSItems.HONEY_MELON_SEEDS.get()).save(output);
		this.groupAndUnlock(ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, XSItems.HONEY_MELON_SLICE.get()).requires(Items.MELON_SLICE).requires(Items.HONEY_BOTTLE), getGroup(XSBlocks.HONEY_MELON.get()), Items.MELON_SLICE,
			Items.HONEY_BOTTLE, XSItems.HONEY_MELON_SLICE.get()).save(output);
		this.groupAndUnlock(ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, XSItems.DIAMOND_APPLE.get()).define('#', Items.DIAMOND).define('?', Items.APPLE).pattern("###").pattern("#?#").pattern("###"), getGroup(XSItems.DIAMOND_APPLE.get()),
			Items.DIAMOND, Items.APPLE).save(output);
	}
	
	protected void furnaceRecipe(@NotNull RecipeOutput output, @NotNull Item input, @NotNull Item result) {
		this.groupAndUnlock(SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, result, 1.0F, 200), getGroup(input), input, result).save(output, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, getId(result)));
	}
	
	protected void smeltingFurnaceRecipe(@NotNull RecipeOutput output, @NotNull RecipeCategory category, @NotNull Item input, @NotNull Item result) {
		this.groupAndUnlock(SmeltingRecipeBuilder.of(category, Ingredient.of(input), result, 0.5F), getGroup(input), input, result).save(output, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
	}
	
	protected void smeltingFurnaceRecipe(@NotNull RecipeOutput output, @NotNull RecipeCategory category, @NotNull TagKey<Item> input, @NotNull Item result) {
		this.groupAndUnlock(SmeltingRecipeBuilder.of(category, Ingredient.of(input), result, 0.5F), input.location().getPath(), Ingredient.of(input), result)
			.save(output, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, getId(result) + "_from_smelting"));
	}
	
	protected @NotNull RecipeBuilder groupAndUnlock(@NotNull RecipeBuilder builder, @NotNull String group, ItemLike @NotNull ... unlockCriterions) {
		for (ItemLike unlockCriterion : unlockCriterions) {
			builder.unlockedBy("has_" + getId(unlockCriterion), has(unlockCriterion));
		}
		return builder.group(group);
	}
	
	protected @NotNull RecipeBuilder groupAndUnlock(@NotNull RecipeBuilder builder, @NotNull String group, @NotNull Ingredient ingredientCriterion, @NotNull Item itemCriterion) {
		for (Ingredient.Value value : ingredientCriterion.values) {
			if (value instanceof Ingredient.ItemValue itemValue) {
				builder.unlockedBy("has_" + getId(itemValue.item().getItem()), has(itemValue.item().getItem()));
			} else if (value instanceof Ingredient.TagValue tagValue) {
				builder.unlockedBy("has_" + tagValue.tag().location().getPath(), has(tagValue.tag()));
			}
		}
		return this.groupAndUnlock(builder, group, itemCriterion);
	}
}
