package net.luis.xsurvive.mixin.entity;

import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Entity.class)
public abstract class EntityMixin {
	
	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo callback) {
		EntityProvider.getSafe((Entity) (Object) this).ifPresent(IEntity::tick);
	}
}
