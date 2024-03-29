package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.luis.xsurvive.client.renderer.item.GlintColorHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
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
	
	@Inject(method = "render(Lnet/minecraft/world/entity/projectile/ThrownTrident;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
	private void render(@NotNull ThrownTrident trident, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int packedLight, CallbackInfo callback) {
		GlintColorHandler.setStack(trident.getPickupItemStackOrigin().copy());
	}
}
