package net.luis.xsurvive.mixin.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Vex.class)
public abstract class VexMixin extends Monster implements TraceableEntity {
	
	//region Mixin
	private VexMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "tick", at = @At("RETURN"))
	public void tick(CallbackInfo callback) {
		if (this.getOwner() instanceof Evoker evoker && !evoker.isAlive()) {
			this.hurt(this.damageSources().starve(), Float.MAX_VALUE);
		}
	}
}
