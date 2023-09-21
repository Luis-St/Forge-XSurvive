package net.luis.xsurvive.event.register;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.config.util.XSConfigManager;
import net.luis.xsurvive.server.commands.TestCommand;
import net.minecraft.server.commands.RaidCommand;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.FMLEnvironment;
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
		if (!FMLEnvironment.production) {
			TestCommand.register(event.getDispatcher());
		}
	}
	
	@SubscribeEvent
	public static void registerReloadListener(@NotNull AddReloadListenerEvent event) {
		event.addListener(new SimplePreparableReloadListener<>() {
			
			@Override
			protected @NotNull Object prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
				return new Object();
			}
			
			@Override
			protected void apply(@NotNull Object object, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
				XSConfigManager.reload();
			}
		});
	}
}
