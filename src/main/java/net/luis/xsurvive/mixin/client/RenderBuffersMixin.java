package net.luis.xsurvive.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.BufferBuilder;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.luis.xsurvive.client.renderer.XSurviveRenderType;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(RenderBuffers.class)
public abstract class RenderBuffersMixin {
	
	@Inject(method = "put", at = @At("HEAD"))
	private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> map, RenderType renderType, CallbackInfo callback) {
		XSurviveRenderType.addGlintTypes(map);
	}
	
}
