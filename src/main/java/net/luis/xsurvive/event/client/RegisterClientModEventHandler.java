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

package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.gui.screens.inventory.tooltip.ClientShulkerBoxTooltip;
import net.luis.xsurvive.client.renderer.item.XSItemDecorator;
import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxContent;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterClientModEventHandler {
	
	@SubscribeEvent
	public static void registerClientTooltipComponentFactories(@NotNull RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(ShulkerBoxContent.class, ClientShulkerBoxTooltip::new);
	}
	
	@SubscribeEvent
	public static void registerColorHandlers(RegisterColorHandlersEvent.@NotNull Block event) {
		event.register((state, blockGetter, pos, i) -> {
			int age = state.getValue(StemBlock.AGE);
			int red = age * 32;
			int green = 255 - age * 8;
			int blue = age * 4;
			return red << 16 | green << 8 | blue;
		}, XSBlocks.HONEY_MELON_STEM.get());
		event.register((state, blockGetter, pos, i) -> 14731036, XSBlocks.ATTACHED_HONEY_MELON_STEM.get());
	}
	
	@SubscribeEvent
	public static void registerItemDecorations(@NotNull RegisterItemDecorationsEvent event) {
		for (Item item : ForgeRegistries.ITEMS) {
			event.register(item, new XSItemDecorator(Minecraft.getInstance()));
		}
	}
}
