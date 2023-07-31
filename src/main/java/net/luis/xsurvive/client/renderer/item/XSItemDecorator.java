package net.luis.xsurvive.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSItemDecorator implements IItemDecorator {
	
	private final Minecraft minecraft;
	
	public XSItemDecorator(Minecraft minecraft) {
		this.minecraft = minecraft;
	}
	
	@Override
	public boolean render(@NotNull GuiGraphics graphics, @NotNull Font font, @NotNull ItemStack stack, int xOffset, int yOffset) {
		LocalPlayer player = this.minecraft.player;
		if (player != null) {
			if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
				double endAspectCooldown = PlayerProvider.getLocal(player).getEndAspectPercent();
				if (endAspectCooldown > 0 && stack.getEnchantmentLevel(XSEnchantments.ASPECT_OF_THE_END.get()) > 0) {
					RenderSystem.disableDepthTest();
					RenderSystem.enableBlend();
					RenderSystem.defaultBlendFunc();
					BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
					this.fillRect(bufferBuilder, xOffset, yOffset + Mth.floor(16.0 * (1.0 - endAspectCooldown)), Mth.ceil(16.0 * endAspectCooldown));
					RenderSystem.disableBlend();
					RenderSystem.enableDepthTest();
					return true;
				}
			}
		}
		return false;
	}
	
	private void fillRect(@NotNull BufferBuilder bufferBuilder, int xStart, int yStart, int yEnd) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferBuilder.vertex(xStart, yStart, 0.0).color(255, 255, 255, 127).endVertex();
		bufferBuilder.vertex(xStart, yStart + yEnd, 0.0).color(255, 255, 255, 127).endVertex();
		bufferBuilder.vertex(xStart + 16, yStart + yEnd, 0.0).color(255, 255, 255, 127).endVertex();
		bufferBuilder.vertex(xStart + 16, yStart, 0.0).color(255, 255, 255, 127).endVertex();
		BufferUploader.drawWithShader(bufferBuilder.end());
	}
}
