package net.luis.xsurvive.world.item;

import static net.luis.xsurvive.world.item.XSItems.BLACK_RUNE;
import static net.luis.xsurvive.world.item.XSItems.BLUE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.BROWN_RUNE;
import static net.luis.xsurvive.world.item.XSItems.CYAN_RUNE;
import static net.luis.xsurvive.world.item.XSItems.GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSItems.GREEN_RUNE;
import static net.luis.xsurvive.world.item.XSItems.LIGHT_BLUE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.LIGHT_GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSItems.LIME_RUNE;
import static net.luis.xsurvive.world.item.XSItems.MAGENTA_RUNE;
import static net.luis.xsurvive.world.item.XSItems.ORANGE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.PINK_RUNE;
import static net.luis.xsurvive.world.item.XSItems.PURPLE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.RAINBOW_RUNE;
import static net.luis.xsurvive.world.item.XSItems.RED_RUNE;
import static net.luis.xsurvive.world.item.XSItems.WHITE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.YELLOW_RUNE;

import java.util.List;

import com.google.common.collect.Lists;

import net.luis.xsurvive.dependency.DependencyItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * 
 * @author Luis-st
 *
 */

public class ItemHelper {
	
	public static List<GlintColorItem> getRunes() {
		return Lists.newArrayList(WHITE_RUNE.get(), GRAY_RUNE.get(), LIGHT_GRAY_RUNE.get(), BROWN_RUNE.get(), BLACK_RUNE.get(), ORANGE_RUNE.get(), MAGENTA_RUNE.get(), LIGHT_BLUE_RUNE.get(), YELLOW_RUNE.get(), LIME_RUNE.get(), PINK_RUNE.get(),
			CYAN_RUNE.get(), PURPLE_RUNE.get(), BLUE_RUNE.get(), GREEN_RUNE.get(), RED_RUNE.get(), RAINBOW_RUNE.get());
	}
	
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
		List<Item> items = Lists.newArrayList();
		if (DependencyItems.ENDERITE_HELMET.isPresent()) {
			items.add(DependencyItems.ENDERITE_HELMET.get());
		}
		if (DependencyItems.ENDERITE_CHESTPLATE.isPresent()) {
			items.add(DependencyItems.ENDERITE_CHESTPLATE.get());
		}
		if (DependencyItems.ENDERITE_LEGGINGS.isPresent()) {
			items.add(DependencyItems.ENDERITE_LEGGINGS.get());
		}
		if (DependencyItems.ENDERITE_BOOTS.isPresent()) {
			items.add(DependencyItems.ENDERITE_BOOTS.get());
		}
		if (!items.isEmpty()) {
			return items;
		} else {
			return getNetheriteArmor();
		}
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
		List<Item> items = Lists.newArrayList();
		if (DependencyItems.ENDERITE_SWORD.isPresent()) {
			items.add(DependencyItems.ENDERITE_SWORD.get());
		}
		if (DependencyItems.ENDERITE_AXE.isPresent()) {
			items.add(DependencyItems.ENDERITE_AXE.get());
		}
		if (!items.isEmpty()) {
			return items;
		} else {
			return getNetheriteWeapons();
		}
	}
	
}
