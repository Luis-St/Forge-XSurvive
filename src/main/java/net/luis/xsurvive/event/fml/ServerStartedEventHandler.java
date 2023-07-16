package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 *
 * @author Luis-st
 *
 */
@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class ServerStartedEventHandler {
	
	@SubscribeEvent
	public static void serverStarted(@NotNull ServerStartedEvent event) throws IOException {
		MinecraftServer server = event.getServer();
		if (server != null && !server.isFlightAllowed()) {
			server.setFlightAllowed(true);
		}
	}
}
