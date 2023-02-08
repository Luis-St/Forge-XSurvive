package net.luis.xsurvive.world.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
@AutoRegisterCapability
public interface IGlintColor {
	
	public static IGlintColor simple(DyeColor color) {
		return simple(color.getId());
	}
	
	public static IGlintColor simple(int glintColor) {
		return (stack) -> glintColor;
	}
	
	int getGlintColor(ItemStack stack);
	
}
