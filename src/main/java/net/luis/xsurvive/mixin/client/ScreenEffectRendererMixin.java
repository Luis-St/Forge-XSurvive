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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.luis.xsurvive.client.EntityFireTypeHelper;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ScreenEffectRenderer.class)
public abstract class ScreenEffectRendererMixin {
	
	@Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
	private static void renderFire(@NotNull Minecraft minecraft, @NotNull PoseStack stack, @NotNull CallbackInfo callback) {
		RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
		RenderSystem.depthFunc(519);
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		Player player = minecraft.player;
		if (player != null) {
			EntityProvider.getSafe(player).ifPresent(handler -> {
				TextureAtlasSprite textureSprite = EntityFireTypeHelper.getFireTextureSprite1(player, handler.getFireType());
				RenderSystem.setShaderTexture(0, textureSprite.atlasLocation());
				float textureSpriteU = (textureSprite.getU0() + textureSprite.getU1()) / 2.0F;
				float textureSpriteV = (textureSprite.getV0() + textureSprite.getV1()) / 2.0F;
				float innerU = Mth.lerp(textureSprite.uvShrinkRatio(), textureSprite.getU0(), textureSpriteU);
				float outerU = Mth.lerp(textureSprite.uvShrinkRatio(), textureSprite.getU1(), textureSpriteU);
				float innerV = Mth.lerp(textureSprite.uvShrinkRatio(), textureSprite.getV1(), textureSpriteV);
				float outerV = Mth.lerp(textureSprite.uvShrinkRatio(), textureSprite.getV0(), textureSpriteV);
				for (int i = 0; i < 2; ++i) {
					stack.pushPose();
					stack.translate(-(i * 2 - 1) * 0.24, -0.3, 0.0);
					stack.mulPose(Axis.YP.rotationDegrees((i * 2 - 1) * 10.0F));
					Matrix4f matrix = stack.last().pose();
					BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
					bufferBuilder.addVertex(matrix, -0.5F, -0.5F, -0.5F).setUv(outerU, innerV).setColor(1.0F, 1.0F, 1.0F, 0.9F);
					bufferBuilder.addVertex(matrix, 0.5F, -0.5F, -0.5F).setUv(innerU, innerV).setColor(1.0F, 1.0F, 1.0F, 0.9F);
					bufferBuilder.addVertex(matrix, 0.5F, 0.5F, -0.5F).setUv(innerU, outerV).setColor(1.0F, 1.0F, 1.0F, 0.9F);
					bufferBuilder.addVertex(matrix, -0.5F, 0.5F, -0.5F).setUv(outerU, outerV).setColor(1.0F, 1.0F, 1.0F, 0.9F);
					BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
					stack.popPose();
				}
			});
		}
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.depthFunc(515);
		callback.cancel();
	}
}
