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

package net.luis.xsurvive.world.entity.player;

import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.server.capability.ServerPlayerHandler;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Luis-St
 *
 */

public class PlayerProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IPlayer playerCapability;
	private final LazyOptional<IPlayer> optional;
	
	public PlayerProvider(@NotNull IPlayer playerCapability) {
		this.playerCapability = playerCapability;
		this.optional = LazyOptional.of(() -> this.playerCapability);
	}
	
	public static @NotNull IPlayer get(@NotNull Player player) {
		return player.getCapability(XSCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
	}
	
	public static @NotNull LazyOptional<IPlayer> getSafe(@NotNull Player player) {
		return player.getCapability(XSCapabilities.PLAYER, null);
	}
	
	public static @NotNull LocalPlayerHandler getLocal(@NotNull Player player) {
		IPlayer capability = player.getCapability(XSCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerHandler handler) {
			return handler;
		} else if (capability instanceof ServerPlayerHandler) {
			throw new RuntimeException("Fail to get LocalPlayerHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static @NotNull ServerPlayerHandler getServer(@NotNull Player player) {
		IPlayer capability = player.getCapability(XSCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerHandler) {
			throw new RuntimeException("Fail to get ServerPlayerHandler from client");
		} else if (capability instanceof ServerPlayerHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
		return XSCapabilities.PLAYER.orEmpty(capability, this.optional);
	}
	
	@Override
	public @NotNull CompoundTag serializeNBT(HolderLookup.@NotNull Provider lookup) {
		return this.playerCapability.serializeDisk(lookup);
	}
	
	@Override
	public void deserializeNBT(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		this.playerCapability.deserializeDisk(lookup, tag);
	}
}
