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

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(WitherBoss.class)
public abstract class WitherBossMixin extends Monster {
	
	//region Mixin
	@Shadow private int[] idleHeadUpdates;
	@Shadow private int destroyBlocksTick;
	@Shadow private ServerBossEvent bossEvent;
	
	private WitherBossMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}
	
	@Shadow
	public abstract int getInvulnerableTicks();
	
	@Shadow
	public abstract void setInvulnerableTicks(int invulnerableTicks);
	//endregion
	
	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
		if (this.isInvulnerableTo(source)) {
			callback.setReturnValue(false);
		} else if (!source.is(DamageTypeTags.IS_DROWNING) && !(source.getEntity() instanceof WitherBoss)) {
			if (this.getInvulnerableTicks() > 0 && source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
				callback.setReturnValue(false);
			} else {
				if (source.getDirectEntity() instanceof AbstractArrow arrow && 4 > arrow.getPierceLevel()) {
					callback.setReturnValue(false);
				} else {
					if (source.getEntity() instanceof LivingEntity livingEntity && !(livingEntity instanceof Player) && livingEntity.getMobType() == this.getMobType()) {
						callback.setReturnValue(false);
					} else {
						if (this.destroyBlocksTick <= 0) {
							this.destroyBlocksTick = 20;
						}
						for (int i = 0; i < this.idleHeadUpdates.length; ++i) {
							this.idleHeadUpdates[i] += 3;
						}
						callback.setReturnValue(super.hurt(source, amount));
					}
				}
			}
		} else {
			callback.setReturnValue(false);
		}
	}
	
	@Inject(method = "makeInvulnerable", at = @At("HEAD"), cancellable = true)
	public void makeInvulnerable(@NotNull CallbackInfo callback) {
		this.setInvulnerableTicks(80);
		this.bossEvent.setProgress(0.0F);
		callback.cancel();
	}
	
	@Inject(method = "isPowered", at = @At("HEAD"), cancellable = true)
	public void isPowered(@NotNull CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(true);
	}
}
