package net.luis.xsurvive.client;

import net.luis.xsurvive.client.capability.LocalPlayerCapabilityHandler;
import net.luis.xsurvive.world.capability.CapabilityUtil;
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
		LocalPlayerCapabilityHandler handler = CapabilityUtil.getLocalPlayer(player);
		handler.deserializeNetwork(tag);
	}
	
}
