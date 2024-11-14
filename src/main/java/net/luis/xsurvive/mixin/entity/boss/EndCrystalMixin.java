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

package net.luis.xsurvive.mixin.entity.boss;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity {
	
	//region Mixin
	private EndCrystalMixin(@NotNull EntityType<?> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	
	@Shadow
	protected abstract void onDestroyedBy(@NotNull ServerLevel serverLevel, @NotNull DamageSource source);
	//endregion
	
	@Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
	public void hurtServer(@NotNull ServerLevel serverLevel, @NotNull DamageSource source, float amount, @NotNull CallbackInfoReturnable<Boolean> callback) {
		if (this.isInvulnerableToBase(source)) {
			callback.setReturnValue(false);
		} else if (source.getEntity() instanceof EnderDragon) {
			callback.setReturnValue(false);
		} else if (!source.isDirect() || source.is(DamageTypeTags.IS_PROJECTILE)) {
			callback.setReturnValue(false);
		} else {
			if (!this.isRemoved() && !this.level().isClientSide) {
				this.remove(Entity.RemovalReason.KILLED);
				if (!source.is(DamageTypeTags.IS_EXPLOSION)) {
					this.level().explode(null, this.getX(), this.getY(), this.getZ(), 9.0F, Level.ExplosionInteraction.BLOCK);
				}
				this.onDestroyedBy(serverLevel, source);
			}
			callback.setReturnValue(true);
		}
	}
}
