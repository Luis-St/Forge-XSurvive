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
	private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/blast_furnace/lit_progress");
	private static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/blast_furnace/burn_progress");
	
	public SmeltingFurnaceScreen(SmeltingFurnaceMenu smeltingMenu, Inventory inventory, Component component) {
		super(smeltingMenu, new SmeltingRecipeBookComponent(), inventory, component, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
	}
}
