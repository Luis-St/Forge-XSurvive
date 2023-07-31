package net.luis.xsurvive.data;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.data.provider.XSBuiltinProvider;
import net.luis.xsurvive.data.provider.block.XSBlockStateProvider;
import net.luis.xsurvive.data.provider.item.XSItemModelProvider;
import net.luis.xsurvive.data.provider.language.XSLanguageProvider;
import net.luis.xsurvive.data.provider.loot.XSGlobalLootModifierProvider;
import net.luis.xsurvive.data.provider.loottable.XSLootTableProvider;
import net.luis.xsurvive.data.provider.recipe.XSRecipeProvider;
import net.luis.xsurvive.data.provider.tag.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.Set;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class GatherDataEventHandler {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		if (event.includeDev()) {
			generator.addProvider(event.includeClient(), new XSBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new XSItemModelProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeClient(), new XSLanguageProvider(generator));
			generator.addProvider(event.includeServer(), new XSLootTableProvider(generator));
			generator.addProvider(event.includeServer(), new XSRecipeProvider(generator));
			XSBlockTagsProvider blockTagsProvider = new XSBlockTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper());
			generator.addProvider(event.includeServer(), blockTagsProvider);
			generator.addProvider(event.includeServer(), new XSItemTagsProvider(generator, event.getLookupProvider(), blockTagsProvider.contentsGetter(), event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSPoiTypeTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSBiomeTagsProvider(generator, event.getLookupProvider(), event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new XSGlobalLootModifierProvider(generator));
			generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(generator.getPackOutput(), event.getLookupProvider(), XSBuiltinProvider.createProvider(), Set.of(XSurvive.MOD_ID)));
		}
	}
}
