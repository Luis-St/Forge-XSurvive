package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(Guardian.class)
public abstract class GuardianMixin extends Monster {

	private GuardianMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}
	
	@Inject(method = "createAttributes", at = @At("HEAD"), cancellable = true)
	private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> callback) {
		callback.setReturnValue(Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 8.0).add(Attributes.MOVEMENT_SPEED, 0.65).add(Attributes.FOLLOW_RANGE, 32.0).add(Attributes.MAX_HEALTH, 40.0));
	}
	
}
