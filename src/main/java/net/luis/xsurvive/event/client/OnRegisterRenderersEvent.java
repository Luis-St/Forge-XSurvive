package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.XSEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnRegisterRenderersEvent {
	
	@SubscribeEvent
	public static void registerClientCommands(RegisterRenderers event) {
		event.registerEntityRenderer(XSEntityTypes.CURSED_ENDER_EYE.get(), (context) -> {
			return new ThrownItemRenderer<>(context, 1.0F, true);
		});
	}
	
}
