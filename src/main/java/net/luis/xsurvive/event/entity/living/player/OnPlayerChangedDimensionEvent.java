package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.capability.CapabilityUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerChangedDimensionEvent {
	
	@SubscribeEvent
	public static void playerChangedDimension(PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			CapabilityUtil.getServerPlayer(player).setChanged();
		}
	}
	
}
