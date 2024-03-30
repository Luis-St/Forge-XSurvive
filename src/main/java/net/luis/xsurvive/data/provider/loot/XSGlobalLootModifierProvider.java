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

package net.luis.xsurvive.data.provider.loot;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.Chance;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.storage.loot.*;
import net.luis.xsurvive.world.level.storage.loot.predicates.LootTableIdsCondition;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static net.minecraft.world.level.storage.loot.BuiltInLootTables.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSGlobalLootModifierProvider extends GlobalLootModifierProvider {
	
	public XSGlobalLootModifierProvider(@NotNull DataGenerator generator) {
		super(generator.getPackOutput(), XSurvive.MOD_ID);
	}
	
	@Override
	protected void start() {
		this.add("multi_drop_modifier", new MultiDropModifier(new LootItemCondition[] {
			new MatchTool(Optional.of(ItemPredicate.Builder.item().build()))
		}));
		this.add("smelting_modifier", new SmeltingModifier(new LootItemCondition[] {
			new MatchTool(Optional.of(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(XSEnchantments.SMELTING.get(), MinMaxBounds.Ints.atLeast(1))).build()))
		}));
		this.add("rune_item_modifier", new RuneItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(STRONGHOLD_LIBRARY).add(STRONGHOLD_CROSSING).add(STRONGHOLD_CORRIDOR).add(BASTION_BRIDGE).add(BASTION_HOGLIN_STABLE).add(BASTION_OTHER)
				.add(BASTION_TREASURE).add(END_CITY_TREASURE).add(ANCIENT_CITY).add(ANCIENT_CITY_ICE_BOX).build()
		}, 2, Util.make(new WeightCollection<>(), (collection) -> {
			collection.add(10, LootModifierHelper.getCommonRunes());
			collection.add(70, LootModifierHelper.getRareRunes());
			collection.add(20, LootModifierHelper.getTreasureRunes());
		})));
		this.add("golden_book_modifier", new GoldenBookModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(STRONGHOLD_LIBRARY).add(BASTION_TREASURE).add(END_CITY_TREASURE).add(ANCIENT_CITY).build()
		}, 1, Util.make(new WeightCollection<>(), (collection) -> {
			collection.add(20, LootModifierHelper.getCommonEnchantments());
			collection.add(40, LootModifierHelper.getRareEnchantments());
			collection.add(25, LootModifierHelper.getVeryRareEnchantments());
			collection.add(15, LootModifierHelper.getTreasureEnchantments());
		}), LootModifierHelper.getExtraOverworldTreasure(), LootModifierHelper.getExtraNetherTreasure(), LootModifierHelper.getExtraEndTreasure()));
		this.add("diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(SIMPLE_DUNGEON).add(ABANDONED_MINESHAFT).add(BASTION_OTHER).add(BASTION_HOGLIN_STABLE).add(IGLOO_CHEST).add(DESERT_PYRAMID).add(RUINED_PORTAL).add(STRONGHOLD_CROSSING).add(UNDERWATER_RUIN_BIG).add(WOODLAND_MANSION).build()
		}, XSItems.DIAMOND_APPLE.get(), Chance.of(0.05)));
		this.add("enchanted_diamond_apple_modifier", new AdditionalChanceItemModifier(new LootItemCondition[] {
			new LootTableIdsCondition.Builder(SIMPLE_DUNGEON).add(ABANDONED_MINESHAFT).add(ANCIENT_CITY).add(BASTION_TREASURE).add(DESERT_PYRAMID).add(RUINED_PORTAL).add(WOODLAND_MANSION).build()
		}, XSItems.ENCHANTED_DIAMOND_APPLE.get(), Chance.of(0.01)));
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Global Loot Modifiers";
	}
}