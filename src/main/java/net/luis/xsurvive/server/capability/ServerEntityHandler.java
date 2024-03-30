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

import net.luis.xsurvive.capability.handler.AbstractEntityHandler;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateEntityCapabilityPacket;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Luis-St
 *
 */

public class ServerEntityHandler extends AbstractEntityHandler {
	
	private int lastSync;
	private boolean changed;
	
	public ServerEntityHandler(Entity entity) {
		super(entity);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (this.getEntity().isOnFire() && this.getFireType() == EntityFireType.NONE) {
			this.fireType = EntityFireType.FIRE;
			this.setChanged();
		} else if (!this.getEntity().isOnFire() && this.getFireType() != EntityFireType.NONE) {
			this.fireType = EntityFireType.NONE;
			this.setChanged();
		}
		if (this.changed) {
			this.broadcastChanges();
			this.lastSync = 0;
			this.changed = false;
		}
		this.lastSync++;
	}
	
	@Override
	public void setFireType(@Nullable EntityFireType fireType) {
		this.fireType = fireType == null ? EntityFireType.NONE : fireType;
		this.setChanged();
	}
	
	@Override
	public void setChanged() {
		this.changed = true;
	}
	
	@Override
	public void broadcastChanges() {
		XSNetworkHandler.INSTANCE.sendToPlayersInLevel((ServerLevel) this.getLevel(), new UpdateEntityCapabilityPacket(this.getEntity().getId(), this.serializeNetwork()));
		this.changed = false;
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeDisk() {
		CompoundTag tag = super.serializeDisk();
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(@NotNull CompoundTag tag) {
		super.deserializeDisk(tag);
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		
	}
	//endregion
}
