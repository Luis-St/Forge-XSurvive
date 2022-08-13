package net.luis.xsurvive.client;

import net.luis.xsurvive.client.capability.LocalPlayerCapabilityHandler;
import net.luis.xsurvive.world.level.entity.player.PlayerCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveClientNetworkHandler {
	
	@SuppressWarnings("resource")
	public static void handleCapabilityUpdate(CompoundTag tag) {
		LocalPlayer player = Minecraft.getInstance().player;
		LocalPlayerCapabilityHandler handler = PlayerCapabilityProvider.getLocal(player);
		handler.deserializeNetwork(tag);
	}
	
}
