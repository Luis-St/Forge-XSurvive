package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.capability.CapabilityUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnEntityJoinWorldEvent {
	
	@SubscribeEvent
	public static void entityJoinWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof Player player) {
			CapabilityUtil.getPlayer(player).setChanged();
		}
	}
	
}
