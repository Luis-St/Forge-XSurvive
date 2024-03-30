/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
