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

import com.mojang.blaze3d.systems.RenderSystem;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.DoubleRangeOption;
import net.luis.xsurvive.client.gui.screens.EnderChestScreen;
import net.luis.xsurvive.client.gui.screens.SmeltingFurnaceScreen;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEventHandler {
	
	@SubscribeEvent
	public static void clientSetup(@NotNull FMLClientSetupEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		event.enqueueWork(() -> {
			MenuScreens.register(XSMenuTypes.SMELTING_FURNACE.get(), SmeltingFurnaceScreen::new);
			MenuScreens.register(XSMenuTypes.ENDER_CHEST.get(), EnderChestScreen::new);
		});
		replaceGammaOption(minecraft);
		XSurvive.LOGGER.debug("Replaced gamma option");
		replaceGlintOptions(minecraft);
		XSurvive.LOGGER.debug("Replaced glint options");
		XSurvive.LOGGER.info("Reload options");
		minecraft.options.load();
		XSurvive.LOGGER.info("Gamma is now {}", minecraft.options.gamma.get());
		XSurvive.LOGGER.info("Glint speed is now {}", minecraft.options.glintSpeed.get());
		XSurvive.LOGGER.info("Glint strength is now {}", minecraft.options.glintStrength.get());
		setBlockRenderTypes();
	}
	
	private static void replaceGammaOption(@NotNull Minecraft minecraft) {
		minecraft.options.gamma = new OptionInstance<>("options.gamma", OptionInstance.noTooltip(), (component, value) -> {
			int gamma = (int) (value * 100.0);
			if (gamma == 0) {
				return Options.genericValueLabel(component, Component.translatable("options.gamma.min"));
			} else if (gamma == 50) {
				return Options.genericValueLabel(component, Component.translatable("options.gamma.default"));
			} else if (gamma == 99) {
				return Options.genericValueLabel(component, Component.translatable("options.gamma.max"));
			} else if (gamma >= 100) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".gamma.infinite"));
			} else {
				return Options.genericValueLabel(component, gamma);
			}
		}, DoubleRangeOption.forGamma(0.0, 100.0), 0.5, (value) -> {});
	}
	
	private static void replaceGlintOptions(@NotNull Minecraft minecraft) {
		minecraft.options.glintSpeed = new OptionInstance<>("options.glintSpeed", OptionInstance.cachedConstantTooltip(Component.translatable("options.glintSpeed.tooltip")), (component, value) -> {
			int glint = (int) (value * 100.0);
			if (glint == 0) {
				return Options.genericValueLabel(component, CommonComponents.OPTION_OFF);
			} else if (glint == 50) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".glint.defaultVanilla"));
			} else if (glint == 75) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".glint.original"));
			} else if (glint == 100) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".glint.maxVanilla"));
			} else {
				return Options.percentValueLabel(component, value);
			}
		}, DoubleRangeOption.forGlint(0.0, 2.0), 0.5, (value) -> {});
		minecraft.options.glintStrength = new OptionInstance<>("options.glintStrength", OptionInstance.cachedConstantTooltip(Component.translatable("options.damageTiltStrength.tooltip")), (component, value) -> {
			int glint = (int) (value * 100.0);
			if (glint == 0) {
				return Options.genericValueLabel(component, CommonComponents.OPTION_OFF);
			} else if (glint == 75) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".glint.defaultVanilla"));
			} else if (glint == 100) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".glint.maxVanilla"));
			} else if (glint == 125) {
				return Options.genericValueLabel(component, Component.translatable("options." + XSurvive.MOD_ID + ".glint.original"));
			} else {
				return Options.percentValueLabel(component, value);
			}
		}, DoubleRangeOption.forGlint(0.0, 2.0), 0.75, (value) -> {
			if (RenderSystem.isOnRenderThread()) {
				RenderSystem.setShaderGlintAlpha(value);
			}
		});
	}
	
	public static void setBlockRenderTypes() {
		ItemBlockRenderTypes.setRenderLayer(XSBlocks.MYSTIC_FIRE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(XSBlocks.HONEY_MELON_STEM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(XSBlocks.ATTACHED_HONEY_MELON_STEM.get(), RenderType.cutout());
	}
}
