package net.luis.xsurvive.client.gui.screens.inventory.tooltip;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 
 * @author Luis-st
 *
 */

public class ClientShulkerBoxTooltip implements ClientTooltipComponent {
	
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
	
	private final List<ItemStack> stacks;
	
	public ClientShulkerBoxTooltip(ShulkerBoxTooltip tooltip) {
		this.stacks = tooltip.stacks();
	}
	
	@Override
	public int getHeight() {
		return 59;
	}

	@Override
	public int getWidth(@NotNull Font font) {
		return 162;
	}
	
	@Override
	public void renderImage(@NotNull Font font, int x, int y, @NotNull PoseStack stack, @NotNull ItemRenderer itemRenderer, int blitOffset) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				int index = j + i * 9;
				this.blit(stack, x + j * 18, y + i * 18, blitOffset);
				if (this.stacks.size() > index && index >= 0) {
					ItemStack itemStack = this.stacks.get(index);
					if (!itemStack.isEmpty()) {
						itemRenderer.renderAndDecorateItem(itemStack, x + j * 18 + 1, y + i * 18 + 1, index);
						itemRenderer.renderGuiItemDecorations(font, itemStack, x + j * 18 + 1, y + i * 18 + 1);
					}
				}
			}
		}
	}
	
	private void blit(PoseStack stack, int x, int y, int blitOffset) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
		GuiComponent.blit(stack, x, y, blitOffset, 0.0F, 0.0F, 18, 18, 128, 128);
	}
	
}
