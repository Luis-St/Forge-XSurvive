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

import net.luis.xsurvive.core.components.XSDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.core.component.DataComponents.*;

/**
 *
 * @author Luis-St
 *
 */

public class EnchantedGoldenBookItem extends Item {
	
	public EnchantedGoldenBookItem(@NotNull Properties properties) {
		super(properties.component(ENCHANTMENTS, ItemEnchantments.EMPTY).component(ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.YELLOW.getId()));
	}
	
	public static @NotNull ItemStack createForEnchantment(@NotNull Holder<Enchantment> enchantment) {
		ItemStack stack = new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
		setEnchantment(stack, enchantment);
		return stack;
	}
	
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag tooltip) {
		super.appendHoverText(stack, context, components, tooltip);
		ItemEnchantments enchantments = stack.getOrDefault(ENCHANTMENTS, ItemEnchantments.EMPTY);
		if (!enchantments.isEmpty()) {
			Holder<Enchantment> enchantment = getEnchantment(stack);
			if (enchantment != null) {
				components.add(enchantment.value().description().plainCopy().withStyle(ChatFormatting.DARK_PURPLE));
			}
		}
	}
	
	//@Override // ToDo: Mixin replacement EnchantmentHelper.getEnchantmentLevel
	//public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
	//	if (this.getEnchantment(stack) == enchantment) {
	//		return 1;
	//	}
	//	return 0;
	//}
	
	public static @Nullable Holder<Enchantment> getEnchantment(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			ItemEnchantments enchantments = stack.getOrDefault(ENCHANTMENTS, ItemEnchantments.EMPTY);
			if (enchantments.size() == 1) {
				return enchantments.keySet().stream().toList().getFirst();
			}
		}
		return null;
	}
	
	public static void setEnchantment(@NotNull ItemStack stack, Holder<Enchantment> enchantment) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
			enchantments.set(enchantment, 1);
			stack.set(ENCHANTMENTS, enchantments.toImmutable());
		}
	}
}
