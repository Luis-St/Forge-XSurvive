package net.luis.xsurvive.event.capability;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, value = Dist.CLIENT)
public class OnClientAttachCapabilitiesEvent {
	
	@SubscribeEvent
	public static void AttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof LocalPlayer player) {
			event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "local_player_capability"), new PlayerProvider(new LocalPlayerHandler(player)));
		}
	}
	
}
