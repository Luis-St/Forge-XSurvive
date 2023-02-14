package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.wiki.files.*;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author Luis-st
 *
 */
@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class ServerStartedEventHandler {
	
	@SubscribeEvent
	public static void serverStarted(ServerStartedEvent event) throws IOException {
		MinecraftServer server = event.getServer();
		if (server != null && !server.isFlightAllowed()) {
			server.setFlightAllowed(true);
		}
		if (!FMLEnvironment.production) {
			createWikis(new File("D:/Programmieren/Git Repositories/Forge-XSurvive").toPath().resolve("wiki files"));
		}
	}
	
	private static void createWikis(Path path) throws IOException {
		BlocksWikiFile.create().write(path);
		CommandsWikiFile.create().write(path);
		DimensionsWikiFile.create().write(path);
		EffectsWikiFile.create().write(path);
		EnchantmentWikiFile.create().write(path);
		ItemsWikiFile.create().write(path);
		PotionsWikiFile.create().write(path);
		VanillaModificationsWikiFile.create().write(path);
		VillagerTradeWikiFile.create().write(path);
	}
	
}
