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

package net.luis.xsurvive.mixin.entity.projectile;

import net.luis.xsurvive.world.entity.projectile.IArrow;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Arrow.class)
public abstract class ArrowMixin implements IArrow {
	
	private int explosionLevel;
	
	@Override
	public int getExplosionLevel() {
		return this.explosionLevel;
	}
	
	@Override
	public void setExplosionLevel(int explosionLevel) {
		this.explosionLevel = explosionLevel;
	}
}
