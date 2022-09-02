package net.luis.xsurvive.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(EyeOfEnder.class)
public abstract class EyeOfEnderMixin extends Entity {
	
	@Shadow
	private boolean surviveAfterDeath;
	
	private EyeOfEnderMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "signalTo", at = @At("TAIL"))
	public void signalTo(BlockPos pos, CallbackInfo callback) {
		this.surviveAfterDeath = this.random.nextInt(5) == 0;
	}
	
}
