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

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis-St
 *
 */

public class EnchantedGoldenBookItem extends GlintColorItem {
	
	public EnchantedGoldenBookItem(Properties properties) {
		super(properties, DyeColor.YELLOW.getId());
	}
	
	public static @NotNull ItemStack createForEnchantment(Enchantment enchantment) {
		ItemStack stack = new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
		((EnchantedGoldenBookItem) stack.getItem()).setEnchantment(stack, enchantment);
		return stack;
	}
	
	@Override
	public boolean isEnchantable(@NotNull ItemStack stack) {
		return true;
	}
	
	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltip) {
		super.appendHoverText(stack, level, components, tooltip);
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if (!enchantments.isEmpty()) {
			Enchantment enchantment = this.getEnchantment(stack);
			if (enchantment != null) {
				components.add(Component.translatable(enchantment.getDescriptionId()).withStyle(ChatFormatting.DARK_PURPLE));
			}
		}
	}
	
	@Override
	public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
		if (this.getEnchantment(stack) == enchantment) {
			return 1;
		}
		return 0;
	}
	
	public @Nullable Enchantment getEnchantment(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			if (enchantments.size() == 1) {
				return enchantments.keySet().stream().toList().get(0);
			}
		}
		return null;
	}
	
	public void setEnchantment(@NotNull ItemStack stack, Enchantment enchantment) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			EnchantmentHelper.setEnchantments(Map.of(enchantment, 1), stack);
		}
	}
}
