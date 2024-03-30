/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
