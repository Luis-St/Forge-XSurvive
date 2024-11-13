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

import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
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
@SuppressWarnings("DataFlowIssue")
public abstract class ItemStackMixin {
	
	//region Mixin
	@Shadow
	public abstract <T extends LivingEntity> void hurtAndBreak(int damage, @NotNull ServerLevel level, @NotNull ServerPlayer player, @NotNull Consumer<Item> action);
	//endregion
	
	@Inject(method = "inventoryTick", at = @At("TAIL"))
	public void inventoryTick(@NotNull Level level, @NotNull Entity entity, int slot, boolean selected, @NotNull CallbackInfo callback) {
		if (entity instanceof ServerPlayer player) {
			int breakingCurse = EnchantmentHelper.getItemEnchantmentLevel(XSEnchantments.CURSE_OF_BREAKING.getOrThrow(level), ((ItemStack) (Object) this));
			if (breakingCurse > 0 && level.getGameTime() % 100 == 0) {
				this.hurtAndBreak(breakingCurse * 2, player.serverLevel(), player, (item) -> {});
			}
		}
	}
}
