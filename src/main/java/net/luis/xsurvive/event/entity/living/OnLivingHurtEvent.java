package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.common.handler.EnchantmentHandler;
import net.luis.xsurvive.init.XSurviveEnchantments;
import net.luis.xsurvive.init.XSurviveMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingHurtEvent {
	
	@SubscribeEvent
	public static void livingHurt(LivingHurtEvent event) {
		Entity target = event.getEntity();
		DamageSource source = event.getSource();
		if (source instanceof EntityDamageSource entitySource && entitySource.getEntity() instanceof LivingEntity livingAttacker && target instanceof LivingEntity livingTarget) {
			int poisonAspect = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.POISON_ASPECT.get(), livingAttacker);
			int frostAspect = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.FROST_ASPECT.get(), livingAttacker);
			if (poisonAspect > 0) {
				livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100 * poisonAspect), livingAttacker);
			}
			if (frostAspect > 0) {
				livingTarget.addEffect(new MobEffectInstance(XSurviveMobEffects.FROST.get(), 100 * frostAspect), livingAttacker);
				livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 * frostAspect, 2, false, false, false), livingAttacker);
			}
		}
	}
	
}

