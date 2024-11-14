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

package net.luis.xsurvive.data.provider.base.server;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.*;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.storage.loot.*;
import net.luis.xsurvive.world.level.storage.loot.predicates.LootTableIdsCondition;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static net.minecraft.world.level.storage.loot.BuiltInLootTables.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSGlobalLootModifierProvider extends GlobalLootModifierProvider {
	
	public XSGlobalLootModifierProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookup) {
		super(generator.getPackOutput(), XSurvive.MOD_ID, lookup);
	}
	
	//region Static helper methods
	private static @NotNull WeightCollection<RarityList<Item>> createRuneWeights() {
		return Util.make(new WeightCollection<>(), (collection) -> {
			collection.add(10, LootModifierHelper.getCommonRunes());
			collection.add(70, LootModifierHelper.getRareRunes());
			collection.add(20, LootModifierHelper.getTreasureRunes());
		});
	}
	
	private static @NotNull WeightCollection<RarityList<Holder<Enchantment>>> createEnchantmentWeights(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return Util.make(new WeightCollection<>(), (collection) -> {
			collection.add(20, LootModifierHelper.getCommonEnchantments(lookup));
			collection.add(40, LootModifierHelper.getRareEnchantments(lookup));
			collection.add(25, LootModifierHelper.getVeryRareEnchantments(lookup));
			collection.add(15, LootModifierHelper.getTreasureEnchantments(lookup));
		});
	}
	//endregion
	
	@Override
	protected void start(HolderLookup.@NotNull Provider lookup) {
		HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = lookup.lookupOrThrow(Registries.ENCHANTMENT);
		this.add("multi_drop_modifier", new MultiDropModifier(new LootItemCondition[] {
			MatchTool.toolMatches(this.enchantmentPredicate(enchantmentLookup, XSEnchantments.MULTI_DROP)).build()
		}));
		this.add("smelting_modifier", new SmeltingModifier(new LootItemCondition[] {
			MatchTool.toolMatches(this.enchantmentPredicate(enchantmentLookup, XSEnchantments.SMELTING)).build()
		}));
		this.add("rune_item_modifier", new RuneItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(STRONGHOLD_LIBRARY.location())
				.add(STRONGHOLD_CROSSING.location())
				.add(STRONGHOLD_CORRIDOR.location())
				.add(BASTION_BRIDGE.location())
				.add(BASTION_HOGLIN_STABLE.location())
				.add(BASTION_OTHER.location())
				.add(BASTION_TREASURE.location())
				.add(END_CITY_TREASURE.location())
				.add(ANCIENT_CITY.location())
				.add(ANCIENT_CITY_ICE_BOX.location()).build()
		}, 2, createRuneWeights()));
		this.add("golden_book_modifier", new GoldenBookModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(STRONGHOLD_LIBRARY.location())
				.add(BASTION_TREASURE.location())
				.add(END_CITY_TREASURE.location())
				.add(ANCIENT_CITY.location()).build()
		}, 1,
			createEnchantmentWeights(enchantmentLookup::getOrThrow),
			LootModifierHelper.getExtraOverworldTreasure(enchantmentLookup::getOrThrow),
			LootModifierHelper.getExtraNetherTreasure(enchantmentLookup::getOrThrow),
			LootModifierHelper.getExtraEndTreasure(enchantmentLookup::getOrThrow)
		));
		this.add("diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(SIMPLE_DUNGEON.location())
				.add(ABANDONED_MINESHAFT.location())
				.add(BASTION_OTHER.location())
				.add(BASTION_HOGLIN_STABLE.location())
				.add(IGLOO_CHEST.location())
				.add(DESERT_PYRAMID.location())
				.add(RUINED_PORTAL.location())
				.add(STRONGHOLD_CROSSING.location())
				.add(UNDERWATER_RUIN_BIG.location())
				.add(WOODLAND_MANSION.location()).build()
		}, XSItems.DIAMOND_APPLE.get(), Chance.of(0.05)));
		this.add("enchanted_diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(SIMPLE_DUNGEON.location())
				.add(ABANDONED_MINESHAFT.location())
				.add(ANCIENT_CITY.location())
				.add(BASTION_TREASURE.location())
				.add(DESERT_PYRAMID.location())
				.add(RUINED_PORTAL.location())
				.add(WOODLAND_MANSION.location()).build()
		}, XSItems.ENCHANTED_DIAMOND_APPLE.get(), Chance.of(0.01)));
	}
	
	//region Helper methods
	private ItemPredicate.@NotNull Builder enchantmentPredicate(HolderLookup.@NotNull RegistryLookup<Enchantment> enchantmentLookup, @NotNull ResourceKey<Enchantment> enchantment) {
		return ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS,
			ItemEnchantmentsPredicate.enchantments(
				List.of(new EnchantmentPredicate(enchantmentLookup.getOrThrow(enchantment), MinMaxBounds.Ints.atLeast(1)))
			)
		);
	}
	//endregion
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Global Loot Modifiers";
	}
}
