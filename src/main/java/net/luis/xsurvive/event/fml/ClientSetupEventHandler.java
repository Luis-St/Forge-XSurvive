package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.DoubleRangeOption;
import net.luis.xsurvive.client.gui.screens.EnderChestScreen;
import net.luis.xsurvive.client.gui.screens.SmeltingFurnaceScreen;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEventHandler {
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		event.enqueueWork(() -> {
			MenuScreens.register(XSMenuTypes.SMELTING_FURNACE.get(), SmeltingFurnaceScreen::new);
			MenuScreens.register(XSMenuTypes.ENDER_CHEST.get(), EnderChestScreen::new);
		});
		replaceGammaOption(minecraft);
		XSurvive.LOGGER.info("Replace gamma option and reload options");
		minecraft.options.load();
		XSurvive.LOGGER.info("Gamma value is now {}", minecraft.options.gamma.get());
	}
	
	private static void replaceGammaOption(Minecraft minecraft) {
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
		}, new DoubleRangeOption(0.0, 100.0), 0.5, (value) -> {
			
		});
	}
	
}
