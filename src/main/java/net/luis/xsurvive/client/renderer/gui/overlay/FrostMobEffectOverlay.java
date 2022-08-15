package net.luis.xsurvive.client.renderer.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

/**
 * 
 * @author Luis-st
 *
 */

public class FrostMobEffectOverlay implements IGuiOverlay {
	
	private static final ResourceLocation FROST_EFFECT = new ResourceLocation("textures/misc/powder_snow_outline.png");
	
	private final Minecraft minecraft;
	
	public FrostMobEffectOverlay(Minecraft minecraft) {
		this.minecraft = minecraft;
	}
	
	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
		Player player = this.minecraft.player;
		if (!this.minecraft.options.hideGui && player.hasEffect(XSMobEffects.FROST.get()) && 0 >= player.getPercentFrozen()) {
			gui.setupOverlayRenderState(true, false);
			this.renderFrostMobEffectOverlay(width, height, PlayerProvider.getLocal(player).getFrostPercent());
		}
	}
	
	private void renderFrostMobEffectOverlay(int width, int height, float frostPercent) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F >= frostPercent && frostPercent >= 0.0F ? frostPercent : 0.0F);
		RenderSystem.setShaderTexture(0, FROST_EFFECT);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
		bufferbuilder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
		bufferbuilder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
		bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
		tesselator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
