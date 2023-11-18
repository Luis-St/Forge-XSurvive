package net.luis.xsurvive.event.registry;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.server.commands.TestCommand;
import net.minecraft.server.commands.RaidCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class RegisterEventHandler {
	
	@SubscribeEvent
	public static void registerCommands(@NotNull RegisterCommandsEvent event) {
		RaidCommand.register(event.getDispatcher());
		TestCommand.register(event.getDispatcher());
	}
}
