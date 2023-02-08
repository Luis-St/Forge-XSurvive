package net.luis.xsurvive.world.inventory.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public record ShulkerBoxTooltip(List<ItemStack> stacks) implements TooltipComponent {


}
