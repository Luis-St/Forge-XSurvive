package net.luis.xsurvive.mixin.entity;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity {
	
	private EndCrystalMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}
	
	@Shadow
	protected abstract void onDestroyedBy(DamageSource source);
	
	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
		if (this.isInvulnerableTo(source)) {
			callback.setReturnValue(false);
		} else if (source.getEntity() instanceof EnderDragon) {
			callback.setReturnValue(false);
		} else if (source.isIndirect() || source.is(DamageTypeTags.IS_PROJECTILE)) {
			callback.setReturnValue(false);
		} else {
			if (!this.isRemoved() && !this.level().isClientSide) {
				this.remove(Entity.RemovalReason.KILLED);
				if (!source.is(DamageTypeTags.IS_EXPLOSION)) {
					this.level().explode(null, this.getX(), this.getY(), this.getZ(), 9.0F, Level.ExplosionInteraction.BLOCK);
				}
				this.onDestroyedBy(source);
			}
			callback.setReturnValue(true);
		}
	}
}
