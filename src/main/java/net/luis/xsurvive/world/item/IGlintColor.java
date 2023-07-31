package net.luis.xsurvive.world.item;

import net.luis.xsurvive.XSurvive;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
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
	
	static @NotNull CompoundTag createGlintTag(@NotNull CompoundTag tag, int color) {
		if (tag.contains(XSurvive.MOD_NAME)) {
			CompoundTag modTag = tag.getCompound(XSurvive.MOD_NAME);
			tag.remove(XSurvive.MOD_NAME);
			modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
			tag.put(XSurvive.MOD_NAME, modTag);
		} else {
			CompoundTag modTag = new CompoundTag();
			modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
			tag.put(XSurvive.MOD_NAME, modTag);
		}
		return tag;
	}
	
	int getGlintColor(@NotNull ItemStack stack);
}
