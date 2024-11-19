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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Vex.class)
public abstract class VexMixin extends Monster implements TraceableEntity {
	
	//region Mixin
	private VexMixin(@NotNull EntityType<? extends Monster> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "tick", at = @At("RETURN"))
	public void tick(@NotNull CallbackInfo callback) {
		if (this.getOwner() instanceof Evoker evoker && !evoker.isAlive()) {
			this.hurt(this.damageSources().starve(), Float.MAX_VALUE);
		}
	}
}
