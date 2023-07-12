package net.luis.xsurvive.world.level.storage.loot.predicates;

import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class LootTableIdsCondition implements LootItemCondition {
	
	private final List<ResourceLocation> lootTables;
	
	public LootTableIdsCondition(List<ResourceLocation> lootTables) {
		this.lootTables = lootTables;
	}
	
	@Override
	public boolean test(@NotNull LootContext context) {
		return this.lootTables.contains(context.getQueriedLootTableId());
	}
	
	@Override
	public @NotNull LootItemConditionType getType() {
		return XSLootItemConditions.LOOT_TABLE_IDS.get();
	}
	
	public static class Builder implements LootItemCondition.Builder {
		
		private final List<ResourceLocation> lootTables;
		
		public Builder(String lootTable) {
			this(new ResourceLocation(lootTable));
		}
		
		public Builder(ResourceLocation lootTable) {
			this.lootTables = Lists.newArrayList();
			this.add(lootTable);
		}
		
		public @NotNull Builder add(@NotNull String lootTable) {
			return this.add(new ResourceLocation(lootTable));
		}
		
		public @NotNull Builder add(@NotNull ResourceLocation lootTable) {
			this.lootTables.add(lootTable);
			return this;
		}
		
		@Override
		public @NotNull LootItemCondition build() {
			return new LootTableIdsCondition(this.lootTables);
		}
	}
	
	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootTableIdsCondition> {
		
		@Override
		public void serialize(@NotNull JsonObject object, @NotNull LootTableIdsCondition instance, @NotNull JsonSerializationContext context) {
			JsonArray array = new JsonArray();
			for (ResourceLocation location : instance.lootTables) {
				array.add(location.toString());
			}
			object.add("loot_table_ids", array);
		}
		
		@Override
		public @NotNull LootTableIdsCondition deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext context) {
			List<ResourceLocation> lootTables = Lists.newArrayList();
			JsonArray array = object.get("loot_table_ids").getAsJsonArray();
			for (JsonElement jsonElement : array) {
				lootTables.add(new ResourceLocation(jsonElement.getAsString()));
			}
			return new LootTableIdsCondition(lootTables);
		}
	}
}
