package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Blaze.BlazeAttackGoal;
import net.minecraft.world.entity.projectile.SmallFireball;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(BlazeAttackGoal.class)
public abstract class BlazeAttackGoalMixin extends Goal {
	
	@Shadow
	@Final
	private Blaze blaze;
	@Shadow
	private int attackStep;
	@Shadow
	private int attackTime;
	@Shadow
	private int lastSeen;
	
	@Shadow
	protected abstract double getFollowDistance();
	
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(CallbackInfo callback) {
		--this.attackTime;
		LivingEntity target = this.blaze.getTarget();
		if (target != null) {
			boolean seeTarget = this.blaze.getSensing().hasLineOfSight(target);
			if (seeTarget) {
				this.lastSeen = 0;
			} else {
				++this.lastSeen;
			}
			double distance = this.blaze.distanceToSqr(target);
			if (4.0 > distance) {
				if (!seeTarget) {
					return;
				} else if (0 >= this.attackTime) {
					this.attackTime = 20;
					this.blaze.doHurtTarget(target);
				}
				this.blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
			} else if (distance < this.getFollowDistance() * this.getFollowDistance() && seeTarget) {
				double distanceX = target.getX() - this.blaze.getX();
				double distanceY = target.getY(0.5) - this.blaze.getY(0.5);
				double distanceZ = target.getZ() - this.blaze.getZ();
				if (0 >= this.attackTime) {
					++this.attackStep;
					if (this.attackStep == 1) {
						this.attackTime = 30;
						this.blaze.setCharged(true);
					} else if (3 >= this.attackStep) {
						this.attackTime = 6;
					} else {
						this.attackTime = 50;
						this.attackStep = 0;
						this.blaze.setCharged(false);
					}
					if (this.attackStep > 1) {
						double direction = Math.sqrt(Math.sqrt(distance)) * 0.5;
						if (!this.blaze.isSilent()) {
							this.blaze.level.levelEvent(null, 1018, this.blaze.blockPosition(), 0);
						}
						for (int i = 0; i < 3; ++i) {
							SmallFireball fireball = new SmallFireball(this.blaze.level, this.blaze, this.blaze.getRandom().triangle(distanceX, 2.297 * direction), distanceY, this.blaze.getRandom().triangle(distanceZ, 2.297 * direction));
							fireball.setPos(fireball.getX(), this.blaze.getY(0.5D) + 0.5D, fireball.getZ());
							this.blaze.level.addFreshEntity(fireball);
						}
					}
				}
				this.blaze.getLookControl().setLookAt(target, 10.0F, 10.0F);
			} else if (5 > this.lastSeen) {
				this.blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
			}
			super.tick();
		}
		callback.cancel();
	}
	
}
