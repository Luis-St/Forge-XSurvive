package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.EnchantmentHandler;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.luis.xsurvive.world.level.entity.EntityHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingDamageEvent {
	
	@SubscribeEvent
	public static void livingDamage(LivingDamageEvent event) {
		Entity target = event.getEntity();
		DamageSource source = event.getSource();
		float amount = event.getAmount();
		float newAmount = amount;
		if (source instanceof EntityDamageSource entitySource && entitySource.getEntity() instanceof Player player) {
			int enderSlayer = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.ENDER_SLAYER.get(), player);
			int impaling = EnchantmentHandler.getEnchantmentLevel(Enchantments.IMPALING, player);
			if (enderSlayer > 0 && EntityHelper.isAffectedByEnderSlayer(target)) {
				newAmount = (float) (amount * 2.5);
			}
			if (impaling > 0 && EntityHelper.isAffectedByImpaling(target)) {
				newAmount = (float) (amount * 2.5);
			}
		}
		if (target instanceof Player player && source == DamageSource.OUT_OF_WORLD && amount > 0) {
			int voidProtection = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.VOID_PROTECTION.get(), player);
			if (voidProtection > 0) {
				float percent = switch (voidProtection) {
					case 1 -> 0.8F;
					case 2 -> 0.6F;
					case 3 -> 0.4F;
					case 4 -> 0.2F;
					default -> 0.0F;
				};
				newAmount = amount * percent;
			}
		}
		event.setAmount(newAmount);
	}
	
}
