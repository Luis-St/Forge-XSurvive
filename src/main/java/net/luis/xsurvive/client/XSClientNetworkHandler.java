package net.luis.xsurvive.client;

import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;

/**
 * 
 * @author Luis-st
 *
 */

public class XSClientNetworkHandler {
	
	@SuppressWarnings("resource")
	public static void handleCapabilityUpdate(CompoundTag tag) {
		LocalPlayer player = Minecraft.getInstance().player;
		LocalPlayerHandler handler = PlayerProvider.getLocal(player);
		handler.deserializeNetwork(tag);
	}
	
}
