package net.luis.xsurvive.world.item;

import static net.luis.xsurvive.world.item.ItemCategory.AXE;
import static net.luis.xsurvive.world.item.ItemCategory.BOOTS;
import static net.luis.xsurvive.world.item.ItemCategory.BOW;
import static net.luis.xsurvive.world.item.ItemCategory.CHESTPLATE;
import static net.luis.xsurvive.world.item.ItemCategory.CROSSBOW;
import static net.luis.xsurvive.world.item.ItemCategory.ELYTRA;
import static net.luis.xsurvive.world.item.ItemCategory.ELYTRA_CHESTPLATE;
import static net.luis.xsurvive.world.item.ItemCategory.FLINT_AND_STEEL;
import static net.luis.xsurvive.world.item.ItemCategory.HELMET;
import static net.luis.xsurvive.world.item.ItemCategory.HOE;
import static net.luis.xsurvive.world.item.ItemCategory.ITEM;
import static net.luis.xsurvive.world.item.ItemCategory.LEGGINGS;
import static net.luis.xsurvive.world.item.ItemCategory.PICKAXE;
import static net.luis.xsurvive.world.item.ItemCategory.SHOVEL;
import static net.luis.xsurvive.world.item.ItemCategory.SWORD;
import static net.luis.xsurvive.world.item.ItemCategory.TRIDENT;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.minecraft.world.item.Item;

/**
 *
 * @author Luis-st
 *
 */

public enum ItemGroup {
	
	ITEMS(ITEM),
	VANISHABLE(),
	TIERED(SWORD, PICKAXE, AXE, SHOVEL, HOE),
	WEAPON(SWORD, BOW, CROSSBOW, TRIDENT, AXE),
	RANGE_WEAPON(BOW, CROSSBOW, TRIDENT),
	TOOL(PICKAXE, AXE, SHOVEL, HOE, FLINT_AND_STEEL),
	WEARABLE(HELMET, CHESTPLATE, ELYTRA, ELYTRA_CHESTPLATE, LEGGINGS, BOOTS),
	ARMOR(HELMET, CHESTPLATE, ELYTRA_CHESTPLATE, LEGGINGS, BOOTS);
	
	private final List<ItemCategory> categories;
	
	private ItemGroup(ItemCategory... categories) {
		this(Lists.newArrayList(categories));
	}
	
	private ItemGroup(List<ItemCategory> categories) {
		this.categories = categories;
	}
	
	public List<ItemCategory> getCategories() {
		return this.categories;
	}
	
	public List<Item> getItems() {
		return this.getCategories().stream().map(ItemCategory::getItems).flatMap(List::stream).collect(Collectors.toList());
	}
	
}
