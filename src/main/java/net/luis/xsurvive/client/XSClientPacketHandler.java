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
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSClientPacketHandler {
	
	public static void handlePlayerCapabilityUpdate(@NotNull CompoundTag tag) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			LocalPlayerHandler handler = PlayerProvider.getLocal(player);
			handler.deserializeNetwork(tag);
		}
	}
	
	public static void handleTridentGlintColorUpdate(int tridentEntityId, @NotNull ItemStack tridentStack) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null && level.getEntity(tridentEntityId) instanceof ThrownTrident trident) {
			trident.tridentItem = tridentStack;
		}
	}
	
	public static void handleEntityCapabilityUpdate(int entityId, @NotNull CompoundTag tag) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
			Entity entity = level.getEntity(entityId);
			if (entity != null) {
				EntityProvider.getClient(entity).deserializeNetwork(tag);
			}
		}
	}
}
