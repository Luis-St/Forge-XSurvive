package net.luis.xsurvive.event.client;

import net.luis.xbackpack.XBackpack;
import net.luis.xsurvive.client.commands.GammaCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XBackpack.MOD_ID, value = Dist.CLIENT)
public class RegisterClientEventHandler {
	
	@SubscribeEvent
	public static void registerClientCommands(@NotNull RegisterClientCommandsEvent event) {
		GammaCommand.register(event.getDispatcher());
	}
}
