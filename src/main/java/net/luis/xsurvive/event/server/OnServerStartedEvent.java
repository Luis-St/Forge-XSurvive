package net.luis.xsurvive.event.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.wiki.file.BlocksWikiFile;
import net.luis.xsurvive.wiki.file.DimensionsWikiFile;
import net.luis.xsurvive.wiki.file.EffectsWikiFile;
import net.luis.xsurvive.wiki.file.EnchantmentWikiFile;
import net.luis.xsurvive.wiki.file.ItemsWikiFile;
import net.luis.xsurvive.wiki.file.PotionsWikiFile;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 *
 * @author Luis-st
 *
 */
@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnServerStartedEvent {
	
	@SubscribeEvent
	public static void serverStarted(ServerStartedEvent event) throws IOException {
		if (!FMLEnvironment.production) {
			createWikis(new File("D:/Git Repositories/Forge-XSurvive").toPath().resolve("wiki files"));
		}
	}
	
	private static void createWikis(Path path) throws IOException {
		EnchantmentWikiFile.create().write(path);
		EffectsWikiFile.create().write(path);
		PotionsWikiFile.create().write(path);
		BlocksWikiFile.create().write(path);
		ItemsWikiFile.create().write(path);
		DimensionsWikiFile.create().write(path);
	}
	
}
