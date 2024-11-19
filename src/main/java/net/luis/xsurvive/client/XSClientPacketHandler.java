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
import net.luis.xsurvive.core.components.XSDataComponents;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

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
	
	public static void handleTridentGlintColorUpdate(int tridentEntityId, int glintColor, @NotNull Set<ResourceLocation> enchantmentKeys) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null && level.getEntity(tridentEntityId) instanceof ThrownTrident trident) {
			ItemStack tridentStack = new ItemStack(Items.TRIDENT);
			if (glintColor >= 0) {
				tridentStack.set(XSDataComponents.GLINT_COLOR.get(), glintColor);
			}
			Registry<Enchantment> registry = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
			ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
			for (ResourceLocation enchantmentKey : enchantmentKeys) {
				registry.get(enchantmentKey).ifPresent(enchantmentReference -> enchantments.set(enchantmentReference, 1));
			}
			tridentStack.set(DataComponents.ENCHANTMENTS, enchantments.toImmutable());
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
