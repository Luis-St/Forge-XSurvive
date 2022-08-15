package net.luis.xsurvive.world.inventory;

import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;

/**
 * 
 * @author Luis-st
 *
 */

public class SmeltingFurnaceMenu extends AbstractFurnaceMenu {

	public SmeltingFurnaceMenu(int id, Inventory inventory, FriendlyByteBuf data) {
		super(XSMenuTypes.SMELTING_FURNACE.get(), XSRecipeTypes.SMELTING.get(), XSRecipeBookTypes.SMELTING, id, inventory);
	}
	
	public SmeltingFurnaceMenu(int id, Inventory inventory, Container container, ContainerData data) {
		super(XSMenuTypes.SMELTING_FURNACE.get(), XSRecipeTypes.SMELTING.get(), XSRecipeBookTypes.SMELTING, id, inventory, container, data);
	}

}
