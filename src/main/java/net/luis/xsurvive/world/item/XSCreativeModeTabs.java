/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.world.item;

import net.luis.xores.XOres;
import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSCreativeModeTabs {
	
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, XOres.MOD_ID);
	
	static {
		TABS.register("runes", () -> {
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
		TABS.register("golden_book", () -> {
			return CreativeModeTab.builder().title(Component.translatable("item_tab." + XSurvive.MOD_ID + ".runes")).icon(() -> {
				return new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
			}).displayItems((parameters, populator) -> {
				for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
					if (enchantment instanceof IEnchantment ench) {
						if (ench.isAllowedOnGoldenBooks()) {
							populator.accept(EnchantedGoldenBookItem.createForEnchantment(enchantment));
						}
					} else {
						XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					}
				}
			}).build();
		});
	}
}
