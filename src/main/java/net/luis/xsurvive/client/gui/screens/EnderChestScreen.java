package net.luis.xsurvive.client.gui.screens;

import net.luis.xsurvive.world.inventory.EnderChestMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class EnderChestScreen extends AbstractContainerScreen<EnderChestMenu> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
	
	public EnderChestScreen(EnderChestMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		this.imageHeight = 222;
		this.inventoryLabelY = this.imageHeight - 94;
	}
	
	@Override
	public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(graphics, mouseX, mouseY, partialTicks);
		super.render(graphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		graphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, 125);
		graphics.blit(TEXTURE, i, j + 125, 0, 126, this.imageWidth, 96);
	}
}
