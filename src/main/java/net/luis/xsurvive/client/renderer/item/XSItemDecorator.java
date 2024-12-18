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

import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Luis-St
 */

public class XSItemDecorator implements IItemDecorator {
	
	private final Minecraft minecraft;
	
	public XSItemDecorator(@NotNull Minecraft minecraft) {
		this.minecraft = minecraft;
	}
	
	@Override
	public boolean render(@NotNull GuiGraphics graphics, @NotNull Font font, @NotNull ItemStack stack, int xOffset, int yOffset) {
		LocalPlayer player = this.minecraft.player;
		if (player != null) {
			if (!player.getCooldowns().isOnCooldown(stack)) {
				double endAspectCooldown = PlayerProvider.getLocal(player).getEndAspectPercent();
				if (endAspectCooldown > 0 && stack.getEnchantments().getLevel(XSEnchantments.ASPECT_OF_THE_END.getOrThrow(player)) > 0) {
					int i = yOffset + Mth.floor(16.0F * (1.0F - endAspectCooldown));
					int j = i + Mth.ceil(16.0F * endAspectCooldown);
					graphics.fill(RenderType.gui(), xOffset, i, xOffset + 16, j, 200, Integer.MAX_VALUE);
					return true;
				}
			}
		}
		return false;
	}
}
