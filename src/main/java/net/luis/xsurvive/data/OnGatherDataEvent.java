package net.luis.xsurvive.data;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.data.provider.block.XSurviveBlockStateProvider;
import net.luis.xsurvive.data.provider.item.XSurviveItemModelProvider;
import net.luis.xsurvive.data.provider.language.XSurviveLanguageProvider;
import net.luis.xsurvive.data.provider.loot.XSurviveGlobalLootModifierProvider;
import net.luis.xsurvive.data.provider.loottable.XSurviveLootTableProvider;
import net.luis.xsurvive.data.provider.recipe.XSurviveRecipeProvider;
import net.luis.xsurvive.data.provider.tag.XSurviveBlockTagsProvider;
import net.luis.xsurvive.data.provider.tag.XSurviveItemTagsProvider;
import net.luis.xsurvive.data.provider.tag.XSurvivePoiTypeTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class OnGatherDataEvent {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		if (event.includeDev()) {
			generator.addProvider(event.includeClient(), new XSurviveBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new XSurviveItemModelProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new XSurviveLanguageProvider(generator));
			generator.addProvider(event.includeServer(), new XSurviveLootTableProvider(generator));
			generator.addProvider(event.includeServer(), new XSurviveRecipeProvider(generator));
			XSurviveBlockTagsProvider blockTagsProvider = new XSurviveBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(event.includeServer(), blockTagsProvider);
			generator.addProvider(event.includeServer(), new XSurviveItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSurvivePoiTypeTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSurviveGlobalLootModifierProvider(generator));
		}
	}
	
}
