package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.player.IPlayer;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerCloneEvent {
	
	@SubscribeEvent
	public static void playerClone(PlayerEvent.Clone event) {
		if (event.getOriginal() instanceof ServerPlayer original && event.getEntity() instanceof ServerPlayer player) {
			original.reviveCaps();
			IPlayer originalHandler = PlayerProvider.get(original);
			IPlayer handler = PlayerProvider.get(player);
			if (event.isWasDeath()) {
				handler.deserializePersistent(originalHandler.serializePersistent());
			} else {
				handler.deserializeDisk(originalHandler.serializeDisk());
			}
			original.invalidateCaps();
		}
	}
	
}
