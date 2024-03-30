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

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSMenuTypes {
	
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, XSurvive.MOD_ID);
	
	public static final RegistryObject<MenuType<SmeltingFurnaceMenu>> SMELTING_FURNACE = MENU_TYPES.register("smelting_furnace", () -> {
		return IForgeMenuType.create((id, inventory, data) -> {
			return new SmeltingFurnaceMenu(id, inventory);
		});
	});
	public static final RegistryObject<MenuType<EnderChestMenu>> ENDER_CHEST = MENU_TYPES.register("ender_chest", () -> {
		return IForgeMenuType.create((id, inventory, data) -> {
			return new EnderChestMenu(id, inventory);
		});
	});
}
