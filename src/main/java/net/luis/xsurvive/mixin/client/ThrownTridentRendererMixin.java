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

package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.luis.xsurvive.client.renderer.item.GlintColorHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.world.entity.projectile.ThrownTrident;
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

@Mixin(ThrownTridentRenderer.class)
public abstract class ThrownTridentRendererMixin {
	
	@Inject(method = "extractRenderState", at = @At("HEAD"))
	private void extractRenderState(@NotNull ThrownTrident trident, @NotNull ThrownTridentRenderState tridentRenderState, float partialTicks, @NotNull CallbackInfo callback) {
		GlintColorHandler.setStack(trident.getPickupItemStackOrigin().copy());
	}
}
