package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.commands.GammaCommand;
import net.luis.xsurvive.config.util.XSConfigManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, value = Dist.CLIENT)
public class RegisterClientEventHandler {
	
	@SubscribeEvent
	public static void registerClientCommands(@NotNull RegisterClientCommandsEvent event) {
		if (XSConfigManager.CLIENT_CONFIG.get().chat().enableGammaCommand()) {
			XSurvive.LOGGER.info("Register gamma command");
			GammaCommand.register(event.getDispatcher());
		}
	}
}
