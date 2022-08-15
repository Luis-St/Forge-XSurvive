package net.luis.xsurvive.data;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.data.provider.block.XSBlockStateProvider;
import net.luis.xsurvive.data.provider.item.XSItemModelProvider;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.data.provider.loot.XSGlobalLootModifierProvider;
import net.luis.xsurvive.data.provider.loottable.XSLootTableProvider;
import net.luis.xsurvive.data.provider.recipe.XSRecipeProvider;
import net.luis.xsurvive.data.provider.tag.XSBlockTagsProvider;
import net.luis.xsurvive.data.provider.tag.XSItemTagsProvider;
import net.luis.xsurvive.data.provider.tag.XSPoiTypeTagsProvider;
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
			generator.addProvider(event.includeClient(), new XSBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new XSItemModelProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new XSLanguageProvider(generator));
			generator.addProvider(event.includeServer(), new XSLootTableProvider(generator));
			generator.addProvider(event.includeServer(), new XSRecipeProvider(generator));
			XSBlockTagsProvider blockTagsProvider = new XSBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(event.includeServer(), blockTagsProvider);
			generator.addProvider(event.includeServer(), new XSItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSPoiTypeTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSGlobalLootModifierProvider(generator));
		}
	}
	
}
