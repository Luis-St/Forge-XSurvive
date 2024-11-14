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

package net.luis.xsurvive.event.registry;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.server.commands.TestCommand;
import net.luis.xsurvive.world.item.XSItemCooldowns;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.commands.RaidCommand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraftforge.event.GatherComponentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class RegisterEventHandler {
	
	@SubscribeEvent
	public static void registerCommands(@NotNull RegisterCommandsEvent event) {
		RaidCommand.register(event.getDispatcher(), event.getBuildContext());
		TestCommand.register(event.getDispatcher());
	}
	
	@SubscribeEvent
	public static void brewingRecipeRegister(@NotNull BrewingRecipeRegisterEvent event) {
		PotionBrewing.Builder builder = event.getBuilder();
		builder.addStartMix(Items.SNOWBALL, XSPotions.FROST.getHolder().orElseThrow());
		builder.addMix(XSPotions.FROST.getHolder().orElseThrow(), Items.REDSTONE, XSPotions.LONG_FROST.getHolder().orElseThrow());
		builder.addMix(XSPotions.FROST.getHolder().orElseThrow(), Items.GLOWSTONE_DUST, XSPotions.STRONG_FROST.getHolder().orElseThrow());
		
		builder.addStartMix(Items.WITHER_ROSE, XSPotions.WITHER.getHolder().orElseThrow());
		builder.addMix(XSPotions.WITHER.getHolder().orElseThrow(), Items.REDSTONE, XSPotions.LONG_WITHER.getHolder().orElseThrow());
		builder.addMix(XSPotions.WITHER.getHolder().orElseThrow(), Items.GLOWSTONE_DUST, XSPotions.STRONG_WITHER.getHolder().orElseThrow());
		
		builder.addStartMix(Items.IRON_PICKAXE, XSPotions.DIG_SPEED.getHolder().orElseThrow());
		builder.addMix(XSPotions.DIG_SPEED.getHolder().orElseThrow(), Items.REDSTONE, XSPotions.LONG_DIG_SPEED.getHolder().orElseThrow());
		builder.addMix(XSPotions.DIG_SPEED.getHolder().orElseThrow(), Items.GLOWSTONE_DUST, XSPotions.STRONG_DIG_SPEED.getHolder().orElseThrow());
	}
	
	@SubscribeEvent
	public static void gatherItemComponentsRegister(GatherComponentsEvent.@NotNull Item event) {
		if (event.getOwner() == Items.ENDER_EYE) {
			event.register(DataComponents.USE_COOLDOWN, new UseCooldown(60, Optional.of(XSItemCooldowns.EYE_OF_ENDER_COOLDOWN)));
		}
	}
}
