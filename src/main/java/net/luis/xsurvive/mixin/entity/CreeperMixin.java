package net.luis.xsurvive.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.luis.xsurvive.world.level.entity.monster.ICreeper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster implements ICreeper {
	
	@Shadow
	private static EntityDataAccessor<Boolean> DATA_IS_POWERED;
	
	@Shadow
	private int explosionRadius = 3;
	
	private CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public int getExplosionRadius() {
		return this.explosionRadius;
	}

	@Override
	public void setExplosionRadius(int explosionRadius) {
		this.explosionRadius = explosionRadius;
	}

	@Override
	public void setPowered(boolean powered) {
		this.entityData.set(DATA_IS_POWERED, true);
	}
	
}
