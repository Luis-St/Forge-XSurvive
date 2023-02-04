package net.luis.xsurvive.client;

import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * 
 * @author Luis-st
 *
 */

public class XSClientPacketHandler {
	
	@SuppressWarnings("resource")
	public static void handlePlayerCapabilityUpdate(CompoundTag tag) {
		LocalPlayer player = Objects.requireNonNull(Minecraft.getInstance().player);
		LocalPlayerHandler handler = PlayerProvider.getLocal(player);
		handler.deserializeNetwork(tag);
	}
	
	@SuppressWarnings("resource")
	public static void handleTridentGlintColorUpdate(int tridentEntityId, ItemStack tridentStack) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
			if (level.getEntity(tridentEntityId) instanceof ThrownTrident trident) {
				trident.tridentItem = tridentStack;
			}
		}
	}

	@SuppressWarnings("resource")
	public static void handleEntityCapabilityUpdate(int entityId, CompoundTag tag) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
			Entity entity = level.getEntity(entityId);
			if (entity != null) {
				EntityProvider.getClient(entity).deserializeNetwork(tag);
			}
		}
	}
	
}
