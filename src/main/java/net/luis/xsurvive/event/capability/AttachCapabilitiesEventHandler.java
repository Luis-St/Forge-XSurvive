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

package net.luis.xsurvive.event.capability;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.capability.*;
import net.luis.xsurvive.server.capability.*;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class AttachCapabilitiesEventHandler {
	
	@SubscribeEvent
	public static void attachEntityCapabilities(@NotNull AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof ServerPlayer player) {
			event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "server_player_capability"), new PlayerProvider(new ServerPlayerHandler(player)));
		}
		if (!entity.level().isClientSide) {
			event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "server_entity_capability"), new EntityProvider(new ServerEntityHandler(entity)));
			if (entity instanceof Villager) {
				event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "server_villager_capability"), new VillagerProvider(new ServerVillagerHandler()));
			}
		}
	}
	
	@SubscribeEvent
	public static void attachLevelCapabilities(@NotNull AttachCapabilitiesEvent<Level> event) {
		if (event.getObject() instanceof ServerLevel level) {
			event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "level_capability"), new LevelProvider(new ServerLevelHandler(level)));
		}
	}
	
	@EventBusSubscriber(modid = XSurvive.MOD_ID, value = Dist.CLIENT)
	public static class Client {
		
		@SubscribeEvent
		public static void attachEntityCapabilities(@NotNull AttachCapabilitiesEvent<Entity> event) {
			Entity entity = event.getObject();
			if (entity instanceof LocalPlayer player) {
				event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "local_player_capability"), new PlayerProvider(new LocalPlayerHandler(player)));
			}
			if (entity.level().isClientSide) {
				event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "client_entity_capability"), new EntityProvider(new ClientEntityHandler(entity)));
			}
		}
		
		@SubscribeEvent
		public static void attachLevelCapabilities(@NotNull AttachCapabilitiesEvent<Level> event) {
			if (event.getObject() instanceof ClientLevel level) {
				event.addCapability(ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "level_capability"), new LevelProvider(new ClientLevelHandler(level)));
			}
		}
	}
}
