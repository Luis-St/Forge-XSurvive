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
import net.luis.xsurvive.world.item.ItemEquipmentHelper;
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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(WitherSkeleton.class)
@SuppressWarnings("UnnecessarySuperQualifier")
public abstract class WitherSkeletonMixin extends AbstractSkeleton {
	
	//region Mixin
	private WitherSkeletonMixin(EntityType<? extends AbstractSkeleton> entityType, Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
	protected void populateDefaultEquipmentSlots(@NotNull RandomSource rng, DifficultyInstance instance, CallbackInfo callback) {
		if (0.25 > rng.nextDouble()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		} else {
			Item weapon = ItemEquipmentHelper.getWeaponWeightsForDifficulty(Math.max(2.75, instance.getEffectiveDifficulty())).next().get(0);
			if (weapon instanceof SwordItem) {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(weapon));
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
				XSurvive.LOGGER.error("Fail to equip difficulty based Sword to Wither Skeleton, since weapon {} is not a Sword", weapon);
			}
		}
		callback.cancel();
	}
	
	@Inject(method = "populateDefaultEquipmentEnchantments", at = @At("HEAD"))
	protected void populateDefaultEquipmentEnchantments(RandomSource rng, @NotNull DifficultyInstance instance, CallbackInfo callback) {
		ItemStack stack = ItemStackHelper.setupItemForSlot(this, EquipmentSlot.MAINHAND, this.getItemInHand(InteractionHand.MAIN_HAND), instance.getSpecialMultiplier());
		XSEnchantmentHelper.removeEnchantment(Enchantments.FLAMING_ARROWS, stack);
		this.setItemSlot(EquipmentSlot.MAINHAND, stack);
	}
	
	@Inject(method = "getArrow", at = @At("HEAD"), cancellable = true)
	protected void getArrow(ItemStack stack, float usePercentage, CallbackInfoReturnable<AbstractArrow> callback) {
		AbstractArrow abstractArrow = super.getArrow(stack, usePercentage);
		if (abstractArrow instanceof Arrow arrow) {
			arrow.addEffect(new MobEffectInstance(MobEffects.WITHER, 200));
		}
		callback.setReturnValue(abstractArrow);
	}
}
