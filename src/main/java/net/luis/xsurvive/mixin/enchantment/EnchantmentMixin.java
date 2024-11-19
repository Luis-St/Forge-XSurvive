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
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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
public class EnchantmentMixin {
	
	@Inject(method = "getFullname", at = @At("HEAD"), cancellable = true)
	private static void getFullname(@NotNull Holder<Enchantment> enchantment, int level, @NotNull CallbackInfoReturnable<Component> callback) {
		Component component = GoldenEnchantmentHelper.getFullname(enchantment, level);
		if (component != null) {
			callback.setReturnValue(component);
		}
	}
}
