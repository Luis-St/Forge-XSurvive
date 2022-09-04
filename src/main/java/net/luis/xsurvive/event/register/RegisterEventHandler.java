package net.luis.xsurvive.event.register;

import net.luis.xsurvive.XSurvive;
import net.minecraft.server.commands.RaidCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class RegisterEventHandler {
	
	@SubscribeEvent
	public static void registerClientCommands(RegisterCommandsEvent event) {
		RaidCommand.register(event.getDispatcher());
	}
	
}
