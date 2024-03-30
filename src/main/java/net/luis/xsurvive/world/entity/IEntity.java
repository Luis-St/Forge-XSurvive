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

package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.capability.ICapability;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface IEntity extends ICapability, INetworkCapability {
	
	@NotNull Entity getEntity();
	
	default Level getLevel() {
		return this.getEntity().level();
	}
	
	void tick();
	
	@NotNull EntityFireType getFireType();
	
	default void setFireType(@NotNull EntityFireType fireType) {}
}
