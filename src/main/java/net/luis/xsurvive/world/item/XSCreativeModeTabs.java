package net.luis.xsurvive.world.item;

import net.luis.xores.XOres;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis
 *
 */

public class XSCreativeModeTabs {
	
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, XOres.MOD_ID);
	
	public static final RegistryObject<CreativeModeTab> RUNES = TABS.register("runes", () -> {
		return CreativeModeTab.builder().title(Component.translatable("item_tab." + XSurvive.MOD_ID + ".runes")).icon(() -> {
			return new ItemStack(XSItems.RAINBOW_RUNE.get());
		}).displayItems((parameters, populator) -> {
			populator.accept(XSItems.WHITE_RUNE.get());
			populator.accept(XSItems.LIGHT_GRAY_RUNE.get());
			populator.accept(XSItems.GRAY_RUNE.get());
			populator.accept(XSItems.BLACK_RUNE.get());
			populator.accept(XSItems.BROWN_RUNE.get());
			populator.accept(XSItems.RED_RUNE.get());
			populator.accept(XSItems.ORANGE_RUNE.get());
			populator.accept(XSItems.YELLOW_RUNE.get());
			populator.accept(XSItems.LIME_RUNE.get());
			populator.accept(XSItems.GREEN_RUNE.get());
			populator.accept(XSItems.CYAN_RUNE.get());
			populator.accept(XSItems.LIGHT_BLUE_RUNE.get());
			populator.accept(XSItems.BLUE_RUNE.get());
			populator.accept(XSItems.PURPLE_RUNE.get());
			populator.accept(XSItems.MAGENTA_RUNE.get());
			populator.accept(XSItems.PINK_RUNE.get());
			populator.accept(XSItems.RAINBOW_RUNE.get());
		}).build();
	});
	public static final RegistryObject<CreativeModeTab> GOLDEN_BOOK = TABS.register("golden_book", () -> {
		return CreativeModeTab.builder().title(Component.translatable("item_tab." + XSurvive.MOD_ID + ".runes")).icon(() -> {
			return new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
		}).displayItems((parameters, populator) -> {
			for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
				if (enchantment instanceof IEnchantment ench) {
					if (ench.isAllowedOnGoldenBooks()) {
						populator.accept(EnchantedGoldenBookItem.createForEnchantment(enchantment));
					}
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				}
			}
		}).build();
	});
}
