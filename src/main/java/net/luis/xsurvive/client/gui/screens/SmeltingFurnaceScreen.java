package net.luis.xsurvive.client.gui.screens;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingFurnaceScreen extends AbstractFurnaceScreen<SmeltingFurnaceMenu> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(XSurvive.MOD_ID, "textures/gui/container/smelting_furnace.png");
	
	public SmeltingFurnaceScreen(SmeltingFurnaceMenu smeltingMenu, Inventory inventory, Component component) {
		super(smeltingMenu, new SmeltingRecipeBookComponent(), inventory, component, TEXTURE);
	}
}
