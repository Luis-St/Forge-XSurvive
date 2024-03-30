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

package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.dependency.DependencyCallWrapper;
import net.luis.xsurvive.world.entity.ai.custom.*;
import net.luis.xsurvive.world.item.alchemy.BrewingRecipeUtils;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class CommonSetupEventHandler {
	
	@SubscribeEvent
	public static void commonSetup(@NotNull FMLCommonSetupEvent event) {
		DependencyCallWrapper.wrapCommonSetup();
		event.enqueueWork(() -> {
			registerBrewingRecipes();
			registerCustomAis();
			replaceEntityDimensions();
		});
	}
	
	private static void registerBrewingRecipes() {
		BrewingRecipeUtils.addRecipes(Items.SNOWBALL, XSPotions.FROST.get(), XSPotions.LONG_FROST.get(), XSPotions.STRONG_FROST.get());
		BrewingRecipeUtils.addRecipes(Items.WITHER_ROSE, XSPotions.WITHER.get(), XSPotions.LONG_WITHER.get(), XSPotions.STRONG_WITHER.get());
		BrewingRecipeUtils.addRecipes(Items.IRON_PICKAXE, XSPotions.DIG_SPEED.get(), XSPotions.LONG_DIG_SPEED.get(), XSPotions.STRONG_DIG_SPEED.get());
	}
	
	private static void registerCustomAis() {
		CustomAiManager.INSTANCE.register(ElderGuardian.class, CustomElderGuardianAi::new);
		CustomAiManager.INSTANCE.register(EnderMan.class, CustomEnderManAi::new);
		CustomAiManager.INSTANCE.register(ZombifiedPiglin.class, CustomZombifiedPiglinAi::new);
	}
	
	private static void replaceEntityDimensions() {
		EntityType.SHULKER_BULLET.dimensions = EntityDimensions.scalable(0.45F, 0.45F);
	}
}
