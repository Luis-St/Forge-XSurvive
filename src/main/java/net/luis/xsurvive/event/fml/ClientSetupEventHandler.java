package net.luis.xsurvive.event.fml;

import com.mojang.blaze3d.systems.RenderSystem;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.DoubleRangeOption;
import net.luis.xsurvive.client.gui.screens.EnderChestScreen;
import net.luis.xsurvive.client.gui.screens.SmeltingFurnaceScreen;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.MenuScreens;
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
 * @author Luis-st
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
		XSurvive.LOGGER.info("Replaced gamma option");
		replaceGlintOptions(minecraft);
		XSurvive.LOGGER.info("Replaced glint options");
		XSurvive.LOGGER.debug("Reload options");
		minecraft.options.load();
		XSurvive.LOGGER.info("Gamma is now {}", minecraft.options.gamma.get());
		XSurvive.LOGGER.info("Glint speed is now {}", minecraft.options.glintSpeed.get());
		XSurvive.LOGGER.info("Glint strength is now {}", minecraft.options.glintStrength.get());
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
		}, DoubleRangeOption.forGamma(0.0, 100.0), 0.5, (value) -> {
			
		});
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
		}, DoubleRangeOption.forGlint(0.0, 2.0), 0.5, (value) -> {
		
		});
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
}
