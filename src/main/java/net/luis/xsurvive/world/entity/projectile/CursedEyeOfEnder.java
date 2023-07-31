package net.luis.xsurvive.world.entity.projectile;

import net.luis.xsurvive.world.entity.XSEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class CursedEyeOfEnder extends EyeOfEnder {
	
	public CursedEyeOfEnder(EntityType<? extends EyeOfEnder> entityType, Level level) {
		super(entityType, level);
	}
	
	public CursedEyeOfEnder(Level level, double x, double y, double z) {
		super(XSEntityTypes.CURSED_ENDER_EYE.get(), level);
		this.setPos(x, y, z);
	}
	
	@Override
	public void signalTo(@NotNull BlockPos pos) {
		super.signalTo(this.blockPosition().offset(0, 10, 0));
		this.surviveAfterDeath = false;
	}
	
	@Override
	public void tick() {
		this.baseTick();
		Vec3 movement = this.getDeltaMovement();
		double x = this.getX() + movement.x;
		double y = this.getY() + movement.y;
		double z = this.getZ() + movement.z;
		double horizontalDistance = movement.horizontalDistance();
		if (!this.level().isClientSide) {
			double targetX = this.tx - x;
			double targetZ = this.tz - z;
			double sqrtTarget = Math.sqrt(targetX * targetX + targetZ * targetZ);
			double target = Mth.atan2(targetZ, targetX);
			double horizontalTarget = Mth.lerp(0.0025, horizontalDistance, sqrtTarget);
			double movementY = movement.y;
			if (sqrtTarget < 1.0) {
				horizontalTarget *= 0.8;
				movementY *= 0.9;
			}
			movement = new Vec3(Math.cos(target) * horizontalTarget, movementY + (1 - movementY) * 0.015, Math.sin(target) * horizontalTarget);
			this.setDeltaMovement(movement);
		}
		if (this.isInWater()) {
			for (int i = 0; i < 4; ++i) {
				this.level().addParticle(ParticleTypes.BUBBLE, x - movement.x * 0.25, y - movement.y * 0.25, z - movement.z * 0.25, movement.x, movement.y, movement.z);
			}
		} else {
			this.level().addParticle(ParticleTypes.ASH, x - movement.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3, y - movement.y * 0.25 - 0.5, z - movement.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3, 0.0, movement.y, 0.0);
		}
		if (!this.level().isClientSide) {
			this.setPos(x, y, z);
			++this.life;
			if (this.life > 100 && !this.level().isClientSide) {
				this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
				this.discard();
				if (this.surviveAfterDeath) {
					this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
				}
			}
		} else {
			this.setPosRaw(x, y, z);
		}
	}
}
