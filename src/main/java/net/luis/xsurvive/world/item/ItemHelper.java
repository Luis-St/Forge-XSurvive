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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.luis.xsurvive.world.item.XSItems.*;

/**
 *
 * @author Luis-St
 *
 */

public class ItemHelper {  // ToDo: Add when xores is updated
	
	public static @NotNull List<Item> getLeatherArmor() {
		return Lists.newArrayList(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);
	}
	
	public static @NotNull List<Item> getGoldArmor() {
		return Lists.newArrayList(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
	}
	
	public static @NotNull List<Item> getChainArmor() {
		return Lists.newArrayList(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS);
	}
	
	public static @NotNull List<Item> getIronArmor() {
		return Lists.newArrayList(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
	}
	
	public static @NotNull List<Item> getDiamondArmor() {
		return Lists.newArrayList(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
	}
	
	public static @NotNull List<Item> getNetheriteArmor() {
		return Lists.newArrayList(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);
	}
	
	/*public static @NotNull List<Item> getEnderiteArmor() {
		List<Item> items = Lists.newArrayList();
		items.add(XOItems.ENDERITE_HELMET.get());
		items.add(XOItems.ENDERITE_CHESTPLATE.get());
		items.add(XOItems.ENDERITE_LEGGINGS.get());
		items.add(XOItems.ENDERITE_BOOTS.get());
		return items;
	}*/
	
	public static @NotNull List<Item> getWoodWeapons() {
		return Lists.newArrayList(Items.WOODEN_SWORD, Items.WOODEN_AXE);
	}
	
	public static @NotNull List<Item> getGoldWeapons() {
		return Lists.newArrayList(Items.GOLDEN_SWORD, Items.GOLDEN_AXE);
	}
	
	public static @NotNull List<Item> getStoneWeapons() {
		return Lists.newArrayList(Items.STONE_SWORD, Items.STONE_AXE);
	}
	
	public static @NotNull List<Item> getIronWeapons() {
		return Lists.newArrayList(Items.IRON_SWORD, Items.IRON_AXE);
	}
	
	public static @NotNull List<Item> getDiamondWeapons() {
		return Lists.newArrayList(Items.DIAMOND_SWORD, Items.DIAMOND_AXE);
	}
	
	public static @NotNull List<Item> getNetheriteWeapons() {
		return Lists.newArrayList(Items.NETHERITE_SWORD, Items.NETHERITE_AXE);
	}
	
	/*public static @NotNull List<Item> getEnderiteWeapons() {
		List<Item> items = Lists.newArrayList();
		items.add(XOItems.ENDERITE_SWORD.get());
		items.add(XOItems.ENDERITE_AXE.get());
		return items;
	}*/
}
