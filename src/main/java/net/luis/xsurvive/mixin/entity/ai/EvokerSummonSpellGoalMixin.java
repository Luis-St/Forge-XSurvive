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

package net.luis.xsurvive.mixin.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("DollarSignInName")
@Mixin(targets = "net.minecraft.world.entity.monster.Evoker$EvokerSummonSpellGoal")
public abstract class EvokerSummonSpellGoalMixin {
	
	//region Mixin
	@Shadow private Evoker this$0;
	//endregion
	
	@Inject(method = "performSpellCasting", at = @At("HEAD"), cancellable = true)
	protected void performSpellCasting(CallbackInfo callback) {
		if (this.this$0.level() instanceof ServerLevel level) {
			int i = this.this$0.getRandom().nextInt(5);
			for (int j = 0; j < 3 + i; ++j) {
				BlockPos pos = this.this$0.blockPosition().offset(-2 + this.this$0.getRandom().nextInt(5), 1, -2 + this.this$0.getRandom().nextInt(5));
				Vex vex = EntityType.VEX.create(level);
				if (vex != null) {
					vex.moveTo(pos, 0.0F, 0.0F);
					vex.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
					vex.setOwner(this.this$0);
					vex.setBoundOrigin(pos);
					level.addFreshEntityWithPassengers(vex);
				}
			}
		}
		callback.cancel();
	}
}
