package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnShieldBlockEvent {
	
	@SubscribeEvent
	public static void shieldBlock(ShieldBlockEvent event) {
		if (event.getEntity() instanceof Player player) {
			if (event.getDamageSource() instanceof IndirectEntityDamageSource source && source.getEntity() instanceof Blaze attacker) {
				event.setCanceled(true);
			}
		}
	}
	
}
