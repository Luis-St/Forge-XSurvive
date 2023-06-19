package net.luis.xsurvive.world.item;

import net.luis.xores.world.item.XOItems;
import net.luis.xsurvive.util.WeightCollection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class ItemWeightHelper {
	
	public static WeightCollection<List<Item>> getWeaponWeightsForDifficulty(double difficulty) {
		WeightCollection<List<Item>> itemWeights = new WeightCollection<>();
		if (difficulty >= 5.5) {
			itemWeights.add(1, ItemHelper.getEnderiteWeapons());
		}
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
	
	public static WeightCollection<Item> getCrossbowWeightsForDifficulty(double difficulty) {
		WeightCollection<Item> itemWeights = new WeightCollection<>();
		itemWeights.add(10, XOItems.NIGHT_CROSSBOW.get());
		itemWeights.add(30, XOItems.ENDERITE_CROSSBOW.get());
		itemWeights.add(60, Items.CROSSBOW);
		return itemWeights;
	}
	
	public static WeightCollection<List<Item>> getArmorWeightsForDifficulty(double difficulty) {
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
