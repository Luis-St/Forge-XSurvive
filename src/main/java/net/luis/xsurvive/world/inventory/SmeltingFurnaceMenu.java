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

package net.luis.xsurvive.world.inventory;

import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingFurnaceMenu extends AbstractFurnaceMenu {
	
	public SmeltingFurnaceMenu(int id, @NotNull Inventory inventory) {
		super(XSMenuTypes.SMELTING_FURNACE.get(), XSRecipeTypes.SMELTING.get(), XSRecipeBookTypes.SMELTING, id, inventory);
	}
	
	public SmeltingFurnaceMenu(int id, @NotNull Inventory inventory, @NotNull Container container, @NotNull ContainerData data) {
		super(XSMenuTypes.SMELTING_FURNACE.get(), XSRecipeTypes.SMELTING.get(), XSRecipeBookTypes.SMELTING, id, inventory, container, data);
	}
}
