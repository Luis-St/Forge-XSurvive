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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
	public abstract <T extends LivingEntity> void hurtAndBreak(int damage, T livingEntity, Consumer<T> consumer);
	//endregion
	
	@Inject(method = "inventoryTick", at = @At("TAIL"))
	public void inventoryTick(Level level, Entity entity, int slot, boolean selected, CallbackInfo callback) {
		if (entity instanceof LivingEntity livingEntity) {
			int breakingCurse = ((ItemStack) (Object) this).getEnchantmentLevel(XSEnchantments.CURSE_OF_BREAKING.get());
			if (breakingCurse > 0 && level.getGameTime() % 100 == 0) {
				this.hurtAndBreak(breakingCurse * 2, livingEntity, (e) -> {});
			}
		}
	}
}
