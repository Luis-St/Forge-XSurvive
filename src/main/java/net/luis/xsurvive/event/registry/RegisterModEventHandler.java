package net.luis.xsurvive.event.registry;

import net.luis.xores.XOres;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 *
 * @author Luis-St
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterModEventHandler {
	
	@SubscribeEvent
	public static void buildContents(@NotNull BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(XSItems.HONEY_MELON_SLICE);
			event.accept(XSItems.DIAMOND_APPLE);
			event.accept(XSItems.ENCHANTED_DIAMOND_APPLE);
		} else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			event.accept(XSItems.CURSED_ENDER_EYE);
			event.accept(XSBlocks.SMELTING_FURNACE);
		} else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(XSItems.HONEY_MELON_SEEDS);
			event.accept(XSBlocks.HONEY_MELON);
		}
	}
	
	@SubscribeEvent
	public static void addPackFinders(@NotNull AddPackFindersEvent event) {
		if (event.getPackType() == PackType.SERVER_DATA) {
			addServerPack(event, "xsurvive_additions", "XSurvive Additions", PackSource.BUILT_IN);
		}
	}
	
	private static void addServerPack(@NotNull AddPackFindersEvent event, @NotNull String packName, @NotNull String displayName, @NotNull PackSource source) {
		Path resourcePath = ModList.get().getModFileById(XSurvive.MOD_ID).getFile().findResource(packName);
		PathPackResources.PathResourcesSupplier resourcesSupplier = new PathPackResources.PathResourcesSupplier(resourcePath, false);
		Pack pack = Pack.readMetaAndCreate("builtin/" + packName, Component.literal(displayName), false, resourcesSupplier, PackType.SERVER_DATA, Pack.Position.TOP, source);
		event.addRepositorySource(packConsumer -> packConsumer.accept(pack));
	}
}
