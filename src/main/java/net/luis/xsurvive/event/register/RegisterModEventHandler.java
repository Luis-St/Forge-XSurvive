package net.luis.xsurvive.event.register;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterModEventHandler {
	
	@SubscribeEvent
	public static void register(CreativeModeTabEvent.Register event) {
		event.registerCreativeModeTab(new ResourceLocation(XSurvive.MOD_ID, "runes"), builder -> {
			builder.title(Component.translatable("item_tab.runes"));
			builder.icon(() -> new ItemStack(XSItems.RAINBOW_RUNE.get()));
			builder.displayItems((enabledFlags, populator, hasPermissions) -> {
				populator.accept(XSItems.WHITE_RUNE.get());
				populator.accept(XSItems.ORANGE_RUNE.get());
				populator.accept(XSItems.MAGENTA_RUNE.get());
				populator.accept(XSItems.LIGHT_BLUE_RUNE.get());
				populator.accept(XSItems.YELLOW_RUNE.get());
				populator.accept(XSItems.LIME_RUNE.get());
				populator.accept(XSItems.PINK_RUNE.get());
				populator.accept(XSItems.GRAY_RUNE.get());
				populator.accept(XSItems.LIGHT_GRAY_RUNE.get());
				populator.accept(XSItems.CYAN_RUNE.get());
				populator.accept(XSItems.PURPLE_RUNE.get());
				populator.accept(XSItems.BLUE_RUNE.get());
				populator.accept(XSItems.BROWN_RUNE.get());
				populator.accept(XSItems.GREEN_RUNE.get());
				populator.accept(XSItems.RED_RUNE.get());
				populator.accept(XSItems.BLACK_RUNE.get());
				populator.accept(XSItems.RAINBOW_RUNE.get());
			});
		});
		event.registerCreativeModeTab(new ResourceLocation(XSurvive.MOD_ID, "golden_book"), builder -> {
			builder.title(Component.translatable("item_tab.golden_book"));
			builder.icon(() -> new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get()));
			builder.displayItems((enabledFlags, populator, hasPermissions) -> {
				for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
					if (enchantment instanceof IEnchantment ench) {
						if (ench.isAllowedOnGoldenBooks()) {
							populator.accept(EnchantedGoldenBookItem.createForEnchantment(enchantment));
						}
					} else {
						XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					}
				}
			});
		});
	}
	
	@SubscribeEvent
	public void buildContents(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(XSItems.HONEY_MELON_SLICE);
			event.accept(XSItems.DIAMOND_APPLE);
			event.accept(XSItems.ENCHANTED_DIAMOND_APPLE);
		}
	}
	
}
