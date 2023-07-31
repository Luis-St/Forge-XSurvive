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
	
	@Shadow private Player player;
	
	private AnvilScreenMixin(AnvilMenu anvilMenu, Inventory inventory, Component component, ResourceLocation location) {
		super(anvilMenu, inventory, component, location);
	}
	
	@Inject(method = "renderLabels", at = @At("HEAD"), cancellable = true)
	protected void renderLabels(GuiGraphics graphics, int x, int y, CallbackInfo callback) {
		RenderSystem.disableBlend();
		super.renderLabels(graphics, x, y);
		int cost = this.menu.getCost();
		if (cost > 0) {
			int color = 8453920;
			Component component;
			if (!this.menu.getSlot(2).hasItem()) {
				component = null;
			} else {
				component = Component.translatable("container.repair.cost", cost);
				if (!this.menu.getSlot(2).mayPickup(this.player)) {
					color = 16736352;
				}
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
