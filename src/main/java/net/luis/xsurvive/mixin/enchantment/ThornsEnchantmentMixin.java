package net.luis.xsurvive.mixin.enchantment;

import com.google.common.collect.Lists;
import net.luis.xsurvive.util.SimpleEntry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ThornsEnchantment.class)
public abstract class ThornsEnchantmentMixin {
	
	@Shadow
	public static boolean shouldHit(int level, RandomSource rng) {
		return false;
	}
	
	@Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
	public void getMaxLevel(CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(4);
	}
	
	@Inject(method = "doPostHurt", at = @At("HEAD"), cancellable = true)
	public void doPostHurt(LivingEntity target, Entity attacker, int level, CallbackInfo callback) {
		RandomSource rng = target.getRandom();
		List<Entry<EquipmentSlot, ItemStack>> thornsEquipment = this.getThornsEquipment(target);
		if (shouldHit(level, rng)) {
			if (attacker != null) {
				int thornsLevel = this.getThornsLevel(target);
				attacker.hurt(target.damageSources().thorns(attacker), (0.2F * level) * thornsLevel);
			}
			for (Entry<EquipmentSlot, ItemStack> entry : thornsEquipment) {
				entry.getValue().hurtAndBreak(1, target, (entity) -> {
					entity.broadcastBreakEvent(entry.getKey());
				});
			}
		}
		callback.cancel();
	}
	
	private int getThornsLevel(LivingEntity entity, EquipmentSlot slot) {
		return entity.getItemBySlot(slot).getEnchantmentLevel((ThornsEnchantment) (Object) this);
	}
	
	private List<Entry<EquipmentSlot, ItemStack>> getThornsEquipment(LivingEntity entity) {
		List<Entry<EquipmentSlot, ItemStack>> thornsEquipment = Lists.newArrayList();
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
