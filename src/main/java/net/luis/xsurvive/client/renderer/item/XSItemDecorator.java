package net.luis.xsurvive.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
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
		bufferBuilder.vertex(xStart, yStart, 0.0).color(red, green, blue, alpha).endVertex();
		bufferBuilder.vertex(xStart, yStart + yEnd, 0.0).color(red, green, blue, alpha).endVertex();
		bufferBuilder.vertex(xStart + xEnd, yStart + yEnd, 0.0).color(red, green, blue, alpha).endVertex();
		bufferBuilder.vertex(xStart + xEnd, yStart, 0.0).color(red, green, blue, alpha).endVertex();
		BufferUploader.drawWithShader(bufferBuilder.end());
	}
	
}
