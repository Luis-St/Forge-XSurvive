package net.luis.xsurvive.world.capability;

import javax.annotation.Nullable;

import net.luis.xsurvive.client.capability.LocalPlayerCapabilityHandler;
import net.luis.xsurvive.server.capability.ServerPlayerCapabilityHandler;
import net.minecraft.world.entity.player.Player;

/**
 * 
 * @author Luis-st
 *
 */

public class CapabilityUtil {
	
	public static IPlayerCapability getPlayer(Player player) {
		return player.getCapability(XSurviveCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
	}
	
	@Nullable
	public static LocalPlayerCapabilityHandler getLocalPlayer(Player player) {
		IPlayerCapability capability = player.getCapability(XSurviveCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerCapabilityHandler handler) {
			return handler;
		} else if (capability instanceof ServerPlayerCapabilityHandler handler) {
			throw new IllegalStateException("Fail to get LocalPlayerCapabilityHandler from Server");
		}
		return null;
	}
	
	@Nullable
	public static ServerPlayerCapabilityHandler getServerPlayer(Player player) {
		IPlayerCapability capability = player.getCapability(XSurviveCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerCapabilityHandler handler) {
			throw new IllegalStateException("Fail to get ServerPlayerCapabilityHandler from Client");
		} else if (capability instanceof ServerPlayerCapabilityHandler handler) {
			return handler;
		}
		return null;
	}
	
}
