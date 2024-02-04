package net.luis.xsurvive.data.provider.recipe;

import com.google.gson.JsonObject;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.item.crafting.XSRecipeSerializers;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingRecipeBuilder implements RecipeBuilder {
	
	private final RecipeCategory category;
	private final CookingBookCategory bookCategory;
	private final Item result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;
	
	public SmeltingRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, Ingredient ingredient, @NotNull ItemLike result, float experience, int cookingTime) {
		this.category = category;
		this.bookCategory = bookCategory;
		this.ingredient = ingredient;
		this.result = result.asItem();
		this.experience = experience;
		this.cookingTime = cookingTime;
	}
	
	public static @NotNull SmeltingRecipeBuilder of(RecipeCategory category, Ingredient ingredient, ItemLike result, float experience) {
		return new SmeltingRecipeBuilder(category, determineRecipeCategory(result.asItem()), ingredient, result, experience, 100);
	}
	
	private static @NotNull CookingBookCategory determineRecipeCategory(@NotNull Item result) {
		if (result.isEdible()) {
			return CookingBookCategory.FOOD;
		} else {
			return result instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
		}
	}
	
	public @NotNull SmeltingRecipeBuilder unlockedBy(@NotNull String key, @NotNull Criterion<?> criterion) {
		this.criteria.put(key, criterion);
		return this;
	}
	
	public @NotNull SmeltingRecipeBuilder group(String group) {
		this.group = group;
		return this;
	}
	
	public @NotNull Item getResult() {
		return this.result;
	}
	
	@Override
	public void save(@NotNull RecipeOutput output, @NotNull ResourceLocation id) {
		this.ensureValid(id);
		Advancement.Builder builder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(builder::addCriterion);
		AdvancementHolder advancement = builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/"));
		output.accept(id, new SmeltingRecipe(this.group == null ? "" : this.group, this.bookCategory, this.ingredient, new ItemStack(this.result), this.experience, this.cookingTime), advancement);
	}
	
	private void ensureValid(ResourceLocation id) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
}
