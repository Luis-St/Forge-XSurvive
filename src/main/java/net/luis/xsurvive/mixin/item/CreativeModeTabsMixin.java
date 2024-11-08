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

package net.luis.xsurvive.mixin.item;

import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin { // ToDo: No idea what this was supposed to do
	
	/*@Inject(method = "generateEnchantmentBookTypesOnlyMaxLevel", at = @At("HEAD"), cancellable = true)
	private static void generateEnchantmentBookTypesOnlyMaxLevel(CreativeModeTab.Output populator, HolderLookup<Enchantment> lookup, Set<EnchantmentCategory> categories, CreativeModeTab.TabVisibility visibility, CallbackInfo callback) {
		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
			if (enchantment.isAllowedOnBooks()) {
				if (enchantment instanceof IEnchantment ench) {
					if (!ench.isUpgradeEnchantment()) {
						for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
							populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)), visibility);
						}
					}
				} else {
					XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					XSurvive.LOGGER.info("A deprecate vanilla logic is called");
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
						populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)), visibility);
					}
				}
			}
		}
		callback.cancel();
	}
	
	@Inject(method = "generateEnchantmentBookTypesAllLevels", at = @At("HEAD"), cancellable = true)
	private static void generateEnchantmentBookTypesAllLevels(CreativeModeTab.Output populator, HolderLookup<Enchantment> lookup, Set<EnchantmentCategory> categories, CreativeModeTab.TabVisibility visibility, CallbackInfo callback) {
		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
			if (enchantment.isAllowedOnBooks()) {
				if (enchantment instanceof IEnchantment ench) {
					if (enchantment.allowedInCreativeTab(Items.ENCHANTED_BOOK, categories) && !ench.isUpgradeEnchantment()) {
						populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
					}
				} else {
					XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					XSurvive.LOGGER.info("A deprecate vanilla logic is called");
					if (enchantment.allowedInCreativeTab(Items.ENCHANTED_BOOK, categories)) {
						populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
					}
				}
			}
		}
		callback.cancel();
	}*/
}
