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

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSEnchantmentTags;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;

/**
 *
 * @author Luis
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSCreativeModeTabs {
	
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, XSurvive.MOD_ID);
	
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
				HolderLookup.RegistryLookup<Enchantment> lookup = parameters.holders().lookupOrThrow(Registries.ENCHANTMENT);
				for (Holder<Enchantment> enchantment : lookup.listElements().filter(GoldenEnchantmentHelper::isEnchantment).toList()) {
					populator.accept(EnchantedGoldenBookItem.createForEnchantment(enchantment));
				}
			}).build();
		});
	}
}
