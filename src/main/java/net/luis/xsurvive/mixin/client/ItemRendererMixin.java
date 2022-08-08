package net.luis.xsurvive.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.luis.xsurvive.client.renderer.item.RuneColorHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	
	@Inject(method = "render", at = @At("HEAD"))
	public void render(ItemStack stack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack pose, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo callback) {
		RuneColorHandler.setStack(stack);
	}

	@Redirect(method = "getArmorFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorGlint()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getArmorGlint() {
		return RuneColorHandler.getArmorGlint();
	}

	@Redirect(method = "getArmorFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorEntityGlint()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getArmorEntityGlint() {
		return RuneColorHandler.getArmorEntityGlint();
	}
	
	@Redirect(method = "getFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintTranslucent()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getGlintTranslucent() {
		return RuneColorHandler.getGlintTranslucent();
	}	

	@Redirect(method = "getFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glint()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getGlint() {
		return RuneColorHandler.getGlint();
	}	

	@Redirect(method = "getFoilBuffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlint()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getEntityGlint() {
		return RuneColorHandler.getEntityGlint();
	}
	
	@Redirect(method = "getFoilBufferDirect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glintDirect()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getGlintDirect() {
		return RuneColorHandler.getGlintDirect();
	}

	@Redirect(method = "getFoilBufferDirect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entityGlintDirect()Lnet/minecraft/client/renderer/RenderType;"))
	private static RenderType getEntityGlintDirect() {
		return RuneColorHandler.getEntityGlintDirect();
	}
	
}
