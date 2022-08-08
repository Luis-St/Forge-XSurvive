package net.luis.xsurvive.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.luis.xsurvive.client.renderer.item.RuneColorHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.world.entity.projectile.ThrownTrident;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(ThrownTridentRenderer.class)
public abstract class ThrownTridentRendererMixin {
	
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
	private void render(ThrownTrident trident, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int packedLight, CallbackInfo callback) {
		RuneColorHandler.setStack(trident.tridentItem);
	}
	
}
