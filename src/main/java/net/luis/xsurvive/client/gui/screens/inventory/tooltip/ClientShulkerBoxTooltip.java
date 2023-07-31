package net.luis.xsurvive.client.gui.screens.inventory.tooltip;

import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class ClientShulkerBoxTooltip implements ClientTooltipComponent {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/bundle.png");
	
	private final List<ItemStack> stacks;
	
	public ClientShulkerBoxTooltip(@NotNull ShulkerBoxTooltip tooltip) {
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
	public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics graphics) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				int index = j + i * 9;
				graphics.blit(TEXTURE, x + j * 18, y + i * 18, 0, 0.0F, 0.0F, 18, 18, 128, 128);
				if (this.stacks.size() > index && index >= 0) {
					ItemStack itemStack = this.stacks.get(index);
					if (!itemStack.isEmpty()) {
						graphics.renderItem(itemStack, x + j * 18 + 1, y + i * 18 + 1, index);
						graphics.renderItemDecorations(font, itemStack, x + j * 18 + 1, y + i * 18 + 1);
					}
				}
			}
		}
	}
}
