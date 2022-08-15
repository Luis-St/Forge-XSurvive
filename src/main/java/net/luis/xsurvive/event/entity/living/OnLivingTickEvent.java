package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.ILivingEntity;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomAi;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingTickEvent {
	
	@SubscribeEvent
	public static void livingTick(LivingEvent.LivingTickEvent event) {
		if (event.getEntity() instanceof ILivingEntity livingEntity) {
			if (livingEntity.hasCustomAi()) {
				CustomAi customAi = livingEntity.getCustomAi();
				if (customAi.canUse()) {
					customAi.tick();
				}
			}
		}
	}
	
}

