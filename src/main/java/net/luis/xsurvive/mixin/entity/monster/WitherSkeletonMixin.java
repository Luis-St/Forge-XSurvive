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

package net.luis.xsurvive.mixin.entity.monster;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.ItemStackHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(WitherSkeleton.class)
@SuppressWarnings("UnnecessarySuperQualifier")
public abstract class WitherSkeletonMixin extends AbstractSkeleton {
	
	//region Mixin
	private WitherSkeletonMixin(@NotNull EntityType<? extends AbstractSkeleton> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
	protected void populateDefaultEquipmentSlots(@NotNull RandomSource rng, @NotNull DifficultyInstance instance, @NotNull CallbackInfo callback) {
		if (0.25 > rng.nextDouble()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStackHelper.getBowForDifficulty(instance.getDifficulty()));
		} else {
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStackHelper.getSwordForDifficulty(instance.getDifficulty(), this));
		}
		callback.cancel();
	}
	
	@Inject(method = "populateDefaultEquipmentEnchantments", at = @At("HEAD"))
	protected void populateDefaultEquipmentEnchantments(@NotNull ServerLevelAccessor level, @NotNull RandomSource rng, @NotNull DifficultyInstance instance, @NotNull CallbackInfo callback) {
		ItemStack stack = ItemStackHelper.setupItemForDifficulty(level.getDifficulty(), this, this.getItemInHand(InteractionHand.MAIN_HAND));
		XSEnchantmentHelper.removeEnchantment(Enchantments.FLAME.getOrThrow(this), stack);
		this.setItemSlot(EquipmentSlot.MAINHAND, stack);
	}
	
	@Inject(method = "getArrow", at = @At("HEAD"), cancellable = true)
	protected void getArrow(@NotNull ItemStack stack, float usePercentage, @Nullable ItemStack weapon, @NotNull CallbackInfoReturnable<AbstractArrow> callback) {
		AbstractArrow abstractArrow = super.getArrow(stack, usePercentage, weapon);
		if (abstractArrow instanceof Arrow arrow) {
			arrow.addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
		}
		callback.setReturnValue(abstractArrow);
	}
}
