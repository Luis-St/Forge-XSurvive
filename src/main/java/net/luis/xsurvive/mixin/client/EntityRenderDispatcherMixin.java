package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
	
	private static final Material FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/fire_0"));
	private static final Material FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/fire_1"));
	private static final Material SOUL_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_0"));
	private static final Material SOUL_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_1"));
	private static final Material MYSTIC_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(XSurvive.MOD_ID, "block/mystic_fire_0"));
	private static final Material MYSTIC_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(XSurvive.MOD_ID, "block/mystic_fire_1"));
	
	@Shadow public Camera camera;
	
	@Shadow
	private static void fireVertex(PoseStack.Pose pose, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v) {
		
	}
	
	@Inject(method = "renderFlame", at = @At("HEAD"), cancellable = true)
	private void renderFlame(PoseStack stack, MultiBufferSource bufferSource, Entity entity, CallbackInfo callback) {
		EntityFireType fireType = EntityProvider.get(entity).getFireType();
		TextureAtlasSprite fireTextureSprite0 = getFireTextureSprite0(entity, fireType);
		TextureAtlasSprite fireTextureSprite1 = getFireTextureSprite1(entity, fireType);
		stack.pushPose();
		float width = entity.getBbWidth() * 1.4F;
		stack.scale(width, width, width);
		float xOffset = 0.5F;
		float height = entity.getBbHeight() / width;
		float yOffset = 0.0F;
		stack.mulPose(Axis.YP.rotationDegrees(-this.camera.getYRot()));
		stack.translate(0.0, 0.0, -0.3F + (float) ((int) height) * 0.02F);
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
	
	private TextureAtlasSprite getFireTextureSprite0(Entity entity, EntityFireType entityFireType) {
		EntityFireType blockFireType = EntityFireType.byBlock(entity.getFeetBlockState().getBlock());
		if (blockFireType == EntityFireType.NONE || blockFireType == entityFireType) {
			return switch (entityFireType) {
				case SOUL_FIRE -> SOUL_FIRE_0.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_0.sprite();
				default -> FIRE_0.sprite();
			};
		} else {
			return switch (blockFireType) {
				case SOUL_FIRE -> SOUL_FIRE_0.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_0.sprite();
				default -> FIRE_0.sprite();
			};
		}
	}
	
	private TextureAtlasSprite getFireTextureSprite1(Entity entity, EntityFireType entityFireType) {
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
