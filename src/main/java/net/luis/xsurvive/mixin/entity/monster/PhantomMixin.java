package net.luis.xsurvive.mixin.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Phantom.class)
@SuppressWarnings("UnnecessarySuperQualifier")
public abstract class PhantomMixin extends FlyingMob {
	
	//region Mixin
	private PhantomMixin(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
	public void aiStep(@NotNull CallbackInfo callback) {
		super.aiStep();
		callback.cancel();
	}
}
