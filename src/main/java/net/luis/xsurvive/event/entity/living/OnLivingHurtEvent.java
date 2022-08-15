package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingHurtEvent {
	
	@SubscribeEvent
	public static void livingHurt(LivingHurtEvent event) {
		Entity target = event.getEntity();
		DamageSource source = event.getSource();
		if (source instanceof EntityDamageSource entitySource && entitySource.getEntity() instanceof LivingEntity livingAttacker && target instanceof LivingEntity livingTarget) {
			int poisonAspect = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.POISON_ASPECT.get(), livingAttacker);
			int frostAspect = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.FROST_ASPECT.get(), livingAttacker);
			if (poisonAspect > 0) {
				livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100 * poisonAspect), livingAttacker);
			}
			if (frostAspect > 0) {
				livingTarget.addEffect(new MobEffectInstance(XSMobEffects.FROST.get(), 100 * frostAspect), livingAttacker);
			}
		}
	}
	
}

