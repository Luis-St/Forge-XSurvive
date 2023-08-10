package net.luis.xsurvive.mixin.entity.projectile;

import net.luis.xsurvive.world.entity.projectile.IArrow;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Arrow.class)
public abstract class ArrowMixin implements IArrow {
	
	private int explosionLevel;
	
	@Override
	public int getExplosionLevel() {
		return this.explosionLevel;
	}
	
	@Override
	public void setExplosionLevel(int explosionLevel) {
		this.explosionLevel = explosionLevel;
	}
}
