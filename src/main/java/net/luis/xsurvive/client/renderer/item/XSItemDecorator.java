package net.luis.xsurvive.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

/**
 *
 * @author Luis-st
 *
 */

public class XSItemDecorator implements IItemDecorator {
	
	private final Minecraft minecraft;
	
	public XSItemDecorator(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	@Override
	public boolean render(Font font, ItemStack stack, int xOffset, int yOffset, float blitOffset) {
		LocalPlayer player = this.minecraft.player;
		if (player != null) {
			if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
				double endAspectCooldown = PlayerProvider.getLocal(player).getEndAspectPercent();
				if (endAspectCooldown > 0 && stack.getEnchantmentLevel(XSEnchantments.ASPECT_OF_THE_END.get()) > 0) {
					RenderSystem.disableDepthTest();
					RenderSystem.disableTexture();
					RenderSystem.enableBlend();
					RenderSystem.defaultBlendFunc();
					BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
					this.fillRect(bufferBuilder, xOffset, yOffset + Mth.floor(16.0 * (1.0 - endAspectCooldown)), 16, Mth.ceil(16.0 * endAspectCooldown), 255, 255, 255, 127);
					RenderSystem.enableTexture();
					RenderSystem.enableDepthTest();
					return true;
				}
			}
		}
		return false;
	}
	
	private void fillRect(BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int red, int green, int blue, int alpha) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferBuilder.vertex((double) (xStart + 0), (double) (yStart + 0), 0.0).color(red, green, blue, alpha).endVertex();
		bufferBuilder.vertex((double) (xStart + 0), (double) (yStart + yEnd), 0.0).color(red, green, blue, alpha).endVertex();
		bufferBuilder.vertex((double) (xStart + xEnd), (double) (yStart + yEnd), 0.0).color(red, green, blue, alpha).endVertex();
		bufferBuilder.vertex((double) (xStart + xEnd), (double) (yStart + 0), 0.0).color(red, green, blue, alpha).endVertex();
		BufferUploader.drawWithShader(bufferBuilder.end());
	}
	
}
