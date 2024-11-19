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

import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.minecraft.core.component.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
	
	//region Mixin
	@Shadow
	public abstract @NotNull Item getItem();
	//endregion
	
	@Inject(method = "addToTooltip", at = @At("HEAD"), cancellable = true)
	private void addToTooltip(@NotNull DataComponentType<?> dataComponent, Item.@NotNull TooltipContext context, @NotNull Consumer<Component> action, @NotNull TooltipFlag tooltipFlag, @NotNull CallbackInfo callback) {
		if (dataComponent == DataComponents.ENCHANTMENTS && this.getItem() instanceof EnchantedGoldenBookItem) {
			callback.cancel();
		}
	}
}
