package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.server.capability.ServerPlayerHandler;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
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
		if (event.getEntity() instanceof ServerPlayer player && instance.getEffect() == XSMobEffects.FROST.get()) {
			ServerPlayerHandler handler = PlayerProvider.getServer(player);
			handler.setFrostTime(instance.getDuration());
		}
	}
	
}
