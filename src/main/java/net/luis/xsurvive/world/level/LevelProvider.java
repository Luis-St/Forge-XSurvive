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

package net.luis.xsurvive.world.level;

import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.capability.ClientLevelHandler;
import net.luis.xsurvive.server.capability.ServerLevelHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class LevelProvider implements ICapabilityProvider {
	
	private final LazyOptional<ILevel> optional;
	
	public LevelProvider(ILevel levelCapability) {
		this.optional = LazyOptional.of(() -> levelCapability);
	}
	
	public static @NotNull ILevel get(@NotNull Level level) {
		return level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
	}
	
	public static @NotNull ClientLevelHandler getClient(@NotNull Level level) {
		ILevel capability = level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientLevelHandler handler) {
			return handler;
		} else if (capability instanceof ServerLevelHandler) {
			throw new RuntimeException("Fail to get LocalPlayerHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static @NotNull ServerLevelHandler getServer(@NotNull Level level) {
		ILevel capability = level.getCapability(XSCapabilities.LEVEL, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientLevelHandler) {
			throw new RuntimeException("Fail to get ServerPlayerHandler from client");
		} else if (capability instanceof ServerLevelHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side) {
		return XSCapabilities.LEVEL.orEmpty(capability, this.optional);
	}
}
