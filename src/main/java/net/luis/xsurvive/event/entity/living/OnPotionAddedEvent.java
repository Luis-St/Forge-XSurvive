package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.server.capability.ServerPlayerCapabilityHandler;
import net.luis.xsurvive.world.capability.CapabilityUtil;
import net.luis.xsurvive.world.effect.XSurviveMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPotionAddedEvent {
	
	@SubscribeEvent
	public static void potionAdded(MobEffectEvent.Added event) {
		MobEffectInstance instance = event.getEffectInstance();
		if (event.getEntity() instanceof ServerPlayer player && instance.getEffect() == XSurviveMobEffects.FROST.get()) {
			ServerPlayerCapabilityHandler handler = CapabilityUtil.getServerPlayer(player);
			handler.setFrostTime(instance.getDuration());
		}
	}
	
}
