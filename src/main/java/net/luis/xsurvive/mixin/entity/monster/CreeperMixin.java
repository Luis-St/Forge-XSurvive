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

package net.luis.xsurvive.mixin.entity.monster;

import net.luis.xsurvive.world.entity.monster.ICreeper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Creeper.class)
@SuppressWarnings("NonConstantFieldWithUpperCaseName")
public abstract class CreeperMixin extends Monster implements ICreeper {
	
	//region Mixin
	@Shadow private static EntityDataAccessor<Boolean> DATA_IS_POWERED;
	
	@Shadow private int explosionRadius = 3;
	
	private CreeperMixin(@NotNull EntityType<? extends Monster> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Override
	public void setExplosionRadius(int explosionRadius) {
		this.explosionRadius = explosionRadius;
	}
	
	@Override
	public void setPowered(boolean powered) {
		this.entityData.set(DATA_IS_POWERED, true);
	}
}
