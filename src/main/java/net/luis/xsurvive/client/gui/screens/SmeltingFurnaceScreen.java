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
import net.luis.xsurvive.client.XSSearchRecipeBookCategory;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.luis.xsurvive.world.item.crafting.XSRecipeBookCategories;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingFurnaceScreen extends AbstractFurnaceScreen<SmeltingFurnaceMenu> {
	
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "textures/gui/container/smelting_furnace.png");
	private static final ResourceLocation LIT_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("container/blast_furnace/lit_progress");
	private static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("container/blast_furnace/burn_progress");
	private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.smeltable");
	private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
		new RecipeBookComponent.TabInfo(new ItemStack(Items.COMPASS), Optional.empty(), XSSearchRecipeBookCategory.SMELTING),
		new RecipeBookComponent.TabInfo(Items.DEEPSLATE, XSRecipeBookCategories.XSMELTING_FURNACE_BLOCKS.get()),
		new RecipeBookComponent.TabInfo(Items.BRICK, XSRecipeBookCategories.XSMELTING_FURNACE_MISC.get())
	);
	
	public SmeltingFurnaceScreen(@NotNull SmeltingFurnaceMenu smeltingMenu, @NotNull Inventory inventory, @NotNull Component component) {
		super(smeltingMenu, inventory, component, FILTER_NAME, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE, TABS);
	}
}
