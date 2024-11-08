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

package net.luis.xsurvive.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {
	
	//region Mixin
	@Shadow private Player player;
	
	private AnvilScreenMixin(@NotNull AnvilMenu anvilMenu, @NotNull Inventory inventory, @NotNull Component component, @NotNull ResourceLocation location) {
		super(anvilMenu, inventory, component, location);
	}
	//endregion
	
	@Inject(method = "renderLabels", at = @At("HEAD"), cancellable = true)
	protected void renderLabels(@NotNull GuiGraphics graphics, int x, int y, @NotNull CallbackInfo callback) {
		RenderSystem.disableBlend();
		super.renderLabels(graphics, x, y);
		int cost = this.menu.getCost();
		if (cost > 0) {
			int color = 8453920;
			@Nullable Component component;
			if (this.menu.getSlot(2).hasItem()) {
				component = Component.translatable("container.repair.cost", cost);
				if (!this.menu.getSlot(2).mayPickup(this.player)) {
					color = 16736352;
				}
			} else {
				component = null;
			}
			if (component != null) {
				int width = this.imageWidth - 8 - this.font.width(component) - 2;
				graphics.fill(width - 2, 67, this.imageWidth - 8, 79, 1325400064);
				graphics.drawString(this.font, component, width, 69, color);
			}
		}
		callback.cancel();
	}
}
