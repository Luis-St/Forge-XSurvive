package net.luis.xsurvive.mixin.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Phantom.class)
public class PhantomMixin extends FlyingMob {
	
	private PhantomMixin(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
	public void aiStep(CallbackInfo callback) {
		super.aiStep();
		callback.cancel();
	}
	
}
