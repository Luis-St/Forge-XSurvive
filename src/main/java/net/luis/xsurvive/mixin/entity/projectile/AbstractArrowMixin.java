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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
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

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile implements IArrow {
	
	private int explosionLevel;
	
	//region Mixin
	private AbstractArrowMixin(@NotNull EntityType<? extends Projectile> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	//endregion
	
	/*@Override
	public int getExplosionLevel() {
		return this.explosionLevel;
	}*/
	
	@Override
	public void setExplosionLevel(int explosionLevel) {
		this.explosionLevel = explosionLevel;
	}
	
	@Inject(method = "onHitBlock", at = @At("TAIL"))
	protected void onHitBlock(@NotNull BlockHitResult hitResult, @NotNull CallbackInfo callback) {
		if (this.explosionLevel > 0) {
			Vec3 location = hitResult.getLocation();
			this.level().explode(this.getOwner(), location.x(), location.y(), location.z(), this.explosionLevel, Level.ExplosionInteraction.BLOCK);
			this.discard();
		}
	}
	
	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(@NotNull CompoundTag tag, @NotNull CallbackInfo callback) {
		tag.putInt("ExplosionLevel", this.explosionLevel);
	}
	
	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(@NotNull CompoundTag tag, @NotNull CallbackInfo callback) {
		this.explosionLevel = tag.getInt("ExplosionLevel");
	}
}
