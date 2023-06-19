package net.luis.xsurvive.data.provider.recipe;

import com.google.gson.JsonObject;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.XSRecipeSerializers;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author Luis-st
 *
 */

public class SmeltingRecipeBuilder implements RecipeBuilder {
	
	private final RecipeCategory category;
	private final CookingBookCategory bookCategory;
	private final Item result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	@Nullable
	private String group;
	
	public SmeltingRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
		this.category = category;
		this.bookCategory = bookCategory;
		this.ingredient = ingredient;
		this.result = result.asItem();
		this.experience = experience;
		this.cookingTime = cookingTime;
	}
	
	public static SmeltingRecipeBuilder of(RecipeCategory category, Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
		return new SmeltingRecipeBuilder(category, determineRecipeCategory(result.asItem()), ingredient, result, experience, 100);
	}
	
	private static CookingBookCategory determineRecipeCategory(Item result) {
		if (result.isEdible()) {
			return CookingBookCategory.FOOD;
		} else {
			return result instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
		}
	}
	
	public @NotNull SmeltingRecipeBuilder unlockedBy(@NotNull String key, @NotNull CriterionTriggerInstance triggerInstance) {
		this.advancement.addCriterion(key, triggerInstance);
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
	public void save(Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation id) {
		this.ensureValid(id);
		this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
		consumer.accept(
				new Result(id, this.group == null ? "" : this.group, this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}
	
	private void ensureValid(ResourceLocation id) {
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
	
	public static class Result implements FinishedRecipe {
		
		private final ResourceLocation id;
		private final String group;
		private final CookingBookCategory category;
		private final Ingredient ingredient;
		private final Item result;
		private final float experience;
		private final int cookingTime;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		
		public Result(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime, Advancement.Builder advancement, ResourceLocation advancementId) {
			this.id = id;
			this.group = group;
			this.category = category;
			this.ingredient = ingredient;
			this.result = result;
			this.experience = experience;
			this.cookingTime = cookingTime;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}
		
		@Override
		public @NotNull JsonObject serializeRecipe() {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("type", XSurvive.MOD_ID + ":xsurvive_smelting");
			this.serializeRecipeData(jsonobject);
			return jsonobject;
		}
		
		@Override
		public void serializeRecipeData(@NotNull JsonObject object) {
			if (!this.group.isEmpty()) {
				object.addProperty("group", this.group);
			}
			object.addProperty("category", this.category.getSerializedName());
			object.add("ingredient", this.ingredient.toJson());
			object.addProperty("result", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
			object.addProperty("experience", this.experience);
			object.addProperty("cookingtime", this.cookingTime);
		}
		
		@Override
		public @NotNull RecipeSerializer<?> getType() {
			return XSRecipeSerializers.SMELTING_RECIPE.get();
		}
		
		@Override
		public @NotNull ResourceLocation getId() {
			return this.id;
		}
		
		@Override
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}
		
		@Override
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}
