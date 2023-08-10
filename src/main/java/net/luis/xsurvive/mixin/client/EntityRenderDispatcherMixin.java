package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.luis.xsurvive.client.EntityFireTypeHelper;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
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

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
	
	//region Mixin
	@Shadow public Camera camera;
	
	@Shadow
	private static void fireVertex(PoseStack.Pose pose, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v) {
		
	}
	//endregion
	
	@Inject(method = "renderFlame", at = @At("HEAD"), cancellable = true)
	private void renderFlame(@NotNull PoseStack stack, @NotNull MultiBufferSource bufferSource, Entity entity, CallbackInfo callback) {
		EntityFireType fireType = EntityProvider.get(entity).getFireType();
		TextureAtlasSprite fireTextureSprite0 = EntityFireTypeHelper.getFireTextureSprite0(entity, fireType);
		TextureAtlasSprite fireTextureSprite1 = EntityFireTypeHelper.getFireTextureSprite1(entity, fireType);
		stack.pushPose();
		float width = entity.getBbWidth() * 1.4F;
		stack.scale(width, width, width);
		float xOffset = 0.5F;
		float height = entity.getBbHeight() / width;
		float yOffset = 0.0F;
		stack.mulPose(Axis.YP.rotationDegrees(-this.camera.getYRot()));
		stack.translate(0.0, 0.0, -0.3F + ((int) height) * 0.02F);
		float zOffset = 0.0F;
		int index = 0;
		VertexConsumer vertexConsumer = bufferSource.getBuffer(Sheets.cutoutBlockSheet());
		for (PoseStack.Pose pose = stack.last(); height > 0.0F; ++index) {
			TextureAtlasSprite textureSprite = index % 2 == 0 ? fireTextureSprite0 : fireTextureSprite1;
			float u0 = textureSprite.getU0();
			float v0 = textureSprite.getV0();
			float u1 = textureSprite.getU1();
			float v1 = textureSprite.getV1();
			if (index / 2 % 2 == 0) {
				float f10 = u1;
				u1 = u0;
				u0 = f10;
			}
			fireVertex(pose, vertexConsumer, xOffset - 0.0F, 0.0F - yOffset, zOffset, u1, v1);
			fireVertex(pose, vertexConsumer, -xOffset - 0.0F, 0.0F - yOffset, zOffset, u0, v1);
			fireVertex(pose, vertexConsumer, -xOffset - 0.0F, 1.4F - yOffset, zOffset, u0, v0);
			fireVertex(pose, vertexConsumer, xOffset - 0.0F, 1.4F - yOffset, zOffset, u1, v0);
			height -= 0.45F;
			yOffset -= 0.45F;
			xOffset *= 0.9F;
			zOffset += 0.03F;
		}
		stack.popPose();
		callback.cancel();
	}
}
