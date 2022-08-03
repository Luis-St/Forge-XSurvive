package net.luis.xsurvive.data.provider.recipe;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.XSurviveRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class SmeltingRecipeBuilder implements RecipeBuilder {
	
	private final Item result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	@Nullable
	private String group;
	
	public SmeltingRecipeBuilder(Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
		this.ingredient = ingredient;
		this.result = result.asItem();
		this.experience = experience;
		this.cookingTime = cookingTime;
	}
	
	public static SmeltingRecipeBuilder of(Ingredient ingredient, ItemLike result, float experience, int cookingTime) {
		return new SmeltingRecipeBuilder(ingredient, result, experience, 100);
	}
	
	public SmeltingRecipeBuilder unlockedBy(String key, CriterionTriggerInstance triggerInstance) {
		this.advancement.addCriterion(key, triggerInstance);
		return this;
	}

	public SmeltingRecipeBuilder group(String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return this.result;
	}
	
	@Override
	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
		this.ensureValid(id);
		this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
		consumer.accept(new Result(id, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement));
	}
	
	private void ensureValid(ResourceLocation id) {
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}
	
	public static class Result implements FinishedRecipe {
		
		private final ResourceLocation id;
		private final String group;
		private final Ingredient ingredient;
		private final Item result;
		private final float experience;
		private final int cookingTime;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		
		public Result(ResourceLocation id, String group, Ingredient ingredient, Item result, float experience, int cookingTime, Advancement.Builder advancement) {
			this.id = id;
			this.group = group;
			this.ingredient = ingredient;
			this.result = result;
			this.experience = experience;
			this.cookingTime = cookingTime;
			this.advancement = advancement;
			this.advancementId = new ResourceLocation(this.id.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + this.id.getPath());
		}
		
		@Override
		public JsonObject serializeRecipe() {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("type", XSurvive.MOD_ID + ":xsurvive_smelting");
			this.serializeRecipeData(jsonobject);
			return jsonobject;
		}
		
		@Override
		public void serializeRecipeData(JsonObject object) {
			if (!this.group.isEmpty()) {
				object.addProperty("group", this.group);
			}
			object.add("ingredient", this.ingredient.toJson());
			object.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
			object.addProperty("experience", this.experience);
			object.addProperty("cookingtime", this.cookingTime);
		}
		
		@Override
		public RecipeSerializer<?> getType() {
			return XSurviveRecipeSerializers.SMELTING_RECIPE.get();
		}
		
		@Override
		public ResourceLocation getId() {
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
