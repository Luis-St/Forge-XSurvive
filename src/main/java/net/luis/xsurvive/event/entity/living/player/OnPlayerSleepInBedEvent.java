package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerSleepInBedEvent {
	
	@SubscribeEvent
	public static void playerSleepInBed(PlayerSleepInBedEvent event) {
		if (!event.getEntity().getAbilities().instabuild) {
			event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
		}
	}
	
}
