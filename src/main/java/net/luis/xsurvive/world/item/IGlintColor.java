package net.luis.xsurvive.world.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Luis-st
 *
 */

@FunctionalInterface
public interface IGlintColor {
	
	public static IGlintColor simple(DyeColor color) {
		return simple(color.getId());
	}
	
	public static IGlintColor simple(int glintColor) {
		return (stack) -> {
			return glintColor;
		};
	}
	
	int getGlintColor(ItemStack stack);
	
}
