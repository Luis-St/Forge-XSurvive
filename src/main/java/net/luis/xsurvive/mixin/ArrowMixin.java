package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.luis.xsurvive.world.level.entity.projectile.IArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Arrow.class)
public abstract class ArrowMixin extends AbstractArrow implements IArrow {
	
	private int explosionLevel = 0;
	
	private ArrowMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	public int getExplosionLevel() {
		return this.explosionLevel;
	}
	
	@Override
	public void setExplosionLevel(int explosionLevel) {
		this.explosionLevel = explosionLevel;
	}
	
}
