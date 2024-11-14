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

package net.luis.xsurvive.world.entity.projectile;

import net.luis.xsurvive.world.entity.XSEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
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
	
	private static final IntProvider CURSED_RAIN_TIME = UniformInt.of(6000, 12000); // 5-10 minutes
	
	public CursedEyeOfEnder(@NotNull EntityType<? extends EyeOfEnder> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	
	public CursedEyeOfEnder(@NotNull Level level, double x, double y, double z) {
		super(XSEntityTypes.CURSED_ENDER_EYE.get(), level);
		this.setPos(x, y, z);
	}
	
	@Override
	public void signalTo(@NotNull BlockPos pos) {
		super.signalTo(this.blockPosition().offset(0, 15, 0));
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
		if (this.level().isClientSide) {
			this.setPosRaw(x, y, z);
		} else {
			this.setPos(x, y, z);
			++this.life;
			if (this.life > 100 && this.level() instanceof ServerLevel level) {
				this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
				LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);
				if (lightning != null) {
					BlockPos.MutableBlockPos pos = this.getOnPos().mutable();
					for (; level.getBlockState(pos.immutable()).isAir(); pos.move(0, -1, 0)) ;
					lightning.moveTo(pos, lightning.getYRot(), lightning.getXRot());
					level.setWeatherParameters(0, CURSED_RAIN_TIME.sample(RandomSource.create()), true, true);
					level.addFreshEntity(lightning);
				}
				this.discard();
			}
		}
	}
}
