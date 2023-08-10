package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.luis.xsurvive.client.EntityFireTypeHelper;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
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
	private static void renderFire(@NotNull Minecraft minecraft, PoseStack stack, CallbackInfo callback) {
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		RenderSystem.depthFunc(519);
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		Player player = minecraft.player;
		if (player != null) {
			TextureAtlasSprite textureSprite = EntityFireTypeHelper.getFireTextureSprite1(player, EntityProvider.get(player).getFireType());
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
				bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
				bufferBuilder.vertex(matrix, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(outerU, innerV).endVertex();
				bufferBuilder.vertex(matrix, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(innerU, innerV).endVertex();
				bufferBuilder.vertex(matrix, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(innerU, outerV).endVertex();
				bufferBuilder.vertex(matrix, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(outerU, outerV).endVertex();
				BufferUploader.drawWithShader(bufferBuilder.end());
				stack.popPose();
			}
		}
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.depthFunc(515);
		callback.cancel();
	}
}
