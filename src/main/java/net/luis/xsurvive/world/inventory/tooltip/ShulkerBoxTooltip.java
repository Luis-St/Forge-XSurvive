package net.luis.xsurvive.world.inventory.tooltip;

import java.util.List;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

/**
 *
 * @author Luis-st
 *
 */

public record ShulkerBoxTooltip(List<ItemStack> stacks) implements TooltipComponent {


}
