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

package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.world.entity.npc.IVillager;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class ServerVillagerHandler implements IVillager {
	
	private int resetCount;
	
	@Override
	public int getResetCount() {
		return this.resetCount;
	}
	
	@Override
	public void resetResetCount() {
		this.resetCount = 0;
	}
	
	@Override
	public void increaseResetCount() {
		this.resetCount++;
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeDisk(HolderLookup.@NotNull Provider lookup) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("reset_count", this.resetCount);
		return tag;
	}
	
	@Override
	public void deserializeDisk(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		this.resetCount = tag.getInt("reset_count");
	}
	//endregion
}
