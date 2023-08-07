package net.luis.xsurvive.mixin.entity;

import net.luis.xsurvive.world.entity.ILivingEntity;
import net.luis.xsurvive.world.entity.ai.custom.CustomAi;
import net.luis.xsurvive.world.entity.ai.custom.CustomAiManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
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

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntity {
	
	//region Mixin
	private CustomAi customAi;
	
	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}
	//endregion
	
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void init(CallbackInfo callback) {
		if (!this.level().isClientSide && CustomAiManager.INSTANCE.hasFactory((LivingEntity) (Object) this)) {
			this.customAi = CustomAiManager.INSTANCE.createFactory((LivingEntity) (Object) this, (ServerLevel) this.level());
		} else {
			this.customAi = null;
		}
	}
	
	@Override
	public boolean hasCustomAi() {
		return !this.level().isClientSide && this.customAi != null;
	}
	
	@Override
	public CustomAi getCustomAi() {
		return !this.level().isClientSide ? this.customAi : null;
	}
}
