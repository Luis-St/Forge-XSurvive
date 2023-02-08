package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ScreenEffectRenderer.class)
public abstract class ScreenEffectRendererMixin {
	
	private static final Material FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/fire_1"));
	private static final Material SOUL_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_1"));
	private static final Material MYSTIC_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(XSurvive.MOD_ID, "block/mystic_fire_1"));
	
	@Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
	private static void renderFire(Minecraft minecraft, PoseStack stack, CallbackInfo callback) {
		BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		RenderSystem.depthFunc(519);
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableTexture();
		TextureAtlasSprite textureSprite = getFireTextureSprite1(Objects.requireNonNull(minecraft.player), EntityProvider.get(minecraft.player).getFireType());
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
		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.depthFunc(515);
		callback.cancel();
	}
	
	private static TextureAtlasSprite getFireTextureSprite1(Entity entity, EntityFireType entityFireType) {
		EntityFireType blockFireType = EntityFireType.byBlock(entity.getFeetBlockState().getBlock());
		if (blockFireType == EntityFireType.NONE || blockFireType == entityFireType) {
			return switch (entityFireType) {
				case SOUL_FIRE -> SOUL_FIRE_1.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_1.sprite();
				default -> FIRE_1.sprite();
			};
		} else {
			return switch (blockFireType) {
				case SOUL_FIRE -> SOUL_FIRE_1.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_1.sprite();
				default -> FIRE_1.sprite();
			};
		}
	}
	
}
