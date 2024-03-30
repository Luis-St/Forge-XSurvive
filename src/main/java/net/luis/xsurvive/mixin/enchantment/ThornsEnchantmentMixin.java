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

import net.luis.xsurvive.util.SimpleEntry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("DataFlowIssue")
@Mixin(ThornsEnchantment.class)
public abstract class ThornsEnchantmentMixin {
	
	//region Mixin
	@Shadow
	public static boolean shouldHit(int level, RandomSource rng) {
		return false;
	}
	//endregion
	
	@Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
	public void getMaxLevel(@NotNull CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(4);
	}
	
	@Inject(method = "doPostHurt", at = @At("HEAD"), cancellable = true)
	public void doPostHurt(@NotNull LivingEntity target, Entity attacker, int level, CallbackInfo callback) {
		RandomSource rng = target.getRandom();
		List<Entry<EquipmentSlot, ItemStack>> thornsEquipment = this.getThornsEquipment(target);
		if (shouldHit(level, rng)) {
			if (attacker != null) {
				attacker.hurt(target.damageSources().thorns(attacker), this.getThornsLevel(target));
			}
			for (Entry<EquipmentSlot, ItemStack> entry : thornsEquipment) {
				entry.getValue().hurtAndBreak(1, target, entity -> entity.broadcastBreakEvent(entry.getKey()));
			}
		}
		callback.cancel();
	}
	
	private int getThornsLevel(@NotNull LivingEntity entity, EquipmentSlot slot) {
		return entity.getItemBySlot(slot).getEnchantmentLevel((ThornsEnchantment) (Object) this);
	}
	
	private @NotNull List<Entry<EquipmentSlot, ItemStack>> getThornsEquipment(LivingEntity entity) {
		List<Entry<EquipmentSlot, ItemStack>> thornsEquipment = new ArrayList<>();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() == Type.ARMOR && this.getThornsLevel(entity, slot) > 0) {
				thornsEquipment.add(new SimpleEntry<>(slot, entity.getItemBySlot(slot)));
			}
		}
		return thornsEquipment;
	}
	
	private int getThornsLevel(LivingEntity entity) {
		return this.getThornsLevel(entity, EquipmentSlot.HEAD) + this.getThornsLevel(entity, EquipmentSlot.CHEST) + this.getThornsLevel(entity, EquipmentSlot.LEGS) + this.getThornsLevel(entity, EquipmentSlot.FEET);
	}
}
