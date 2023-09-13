package net.luis.xsurvive.world.item;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author Luis-St
 */

public class ItemStackHelper {
	
	public static int getDurability(@NotNull ItemStack stack) {
		return stack.getMaxDamage() - stack.getDamageValue();
	}
	
	public static double getDurabilityPercent(@NotNull ItemStack stack) {
		return (double) getDurability(stack) / (double) stack.getMaxDamage();
	}
}
