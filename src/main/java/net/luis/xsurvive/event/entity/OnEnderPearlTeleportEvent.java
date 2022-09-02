package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnEnderPearlTeleportEvent {
	
	@SubscribeEvent
	public static void projectileImpact(EnderPearl event) {
		event.setAttackDamage(event.getAttackDamage() * 1.5F);
	}
	
}
