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

import net.luis.xores.world.item.XOItems;
import net.luis.xsurvive.util.WeightCollection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class ItemEquipmentHelper {
	
	public static @NotNull WeightCollection<List<Item>> getWeaponWeightsForDifficulty(double difficulty) {
		WeightCollection<List<Item>> itemWeights = new WeightCollection<>();
		/*if (difficulty >= 5.5) {
			itemWeights.add(1, ItemHelper.getEnderiteWeapons());
		}*/
		if (difficulty >= 4.5) {
			itemWeights.add(10, ItemHelper.getNetheriteWeapons());
		}
		if (difficulty >= 3.5) {
			itemWeights.add(40, ItemHelper.getDiamondWeapons());
		}
		if (difficulty >= 2.75) {
			itemWeights.add(30, ItemHelper.getIronWeapons());
		}
		if (3.5 >= difficulty && difficulty >= 1.75) {
			itemWeights.add(20, ItemHelper.getStoneWeapons());
		}
		if (2.75 >= difficulty && difficulty >= 1.0) {
			itemWeights.add(10, ItemHelper.getGoldWeapons());
		}
		if (1.75 >= difficulty) {
			itemWeights.add(5, ItemHelper.getWoodWeapons());
		}
		return itemWeights;
	}
	
	public static @NotNull WeightCollection<Item> getCrossbowWeightsForDifficulty(double difficulty) {
		WeightCollection<Item> itemWeights = new WeightCollection<>();
		if (difficulty >= 5.5) {
			itemWeights.add(10, XOItems.ENDERITE_CROSSBOW.get());
		}
		if (difficulty >= 4.0) {
			itemWeights.add(30, XOItems.NETHERITE_CROSSBOW.get());
		}
		itemWeights.add(4.0 >= difficulty ? 50 : 80, Items.CROSSBOW);
		return itemWeights;
	}
	
	public static @NotNull WeightCollection<List<Item>> getArmorWeightsForDifficulty(double difficulty) {
		WeightCollection<List<Item>> itemWeights = new WeightCollection<>();
		if (difficulty >= 5.5) {
			itemWeights.add(1, ItemHelper.getEnderiteArmor());
		}
		if (difficulty >= 4.5) {
			itemWeights.add(10, ItemHelper.getNetheriteArmor());
		}
		if (difficulty >= 3.5) {
			itemWeights.add(40, ItemHelper.getDiamondArmor());
		}
		if (difficulty >= 2.75) {
			itemWeights.add(30, ItemHelper.getIronArmor());
		}
		if (3.5 >= difficulty && difficulty >= 1.75) {
			itemWeights.add(20, ItemHelper.getChainArmor());
		}
		if (2.75 >= difficulty && difficulty >= 1.0) {
			itemWeights.add(10, ItemHelper.getGoldArmor());
		}
		if (1.75 >= difficulty) {
			itemWeights.add(5, ItemHelper.getLeatherArmor());
		}
		return itemWeights;
	}
}
