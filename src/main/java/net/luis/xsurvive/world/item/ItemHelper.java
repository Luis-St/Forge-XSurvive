package net.luis.xsurvive.world.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.luis.xores.world.item.XOItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * 
 * @author Luis-st
 *
 */

public class ItemHelper {
	
	public static List<Item> getLeatherArmor() {
		return Lists.newArrayList(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);
	}
	
	public static List<Item> getGoldArmor() {
		return Lists.newArrayList(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
	}
	
	public static List<Item> getChainArmor() {
		return Lists.newArrayList(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS);
	}
	
	public static List<Item> getIronArmor() {
		return Lists.newArrayList(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
	}
	
	public static List<Item> getDiamondArmor() {
		return Lists.newArrayList(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
	}
	
	public static List<Item> getNetheriteArmor() {
		return Lists.newArrayList(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);
	}
	
	public static List<Item> getEnderiteArmor() {
		return Lists.newArrayList(XOItems.ENDERITE_HELMET.get(), XOItems.ENDERITE_CHESTPLATE.get(), XOItems.ENDERITE_LEGGINGS.get(), XOItems.ENDERITE_BOOTS.get());
	}
	
	public static List<Item> getWoodWeapons() {
		return Lists.newArrayList(Items.WOODEN_SWORD, Items.WOODEN_AXE);
	}
	
	public static List<Item> getGoldWeapons() {
		return Lists.newArrayList(Items.GOLDEN_SWORD, Items.GOLDEN_AXE);
	}
	
	public static List<Item> getStoneWeapons() {
		return Lists.newArrayList(Items.STONE_SWORD, Items.STONE_AXE);
	}
	
	public static List<Item> getIronWeapons() {
		return Lists.newArrayList(Items.IRON_SWORD, Items.IRON_AXE);
	}
	
	public static List<Item> getDiamondWeapons() {
		return Lists.newArrayList(Items.DIAMOND_SWORD, Items.DIAMOND_AXE);
	}
	
	public static List<Item> getNetheriteWeapons() {
		return Lists.newArrayList(Items.NETHERITE_SWORD, Items.NETHERITE_AXE);
	}
	
	public static List<Item> getEnderiteWeapons() {
		return Lists.newArrayList(XOItems.ENDERITE_SWORD.get(), XOItems.ENDERITE_AXE.get());
	}
	
}
