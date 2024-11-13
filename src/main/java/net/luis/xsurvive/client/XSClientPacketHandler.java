/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.client;

import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.level.LevelProvider;
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
			trident.pickupItemStack = tridentStack;
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
	
	public static void handleLevelCapabilityUpdate(@NotNull CompoundTag tag) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
			LevelProvider.getClient(level).deserializeNetwork(tag);
		}
	}
}
