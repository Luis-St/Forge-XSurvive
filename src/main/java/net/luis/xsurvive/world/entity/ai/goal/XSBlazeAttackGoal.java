package net.luis.xsurvive.world.entity.ai.goal;

import java.util.EnumSet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.SmallFireball;

/**
 * 
 * @author Luis-st
 *
 */

public class XSBlazeAttackGoal extends Goal {
	
	private final Blaze blaze;
	private int attackStep;
	private int attackTime;
	private int lastSeen;
	
	public XSBlazeAttackGoal(Blaze blaze) {
		this.blaze = blaze;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	
	@Override
	public boolean canUse() {
		LivingEntity livingentity = this.blaze.getTarget();
		return livingentity != null && livingentity.isAlive() && this.blaze.canAttack(livingentity);
	}
	
	@Override
	public void start() {
		this.attackStep = 0;
	}
	
	@Override
	public void stop() {
		this.blaze.setCharged(false);
		this.lastSeen = 0;
	}
	
	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}
	
	@Override
	public void tick() {
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
	}
	
	private double getFollowDistance() {
		return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
	}
}
