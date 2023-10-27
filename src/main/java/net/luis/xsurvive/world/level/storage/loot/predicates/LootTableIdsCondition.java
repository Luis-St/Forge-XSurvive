package net.luis.xsurvive.world.level.storage.loot.predicates;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class LootTableIdsCondition implements LootItemCondition {
	
	public static final Codec<LootTableIdsCondition> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.list(ResourceLocation.CODEC).fieldOf("loot_table_ids").forGetter((condition) -> {
			return condition.lootTables;
		})).apply(instance, LootTableIdsCondition::new);
	});
	
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
}
