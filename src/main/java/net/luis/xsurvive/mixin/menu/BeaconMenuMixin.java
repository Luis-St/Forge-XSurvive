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

package net.luis.xsurvive.mixin.menu;

import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(BeaconMenu.class)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class BeaconMenuMixin {
	
	//region Mixin
	@Shadow private ContainerLevelAccess access;
	//endregion
	
	@Inject(method = "updateEffects", at = @At("TAIL"))
	public void updateEffects(Optional<Holder<MobEffect>> primary, Optional<Holder<MobEffect>> secondary, CallbackInfo callback) {
		if (this.access != ContainerLevelAccess.NULL) {
			this.access.execute((level, pos) -> LevelProvider.getServer(level).setBeaconEffects(pos, primary.orElseThrow(), secondary.orElseThrow()));
		}
	}
}
