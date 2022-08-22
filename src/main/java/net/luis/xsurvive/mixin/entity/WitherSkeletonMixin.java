package net.luis.xsurvive.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.level.entity.EntityHelper;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonMixin extends AbstractSkeleton {

	private WitherSkeletonMixin(EntityType<? extends AbstractSkeleton> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
	protected void populateDefaultEquipmentSlots(RandomSource rng, DifficultyInstance instance, CallbackInfo callback) {
		if (0.25 > rng.nextDouble()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		} else {
			Item weapon = EntityHelper.getWeaponWeightsForDifficulty(Math.max(2.75, instance.getEffectiveDifficulty())).next().get(0);
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
	protected void populateDefaultEquipmentEnchantments(RandomSource rng, DifficultyInstance instance, CallbackInfo callback) {
		ItemStack stack = EntityHelper.setupItemForSlot(this, EquipmentSlot.MAINHAND, Lists.newArrayList(this.getItemInHand(InteractionHand.MAIN_HAND).getItem()), instance.getSpecialMultiplier());
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