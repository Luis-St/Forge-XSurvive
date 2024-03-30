/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.mixin.entity;

import net.luis.xsurvive.world.entity.ILivingEntity;
import net.luis.xsurvive.world.entity.ai.custom.CustomAi;
import net.luis.xsurvive.world.entity.ai.custom.CustomAiManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
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
@SuppressWarnings("DataFlowIssue")
public abstract class LivingEntityMixin extends Entity implements ILivingEntity {
	
	//region Mixin
	private @Nullable CustomAi customAi;
	
	protected LivingEntityMixin(EntityType<?> entityType, Level level) {
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
	public @Nullable CustomAi getCustomAi() {
		return !this.level().isClientSide ? this.customAi : null;
	}
}
