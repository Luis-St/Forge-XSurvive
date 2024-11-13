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

package net.luis.xsurvive.world.level.block.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
	
	public SmeltingFurnaceBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(XSBlockEntityTypes.SMELTING_FURNACE.get(), pos, state, XSRecipeTypes.SMELTING.get());
	}
	
	@Override
	protected @NotNull Component getDefaultName() {
		return Component.translatable(XSurvive.MOD_ID + ".container.smelting_furnace");
	}
	
	@Override
	protected int getBurnDuration(@NotNull ItemStack stack) {
		return super.getBurnDuration(stack) / 2;
	}
	
	@Override
	protected @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory) {
		return new SmeltingFurnaceMenu(id, inventory, this, this.dataAccess);
	}
}
