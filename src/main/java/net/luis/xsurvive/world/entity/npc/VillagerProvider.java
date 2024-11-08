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

package net.luis.xsurvive.world.entity.npc;

import net.luis.xsurvive.capability.XSCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.npc.Villager;
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

public class VillagerProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IVillager villagerCapability;
	private final LazyOptional<IVillager> optional;
	
	public VillagerProvider(@NotNull IVillager playerCapability) {
		this.villagerCapability = playerCapability;
		this.optional = LazyOptional.of(() -> this.villagerCapability);
	}
	
	public static @NotNull IVillager get(@NotNull Villager villager) {
		return villager.getCapability(XSCapabilities.VILLAGER, null).orElseThrow(NullPointerException::new);
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
		return XSCapabilities.VILLAGER.orEmpty(capability, this.optional);
	}
	
	@Override
	public @NotNull CompoundTag serializeNBT(HolderLookup.@NotNull Provider lookup) {
		return this.villagerCapability.serializeDisk(lookup);
	}
	
	@Override
	public void deserializeNBT(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		this.villagerCapability.deserializeDisk(lookup, tag);
	}
}
