package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnServerStartedEvent {
	
	@SubscribeEvent
	public static void serverStarted(ServerStartedEvent event) {
		MinecraftServer server = event.getServer();
		if (server != null && !server.isFlightAllowed()) {
			server.setFlightAllowed(true);
		}
	}
	
}
