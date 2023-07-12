package net.luis.xsurvive.world.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
@AutoRegisterCapability
public interface IGlintColor {
	
	static @NotNull IGlintColor simple(@NotNull DyeColor color) {
		return simple(color.getId());
	}
	
	static @NotNull IGlintColor simple(int glintColor) {
		return (stack) -> glintColor;
	}
	
	int getGlintColor(@NotNull ItemStack stack);
}
