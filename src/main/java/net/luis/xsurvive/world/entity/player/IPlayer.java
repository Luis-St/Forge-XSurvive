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

import net.luis.xsurvive.capability.ICapability;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@AutoRegisterCapability
public interface IPlayer extends ICapability, INetworkCapability {
	
	@NotNull Player getPlayer();
	
	default @NotNull Level getLevel() {
		return this.getPlayer().level();
	}
	
	void tick();
	
	int getFrostTime();
	
	default void setFrostTime(int frostTime) {}
	
	double getFrostPercent();
	
	int getEndAspectCooldown();
	
	default void setEndAspectCooldown(int endAspectCooldown) {}
	
	double getEndAspectPercent();
	
	@NotNull CombinedInvWrapper getCombinedInventory();
	
	@NotNull Optional<@Nullable BlockPos> getContainerPos();
	
	@NotNull CompoundTag serializePersistent();
	
	void deserializePersistent(@NotNull CompoundTag tag);
}
