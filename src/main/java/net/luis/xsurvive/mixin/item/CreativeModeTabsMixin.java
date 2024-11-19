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

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin {
	
	@Inject(method = "generateEnchantmentBookTypesOnlyMaxLevel", at = @At("HEAD"), cancellable = true)
	private static void generateEnchantmentBookTypesOnlyMaxLevel(CreativeModeTab.@NotNull Output populator, @NotNull HolderLookup<Enchantment> lookup, CreativeModeTab.@NotNull TabVisibility visibility, @NotNull CallbackInfo callback) {
		lookup.listElements().filter(GoldenEnchantmentHelper::isEnchantment).filter(Predicate.not(GoldenEnchantmentHelper::isUpgradeEnchantment)).forEach(holder -> {
			ItemStack book = EnchantmentHelper.createBook(new EnchantmentInstance(holder, holder.value().getMaxLevel()));
			populator.accept(book, visibility);
		});
		callback.cancel();
	}
	
	@Inject(method = "generateEnchantmentBookTypesAllLevels", at = @At("HEAD"), cancellable = true)
	private static void generateEnchantmentBookTypesAllLevels(CreativeModeTab.@NotNull Output populator, @NotNull HolderLookup<Enchantment> lookup, CreativeModeTab.@NotNull TabVisibility visibility, @NotNull CallbackInfo callback) {
		lookup.listElements().filter(GoldenEnchantmentHelper::isEnchantment).filter(Predicate.not(GoldenEnchantmentHelper::isUpgradeEnchantment)).forEach(holder -> {
			Enchantment enchantment = holder.value();
			IntStream.rangeClosed(enchantment.getMinLevel(), enchantment.getMaxLevel()).forEach(level -> {
				ItemStack book = EnchantmentHelper.createBook(new EnchantmentInstance(holder, level));
				populator.accept(book, visibility);
			});
		});
		callback.cancel();
	}
}
