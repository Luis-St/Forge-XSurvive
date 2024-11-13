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

package net.luis.xsurvive.mixin.entity.villager;

import net.minecraft.world.entity.npc.VillagerData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(VillagerData.class)
@SuppressWarnings({ "StaticVariableUsedBeforeInitialization", "NonConstantFieldWithUpperCaseName" })
public abstract class VillagerDataMixin {
	
	//region Mixin
	@Shadow private static int[] NEXT_LEVEL_XP_THRESHOLDS;
	
	@Shadow
	private static boolean canLevelUp(int level) {
		return false;
	}
	//endregion
	
	@Inject(method = "getMaxXpPerLevel", at = @At("HEAD"), cancellable = true)
	private static void getMaxXpPerLevel(int level, @NotNull CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(canLevelUp(level) ? NEXT_LEVEL_XP_THRESHOLDS[level] * 3 : 0);
	}
}
