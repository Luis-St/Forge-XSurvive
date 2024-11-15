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

package net.luis.xsurvive.world.level.storage.loot;

import com.google.common.collect.Lists;
import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.util.RarityList;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Function;

import static net.luis.xsurvive.world.item.XSItems.*;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.*;
import static net.minecraft.world.item.enchantment.Enchantments.*;

/**
 *
 * @author Luis-St
 *
 */

public class LootModifierHelper {
	
	public static @NotNull RarityList<Holder<Enchantment>> getCommonEnchantments(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.COMMON, createList(
			lookup.apply(FIRE_PROTECTION),
			lookup.apply(BLAST_PROTECTION),
			lookup.apply(PROJECTILE_PROTECTION),
			lookup.apply(BANE_OF_ARTHROPODS),
			lookup.apply(PUNCH),
			lookup.apply(KNOCKBACK),
			lookup.apply(BLASTING),
			lookup.apply(THORNS),
			lookup.apply(FLAME)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getRareEnchantments(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.RARE, createList(
			lookup.apply(PROTECTION),
			lookup.apply(SHARPNESS),
			lookup.apply(SMITE),
			lookup.apply(ENDER_SLAYER),
			lookup.apply(EFFICIENCY),
			lookup.apply(POWER),
			lookup.apply(GROWTH),
			lookup.apply(PIERCING),
			lookup.apply(FIRE_ASPECT),
			lookup.apply(FROST_ASPECT),
			lookup.apply(POISON_ASPECT),
			lookup.apply(MULTISHOT),
			lookup.apply(RESPIRATION),
			lookup.apply(AQUA_AFFINITY),
			lookup.apply(SWEEPING_EDGE),
			lookup.apply(QUICK_CHARGE),
			lookup.apply(RIPTIDE),
			lookup.apply(IMPALING),
			lookup.apply(FROST_WALKER)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getVeryRareEnchantments(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.VERY_RARE, createList(
			lookup.apply(FEATHER_FALLING),
			lookup.apply(DEPTH_STRIDER),
			lookup.apply(UNBREAKING),
			lookup.apply(FORTUNE),
			lookup.apply(LOOTING),
			lookup.apply(LOYALTY),
			lookup.apply(LUCK_OF_THE_SEA),
			lookup.apply(LURE),
			lookup.apply(REPLANTING),
			lookup.apply(CHANNELING),
			lookup.apply(SILK_TOUCH),
			lookup.apply(DENSITY),
			lookup.apply(BREACH)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getTreasureEnchantments(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.TREASURE, createList(
			lookup.apply(MENDING),
			lookup.apply(INFINITY),
			lookup.apply(MULTI_DROP),
			lookup.apply(REACHING)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getExtraOverworldTreasure(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.TREASURE, createList(
			lookup.apply(SWIFT_SNEAK),
			lookup.apply(WIND_BURST)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getExtraNetherTreasure(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.TREASURE, createList(
			lookup.apply(SOUL_SPEED)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getExtraEndTreasure(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.TREASURE, createList(
			lookup.apply(VOID_WALKER),
			lookup.apply(VOID_PROTECTION),
			lookup.apply(ASPECT_OF_THE_END)
		));
	}
	
	public static @NotNull RarityList<Holder<Enchantment>> getAllTreasureEnchantments(@NotNull Function<ResourceKey<Enchantment>, Holder<Enchantment>> lookup) {
		return RarityList.of(Rarity.TREASURE, createList(
			lookup.apply(MULTI_DROP),
			lookup.apply(SWIFT_SNEAK),
			lookup.apply(SOUL_SPEED),
			lookup.apply(VOID_PROTECTION),
			lookup.apply(ASPECT_OF_THE_END)
		));
	}
	
	public static @NotNull RarityList<Item> getCommonRunes() {
		return RarityList.of(Rarity.COMMON, Lists.newArrayList(WHITE_RUNE.get(), GRAY_RUNE.get(), LIGHT_GRAY_RUNE.get(), BROWN_RUNE.get(), BLACK_RUNE.get()));
	}
	
	public static @NotNull RarityList<Item> getRareRunes() {
		return RarityList.of(Rarity.RARE, Lists.newArrayList(ORANGE_RUNE.get(), MAGENTA_RUNE.get(), LIGHT_BLUE_RUNE.get(), YELLOW_RUNE.get(), LIME_RUNE.get(), PINK_RUNE.get(), CYAN_RUNE.get(), PURPLE_RUNE.get(), BLUE_RUNE.get(), GREEN_RUNE.get(), RED_RUNE.get()));
	}
	
	public static @NotNull RarityList<Item> getTreasureRunes() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(RAINBOW_RUNE.get()));
	}
	
	@SafeVarargs
	private static @NotNull @Unmodifiable List<Holder<Enchantment>> createList(Holder<Enchantment> @NotNull... enchantments) {
		// Clears out all non-golden or upgradeable enchantments, so we can track all enchantments above
		return Lists.newArrayList(enchantments).stream().filter(GoldenEnchantmentHelper::isEnchantment).toList();
	}
}
