package net.luis.xsurvive.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.luis.xsurvive.world.inventory.EnderChestMenu;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
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
	public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		this.renderTooltip(stack, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(@NotNull PoseStack stack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		blit(stack, i, j, 0, 0, this.imageWidth, 125);
		blit(stack, i, j + 125, 0, 126, this.imageWidth, 96);
	}
}
