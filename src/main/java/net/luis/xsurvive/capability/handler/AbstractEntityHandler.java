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

package net.luis.xsurvive.capability.handler;

import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class AbstractEntityHandler implements IEntity {
	
	private final Entity entity;
	private int tick;
	protected EntityFireType fireType = EntityFireType.NONE;
	
	protected AbstractEntityHandler(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public @NotNull Entity getEntity() {
		return this.entity;
	}
	
	@Override
	public void tick() {
		this.tick++;
	}
	
	@Override
	public @NotNull EntityFireType getFireType() {
		return this.fireType;
	}
	
	//region NBT
	private @NotNull CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("fire_type", this.fireType.ordinal());
		return tag;
	}
	
	private void deserialize(@NotNull CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.fireType = EntityFireType.byOrdinal(tag.getInt("fire_type"), EntityFireType.NONE);
	}
	
	@Override
	public @NotNull CompoundTag serializeDisk() {
		return this.serialize();
	}
	
	@Override
	public void deserializeDisk(@NotNull CompoundTag tag) {
		this.deserialize(tag);
	}
	
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		return this.serialize();
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		this.deserialize(tag);
	}
	//endregion
}
