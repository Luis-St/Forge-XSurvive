package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.world.item.enchantment.EnchantmentHandler;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber
public class OnLivingExperienceDropEvent {
	
	@SubscribeEvent
	public static void livingExperienceDrop(LivingExperienceDropEvent event) {
		Player player = event.getAttackingPlayer();
		int xp = event.getOriginalExperience();
		if (player != null) {
			int experience = EnchantmentHandler.getEnchantmentLevel(XSurviveEnchantments.EXPERIENCE.get(), player);
			int looting = EnchantmentHandler.getEnchantmentLevel(Enchantments.MOB_LOOTING, player);
			if (player != null && xp > 0 && experience > 0) {
				event.setDroppedExperience(xp * ((experience + 1) * ((experience * 2) + looting)));
			}
		}
	}
	
}
