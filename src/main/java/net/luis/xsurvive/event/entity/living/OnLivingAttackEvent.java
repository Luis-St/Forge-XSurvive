package net.luis.xsurvive.event.entity.living;

import java.util.Random;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.EnchantmentHandler;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingAttackEvent {
	
	public static final Random RNG = new Random(System.currentTimeMillis());
	
	@SubscribeEvent
	public static void livingAttack(LivingAttackEvent event) {
		Entity target = event.getEntity();
		DamageSource source = event.getSource();
		float amount = event.getAmount();
		if (source instanceof EntityDamageSource entitySource && entitySource.getEntity() instanceof Player player) {
			if (target instanceof LivingEntity livingTarget && amount > 0.0F) {
				int harmingCurse = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.CURSE_OF_HARMING.get(), player);
				int thunderbolt = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.THUNDERBOLT.get(), player);
				if (harmingCurse > 0) {
					float damage = (amount / 2.0F) * (float) harmingCurse;
					if (player.hurt(new EntityDamageSource("curse_of_harming", livingTarget), damage)) {
						event.setCanceled(true);
					}
				}
				if (thunderbolt > 0 && player.level instanceof ServerLevel serverLevel) {
					LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
					lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
					serverLevel.addFreshEntity(lightningBolt);
					if (RNG.nextInt(5) == 0) {
						serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + RNG.nextFloat() * 0.2F);
					}
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 4, false, false), lightningBolt);
				}
			}
		}
		if (target instanceof Player player && source == DamageSource.OUT_OF_WORLD && amount > 0) {
			int voidProtection = player.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(XSurviveEnchantments.VOID_PROTECTION.get());
			if (voidProtection > 0) {
				float percent = switch (voidProtection) {
					case 1 -> 1.0F;
					case 2 -> 0.75F;
					case 3 -> 0.5F;
					case 4 -> 0.25F;
					default -> 0.0F;
				};
				event.setCanceled(RNG.nextDouble() > percent);
			}
		}
	}
	
}
