package net.luis.xsurvive.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.luis.xsurvive.world.entity.EntityProvider;
import net.minecraft.world.entity.Entity;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Entity.class)
public abstract class EntityMixin {
	
	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo callback) {
		EntityProvider.get((Entity) (Object) this).tick();
	}
	
}
