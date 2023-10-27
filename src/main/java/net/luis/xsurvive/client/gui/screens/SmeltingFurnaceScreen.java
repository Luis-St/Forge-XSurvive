package net.luis.xsurvive.client.gui.screens;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
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
	private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/blast_furnace/lit_progress");
	private static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/blast_furnace/burn_progress");
	
	public SmeltingFurnaceScreen(SmeltingFurnaceMenu smeltingMenu, Inventory inventory, Component component) {
		super(smeltingMenu, new SmeltingRecipeBookComponent(), inventory, component, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
	}
}
