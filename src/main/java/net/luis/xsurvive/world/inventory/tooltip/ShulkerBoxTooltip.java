package net.luis.xsurvive.world.inventory.tooltip;

import java.util.List;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Luis-st
 *
 */

public class ShulkerBoxTooltip implements TooltipComponent {
	
	private final List<ItemStack> stacks;
	
	public ShulkerBoxTooltip(List<ItemStack> stacks) {
		this.stacks = stacks;
	}
	
	public List<ItemStack> getStacks() {
		return this.stacks;
	}
	
}
