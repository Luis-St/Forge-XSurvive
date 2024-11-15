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

package net.luis.xsurvive.mixin.enchantment;

import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
	
	@Inject(method = "getFullname", at = @At("RETURN"), cancellable = true)
	private static void getFullname(@NotNull Holder<Enchantment> enchantment, int level, @NotNull CallbackInfoReturnable<Component> callback) {
		if (GoldenEnchantmentHelper.isEnchantment(enchantment)) {
			if ((GoldenEnchantmentHelper.getMaxGoldenBookLevel(enchantment) >= level && level > enchantment.value().getMaxLevel()) || GoldenEnchantmentHelper.isUpgradeEnchantment(enchantment)) {
				MutableComponent component = enchantment.value().description().copy();
				component.append(" ").append(Component.translatable("enchantment.level." + level));
				if (enchantment.value().getMaxLevel() >= level && level > 0) {
					callback.setReturnValue(component.withStyle(ChatFormatting.BLUE));
				} else {
					callback.setReturnValue(component.withStyle(ChatFormatting.DARK_PURPLE));
				}
			}
		}
	}
}
