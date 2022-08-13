package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerRespawnEvent {
	
	@SubscribeEvent
	public static void playerRespawn(PlayerRespawnEvent event) {
		PlayerProvider.get(event.getEntity()).setChanged();
	}
	
}

