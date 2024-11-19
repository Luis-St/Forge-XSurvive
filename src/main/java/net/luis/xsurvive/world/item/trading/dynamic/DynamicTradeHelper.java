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

package net.luis.xsurvive.world.item.trading.dynamic;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.luis.xsurvive.tag.XSEnchantmentTags;
import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.luis.xsurvive.world.item.trading.Trade;
import net.luis.xsurvive.world.level.storage.loot.LootModifierHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-St
 *
 */

class DynamicTradeHelper {
	
	static @NotNull List<Holder<Enchantment>> getNonTreasureEnchantments(@NotNull Collection<? extends Holder<Enchantment>> enchantments) {
		return enchantments.stream().filter(enchantment -> enchantment.is(EnchantmentTags.NON_TREASURE)).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	static @NotNull List<Holder<Enchantment>> getValidEnchantments(@NotNull Entity entity, @NotNull List<Rarity> rarities) {
		return getEnchantmentsForRarity(key -> ((ResourceKey<Enchantment>) key).getOrThrow(entity), rarities).stream().filter(enchantment -> enchantment.is(EnchantmentTags.TRADEABLE)).collect(Collectors.toList());
	}
	
	private static @NotNull List<Holder<Enchantment>> getEnchantmentsForRarity(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup, @NotNull List<Rarity> rarities) {
		List<Holder<Enchantment>> enchantments = Lists.newArrayList();
		if (rarities.contains(Rarity.COMMON)) {
			enchantments.addAll(LootModifierHelper.getCommonEnchantments(lookup).getValues());
		}
		if (rarities.contains(Rarity.RARE)) {
			enchantments.addAll(LootModifierHelper.getRareEnchantments(lookup).getValues());
		}
		if (rarities.contains(Rarity.VERY_RARE)) {
			enchantments.addAll(LootModifierHelper.getVeryRareEnchantments(lookup).getValues());
		}
		if (rarities.contains(Rarity.TREASURE)) {
			enchantments.addAll(LootModifierHelper.getAllTreasureEnchantments(lookup).getValues());
		}
		return enchantments;
	}
	
	static @NotNull List<Holder<Enchantment>> getValidGoldenEnchantments(@NotNull Collection<? extends Holder<Enchantment>> enchantments) {
		return enchantments.stream().filter(GoldenEnchantmentHelper::isEnchantment).collect(Collectors.toList());
	}
	
	static int getEmeraldCount(@NotNull RandomSource rng, int level) {
		return Math.min(2 + rng.nextInt(5 + level * 10) + 3 * level + 5, 64);
	}
	
	static int getEmeraldCount(@NotNull RandomSource rng, @NotNull List<MobEffectInstance> effects) {
		int emeralds = 1;
		for (MobEffectInstance effect : effects) {
			emeralds += effect.getAmplifier() + 1;
		}
		return getEmeraldCount(rng, emeralds);
	}
	
	static int getEmeraldCount(@NotNull ItemStack stack) {
		ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		int emeralds = enchantments.size();
		for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
			Holder<Enchantment> enchantment = entry.getKey();
			int anvilCost = enchantment.value().getAnvilCost();
			if (anvilCost >= 2) {
				if (anvilCost> 2) {
					emeralds += entry.getIntValue() + entry.getIntValue();
				} else {
					emeralds += entry.getIntValue() + (entry.getIntValue() / 2);
				}
			} else {
				emeralds += entry.getIntValue();
			}
		}
		return Math.min(emeralds, 64);
	}
	
	static int getVillagerXp(int villagerLevel) {
		return Trade.VILLAGER_XP[villagerLevel - 1];
	}
	
	static <T> @NotNull T random(@NotNull List<T> list, @NotNull RandomSource rng) {
		return list.get(rng.nextInt(list.size()));
	}
}
