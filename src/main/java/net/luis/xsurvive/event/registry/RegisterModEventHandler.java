package net.luis.xsurvive.event.registry;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

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
}
