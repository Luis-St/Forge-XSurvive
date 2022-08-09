package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.gui.screens.inventory.tooltip.ClientShulkerBoxTooltip;
import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxTooltip;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnRegisterClientTooltipComponentFactoriesEvent {
	
	@SubscribeEvent
	public static void registerClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(ShulkerBoxTooltip.class, (tooltip) -> {
			return new ClientShulkerBoxTooltip(tooltip);
		});
	}
	
}
