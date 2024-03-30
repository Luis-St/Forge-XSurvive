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

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(WitherSkull.class)
@SuppressWarnings({ "DataFlowIssue", "UnnecessarySuperQualifier" })
public abstract class WitherSkullMixin extends AbstractHurtingProjectile {
	
	//region Mixin
	private WitherSkullMixin(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
	protected void onHitEntity(EntityHitResult hitResult, CallbackInfo callback) {
		super.onHitEntity(hitResult);
		if (!this.level().isClientSide) {
			Entity target = hitResult.getEntity();
			boolean hurt;
			if (this.getOwner() instanceof LivingEntity owner) {
				hurt = target.hurt(this.damageSources().witherSkull((WitherSkull) (Object) this, owner), 12.0F);
				if (hurt) {
					owner.heal(10.0F);
					if (target.isAlive()) {
						this.doEnchantDamageEffects(owner, target);
					}
				}
			} else {
				hurt = target.hurt(this.damageSources().magic(), 10.0F);
			}
			if (hurt && target instanceof LivingEntity livingTarget) {
				int duration = 0;
				int amplifier = 0;
				switch (this.level().getDifficulty()) {
					case EASY -> duration = 10;
					case NORMAL -> {
						duration = 25;
						amplifier = 1;
					}
					case HARD -> {
						duration = 40;
						amplifier = 2;
					}
					default -> {}
				}
				if (duration > 0) {
					livingTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * duration, 1 + amplifier), this.getEffectSource());
				}
			}
		}
		callback.cancel();
	}
	
	@Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
	protected void onHit(HitResult hitResult, CallbackInfo callback) {
		super.onHit(hitResult);
		if (!this.level().isClientSide) {
			this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, false, Level.ExplosionInteraction.MOB);
			this.discard();
		}
		callback.cancel();
	}
}
