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

package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSEnchantmentTags;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.luis.xsurvive.world.item.EnchantedGoldenBookItem.*;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper.*;

/**
 *
 * @author Luis-St
 *
 */

public class GoldenEnchantmentHelper {
	
	public static @NotNull EnchantedItem merge(@NotNull ItemStack left, @NotNull ItemStack right) {
		ItemStack result = left.copy();
		if (right.getItem() instanceof EnchantedGoldenBookItem) {
			Holder<Enchantment> enchantment = getEnchantment(right);
			if (enchantment != null && isGoldenEnchantment(enchantment)) {
				int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, result);
				GoldenEnchantmentInstance instance = createGoldenInstance(enchantment);
				if (!hasEnchantment(enchantment, result)) {
					if (isEnchantmentCompatible(result, enchantment)) {
						addEnchantment(new EnchantmentInstance(enchantment, 1), result, false);
						return new EnchantedItem(result, 10);
					}
					return EnchantedItem.EMPTY;
				} else if (instance.isGolden() && instance.level > enchantment.value().getMaxLevel() && level >= enchantment.value().getMaxLevel()) {
					if (getMaxGoldenBookLevel(enchantment) > level) {
						replaceEnchantment(instance, result);
						return new EnchantedItem(result, getGoldenCost(enchantment, level));
					}
					return EnchantedItem.EMPTY;
				} else {
					increaseEnchantment(enchantment, result, false);
					return new EnchantedItem(result, 10);
				}
			}
			return EnchantedItem.EMPTY;
		}
		XSurvive.LOGGER.error("Can not merge '{}' with '{}', since the right Item must be a instance of EnchantedGoldenBookItem", ForgeRegistries.ITEMS.getKey(left.getItem()), ForgeRegistries.ITEMS.getKey(left.getItem()));
		return EnchantedItem.EMPTY;
	}
	
	public static @NotNull EnchantedItem upgrade(@NotNull ItemStack left, @NotNull ItemStack right) {
		ItemStack result = left.copy();
		if (right.getItem() instanceof EnchantedGoldenBookItem) {
			Holder<Enchantment> enchantment = getEnchantment(right);
			if (enchantment != null && isEnchantment(enchantment)) {
				int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, result);
				if (isUpgradeEnchantment(enchantment) && enchantment.value().getMaxLevel() > level) {
					increaseEnchantment(enchantment, result, false);
					return new EnchantedItem(result, 30);
				} else {
					return merge(left, right);
				}
			}
			return EnchantedItem.EMPTY;
		}
		XSurvive.LOGGER.error("Can not upgrade '{}' with '{}', since the right Item must be a instance of EnchantedGoldenBookItem", ForgeRegistries.ITEMS.getKey(left.getItem()), ForgeRegistries.ITEMS.getKey(left.getItem()));
		return EnchantedItem.EMPTY;
	}
	
	public static boolean isEnchantment(@NotNull Holder<Enchantment> enchantment) {
		return isGoldenEnchantment(enchantment) || isUpgradeEnchantment(enchantment);
	}
	
	public static boolean isGoldenEnchantment(@NotNull Holder<Enchantment> enchantment) {
		return enchantment.is(XSEnchantmentTags.GOLDEN_ENCHANTMENT);
	}
	
	public static int getMaxGoldenBookLevel(@NotNull Holder<Enchantment> enchantment) {
		return isGoldenEnchantment(enchantment) ? enchantment.value().getMaxLevel() + 1 : -1;
	}
	
	public static boolean isGoldenLevel(@NotNull Holder<Enchantment> enchantment, int level) {
		if (!isGoldenEnchantment(enchantment)) {
			return false;
		}
		return getMaxGoldenBookLevel(enchantment) >= level && level >= enchantment.value().getMaxLevel();
	}
	
	public static int getGoldenCost(@NotNull Holder<Enchantment> enchantment, int level) {
		return Math.max(0, level - enchantment.value().getMaxLevel()) * 10 + 50;
	}
	
	public static boolean isUpgradeEnchantment(@NotNull Holder<Enchantment> enchantment) {
		return enchantment.is(XSEnchantmentTags.UPGRADE_ENCHANTMENT);
	}
	
	public static @NotNull GoldenEnchantmentInstance createGoldenInstance(@NotNull Holder<Enchantment> enchantment) {
		return new GoldenEnchantmentInstance(enchantment, enchantment.value().getMaxLevel() + 1);
	}
	
	public static @Nullable Component getFullname(@NotNull Holder<Enchantment> enchantment, int level) {
		if (isEnchantment(enchantment)) {
			if ((getMaxGoldenBookLevel(enchantment) >= level && level > enchantment.value().getMaxLevel()) || isUpgradeEnchantment(enchantment)) {
				MutableComponent component = enchantment.value().description().copy();
				component.append(" ").append(Component.translatable("enchantment.level." + level));
				if (enchantment.value().getMaxLevel() >= level && level > 0) {
					return component.withStyle(ChatFormatting.BLUE);
				} else {
					return component.withStyle(ChatFormatting.DARK_PURPLE);
				}
			}
		}
		return null;
	}
}
