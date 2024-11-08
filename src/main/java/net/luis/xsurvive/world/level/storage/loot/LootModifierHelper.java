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
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import static net.luis.xsurvive.world.item.XSItems.*;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.*;
import static net.minecraft.world.item.enchantment.Enchantments.*;

/**
 *
 * @author Luis-St
 *
 */

public class LootModifierHelper {
	
	// ToDO: Add Mace enchantments
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getCommonEnchantments() {
		return RarityList.of(Rarity.COMMON, Lists.newArrayList(FIRE_PROTECTION, BLAST_PROTECTION, PROJECTILE_PROTECTION, BANE_OF_ARTHROPODS, PUNCH, KNOCKBACK, BLASTING));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getRareEnchantments() {
		return RarityList.of(Rarity.RARE, Lists.newArrayList(PROTECTION, SHARPNESS, SMITE, ENDER_SLAYER, EFFICIENCY, POWER, GROWTH, PIERCING, FIRE_ASPECT, FROST_ASPECT, POISON_ASPECT));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getVeryRareEnchantments() {
		return RarityList.of(Rarity.VERY_RARE, Lists.newArrayList(FEATHER_FALLING, RESPIRATION, DEPTH_STRIDER, SWEEPING_EDGE, UNBREAKING, FORTUNE, LOOTING, LOYALTY, RIPTIDE, QUICK_CHARGE, LUCK_OF_THE_SEA, LURE, REPLANTING));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getTreasureEnchantments() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(MULTI_DROP, REACHING));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getExtraOverworldTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(SWIFT_SNEAK));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getExtraNetherTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(SOUL_SPEED));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getExtraEndTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(VOID_WALKER, VOID_PROTECTION, ASPECT_OF_THE_END));
	}
	
	public static @NotNull RarityList<ResourceKey<Enchantment>> getAllTreasureEnchantments() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(MULTI_DROP, SWIFT_SNEAK, SOUL_SPEED, VOID_PROTECTION, ASPECT_OF_THE_END));
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
}
