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

package net.luis.xsurvive.world.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.xores.world.item.XOItems;
import net.luis.xsurvive.util.WeightCollection;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

class ItemEquipmentHelper {
	
	//region Weights
	public static @NotNull WeightCollection<List<Item>> getWeaponWeights(@NotNull Difficulty difficulty) {
		WeightCollection<List<Item>> itemWeights = new WeightCollection<>();
		if (difficulty == Difficulty.HARD) {
			itemWeights.add(10, getEnderiteWeapons());
			itemWeights.add(20, getNetheriteWeapons());
			itemWeights.add(35, getDiamondWeapons());
		}
		itemWeights.add(50, getIronWeapons());
		itemWeights.add(difficulty == Difficulty.HARD ? 35 : 50, getStoneWeapons());
		itemWeights.add(difficulty == Difficulty.HARD ? 15 : 25, getGoldWeapons());
		itemWeights.add(1, getWoodWeapons());
		return itemWeights;
	}
	
	public static @NotNull WeightCollection<Item> getBowWeights(@NotNull Difficulty difficulty) {
		WeightCollection<Item> itemWeights = new WeightCollection<>();
		if (difficulty == Difficulty.HARD) {
			itemWeights.add(5, XOItems.ENDERITE_BOW.get());
			itemWeights.add(10, XOItems.NETHERITE_BOW.get());
		}
		itemWeights.add(90, Items.BOW);
		return itemWeights;
	}
	
	public static @NotNull WeightCollection<Item> getCrossbowWeights(@NotNull Difficulty difficulty) {
		WeightCollection<Item> itemWeights = new WeightCollection<>();
		if (difficulty == Difficulty.HARD) {
			itemWeights.add(5, XOItems.ENDERITE_CROSSBOW.get());
			itemWeights.add(10, XOItems.NETHERITE_CROSSBOW.get());
		}
		itemWeights.add(90, Items.CROSSBOW);
		return itemWeights;
	}
	
	public static @NotNull WeightCollection<Map<EquipmentSlot, Item>> getArmorWeights(@NotNull Difficulty difficulty) {
		WeightCollection<Map<EquipmentSlot, Item>> itemWeights = new WeightCollection<>();
		if (difficulty == Difficulty.HARD) {
			itemWeights.add(10, getEnderiteArmor());
			itemWeights.add(20, getNetheriteArmor());
			itemWeights.add(35, getDiamondArmor());
		}
		itemWeights.add(50, getIronArmor());
		itemWeights.add(difficulty == Difficulty.HARD ? 35 : 50, getChainArmor());
		itemWeights.add(difficulty == Difficulty.HARD ? 15 : 25, getGoldArmor());
		itemWeights.add(1, getLeatherArmor());
		return itemWeights;
	}
	//endregion
	
	//region Armor
	private static @NotNull Map<EquipmentSlot, Item> getLeatherArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, Items.LEATHER_HELMET);
		items.put(EquipmentSlot.CHEST, Items.LEATHER_CHESTPLATE);
		items.put(EquipmentSlot.LEGS, Items.LEATHER_LEGGINGS);
		items.put(EquipmentSlot.FEET, Items.LEATHER_BOOTS);
		return items;
	}
	
	private static @NotNull Map<EquipmentSlot, Item> getGoldArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, Items.GOLDEN_HELMET);
		items.put(EquipmentSlot.CHEST, Items.GOLDEN_CHESTPLATE);
		items.put(EquipmentSlot.LEGS, Items.GOLDEN_LEGGINGS);
		items.put(EquipmentSlot.FEET, Items.GOLDEN_BOOTS);
		return items;
	}
	
	private static @NotNull Map<EquipmentSlot, Item> getChainArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, Items.CHAINMAIL_HELMET);
		items.put(EquipmentSlot.CHEST, Items.CHAINMAIL_CHESTPLATE);
		items.put(EquipmentSlot.LEGS, Items.CHAINMAIL_LEGGINGS);
		items.put(EquipmentSlot.FEET, Items.CHAINMAIL_BOOTS);
		return items;
	}
	
	private static @NotNull Map<EquipmentSlot, Item> getIronArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, Items.IRON_HELMET);
		items.put(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE);
		items.put(EquipmentSlot.LEGS, Items.IRON_LEGGINGS);
		items.put(EquipmentSlot.FEET, Items.IRON_BOOTS);
		return items;
	}
	
	private static @NotNull Map<EquipmentSlot, Item> getDiamondArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, Items.DIAMOND_HELMET);
		items.put(EquipmentSlot.CHEST, Items.DIAMOND_CHESTPLATE);
		items.put(EquipmentSlot.LEGS, Items.DIAMOND_LEGGINGS);
		items.put(EquipmentSlot.FEET, Items.DIAMOND_BOOTS);
		return items;
	}
	
	private static @NotNull Map<EquipmentSlot, Item> getNetheriteArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, Items.NETHERITE_HELMET);
		items.put(EquipmentSlot.CHEST, Items.NETHERITE_CHESTPLATE);
		items.put(EquipmentSlot.LEGS, Items.NETHERITE_LEGGINGS);
		items.put(EquipmentSlot.FEET, Items.NETHERITE_BOOTS);
		return items;
	}
	
	private static @NotNull Map<EquipmentSlot, Item> getEnderiteArmor() {
		Map<EquipmentSlot, Item> items = Maps.newHashMap();
		items.put(EquipmentSlot.HEAD, XOItems.ENDERITE_HELMET.get());
		items.put(EquipmentSlot.CHEST, XOItems.ENDERITE_CHESTPLATE.get());
		items.put(EquipmentSlot.LEGS, XOItems.ENDERITE_LEGGINGS.get());
		items.put(EquipmentSlot.FEET, XOItems.ENDERITE_BOOTS.get());
		return items;
	}
	//endregion
	
	//region Weapons
	private static @NotNull List<Item> getWoodWeapons() {
		return Lists.newArrayList(Items.WOODEN_SWORD, Items.WOODEN_AXE);
	}
	
	private static @NotNull List<Item> getGoldWeapons() {
		return Lists.newArrayList(Items.GOLDEN_SWORD, Items.GOLDEN_AXE);
	}
	
	private static @NotNull List<Item> getStoneWeapons() {
		return Lists.newArrayList(Items.STONE_SWORD, Items.STONE_AXE);
	}
	
	private static @NotNull List<Item> getIronWeapons() {
		return Lists.newArrayList(Items.IRON_SWORD, Items.IRON_AXE);
	}
	
	private static @NotNull List<Item> getDiamondWeapons() {
		return Lists.newArrayList(Items.DIAMOND_SWORD, Items.DIAMOND_AXE);
	}
	
	private static @NotNull List<Item> getNetheriteWeapons() {
		return Lists.newArrayList(Items.NETHERITE_SWORD, Items.NETHERITE_AXE);
	}
	
	private static @NotNull List<Item> getEnderiteWeapons() {
		return Lists.newArrayList(XOItems.ENDERITE_SWORD.get(), XOItems.ENDERITE_AXE.get());
	}
	//endregion
}
