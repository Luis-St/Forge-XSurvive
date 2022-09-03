package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnBreakSpeedEvent {
	
	@SubscribeEvent
	public static void anvilRepair(BreakSpeed event) {
		if (event.getState().is(Blocks.SPAWNER)) {
			event.setCanceled(true);
		}
	}
	
}
