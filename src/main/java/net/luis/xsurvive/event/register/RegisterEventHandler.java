package net.luis.xsurvive.event.register;

import net.luis.xsurvive.XSurvive;
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
	public static void registerClientCommands(@NotNull RegisterCommandsEvent event) {
		RaidCommand.register(event.getDispatcher());
	}
}
