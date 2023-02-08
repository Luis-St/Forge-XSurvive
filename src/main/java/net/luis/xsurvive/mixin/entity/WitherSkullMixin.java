package net.luis.xsurvive.mixin.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
 * @author Luis-st
 *
 */

@Mixin(WitherSkull.class)
public abstract class WitherSkullMixin extends AbstractHurtingProjectile {
	
	private WitherSkullMixin(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
	protected void onHitEntity(EntityHitResult hitResult, CallbackInfo callback) {
		super.onHitEntity(hitResult);
		if (!this.level.isClientSide) {
			Entity target = hitResult.getEntity();
			boolean hurt;
			if (this.getOwner() instanceof LivingEntity owner) {
				hurt = target.hurt(DamageSource.witherSkull((WitherSkull) (Object) this, owner), 12.0F);
				if (hurt) {
					owner.heal(10.0F);
					if (target.isAlive()) {
						this.doEnchantDamageEffects(owner, target);
					}
				}
			} else {
				hurt = target.hurt(DamageSource.MAGIC, 10.0F);
			}
			if (hurt && target instanceof LivingEntity livingTarget) {
				int duration = 0;
				int amplifier = 0;
				switch (this.level.getDifficulty()) {
					case EASY -> duration = 10;
					case NORMAL -> {
						duration = 25;
						amplifier = 1;
					}
					case HARD -> {
						duration = 40;
						amplifier = 2;
					}
					default -> {
					}
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
		if (!this.level.isClientSide) {
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, false, Level.ExplosionInteraction.MOB);
			this.discard();
		}
		callback.cancel();
	}
	
}
