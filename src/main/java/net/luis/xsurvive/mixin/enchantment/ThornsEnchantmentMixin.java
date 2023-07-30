package net.luis.xsurvive.mixin.enchantment;

import com.google.common.collect.Lists;
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
				entry.getValue().hurtAndBreak(1, target, (entity) -> {
					entity.broadcastBreakEvent(entry.getKey());
				});
			}
		}
		callback.cancel();
	}
	
	private int getThornsLevel(@NotNull LivingEntity entity, EquipmentSlot slot) {
		return entity.getItemBySlot(slot).getEnchantmentLevel((ThornsEnchantment) (Object) this);
	}
	
	private @NotNull List<Entry<EquipmentSlot, ItemStack>> getThornsEquipment(LivingEntity entity) {
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
