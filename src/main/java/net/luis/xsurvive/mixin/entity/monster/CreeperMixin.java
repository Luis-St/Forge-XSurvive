package net.luis.xsurvive.mixin.entity.monster;

import net.luis.xsurvive.world.entity.monster.ICreeper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Creeper.class)
@SuppressWarnings("NonConstantFieldWithUpperCaseName")
public abstract class CreeperMixin extends Monster implements ICreeper {
	
	//region Mixin
	@Shadow private static EntityDataAccessor<Boolean> DATA_IS_POWERED;
	
	@Shadow private int explosionRadius = 3;
	
	private CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Override
	public void setExplosionRadius(int explosionRadius) {
		this.explosionRadius = explosionRadius;
	}
	
	@Override
	public void setPowered(boolean powered) {
		this.entityData.set(DATA_IS_POWERED, true);
	}
}
