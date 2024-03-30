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

package net.luis.xsurvive.client.renderer.item;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.renderer.XSurviveRenderType;
import net.luis.xsurvive.world.item.IGlintColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Luis-St
 *
 */

public class GlintColorHandler {
	
	private static final ThreadLocal<ItemStack> STACK = new ThreadLocal<>();
	
	public static ItemStack getStack() {
		return STACK.get();
	}
	
	public static void setStack(ItemStack stack) {
		STACK.set(stack);
	}
	
	private static int getColor() {
		ItemStack stack = getStack();
		if (stack != null && !stack.isEmpty()) {
			LazyOptional<IGlintColor> optional = stack.getCapability(XSCapabilities.GLINT_COLOR, null);
			if (optional.isPresent()) {
				return optional.orElseThrow(NullPointerException::new).getGlintColor(stack);
			} else if (stack.getItem() instanceof IGlintColor glintColor) {
				return glintColor.getGlintColor(stack);
			} else if (stack.hasTag() && stack.getOrCreateTag().contains(XSurvive.MOD_NAME)) {
				CompoundTag tag = stack.getOrCreateTag().getCompound(XSurvive.MOD_NAME);
				if (tag.contains(XSurvive.MOD_NAME + "GlintColor")) {
					return tag.getInt(XSurvive.MOD_NAME + "GlintColor");
				} else if (tag.contains(XSurvive.MOD_NAME + "ItemColor")) {
					return tag.getInt(XSurvive.MOD_NAME + "ItemColor");
				}
			}
		}
		return -1;
	}
	
	public static @NotNull RenderType getGlint() {
		return renderType(XSurviveRenderType.glint, RenderType::glint);
	}
	
	public static @NotNull RenderType getGlintTranslucent() {
		return renderType(XSurviveRenderType.glintTranslucent, RenderType::glintTranslucent);
	}
	
	public static @NotNull RenderType getEntityGlint() {
		return renderType(XSurviveRenderType.entityGlint, RenderType::entityGlint);
	}
	
	public static @NotNull RenderType getGlintDirect() {
		return renderType(XSurviveRenderType.glintDirect, RenderType::glintDirect);
	}
	
	public static @NotNull RenderType getEntityGlintDirect() {
		return renderType(XSurviveRenderType.entityGlintDirect, RenderType::entityGlintDirect);
	}
	
	public static @NotNull RenderType getArmorGlint() {
		return renderType(XSurviveRenderType.armorGlint, RenderType::armorGlint);
	}
	
	public static @NotNull RenderType getArmorEntityGlint() {
		return renderType(XSurviveRenderType.armorEntityGlint, RenderType::armorEntityGlint);
	}
	
	private static @NotNull RenderType renderType(@NotNull List<RenderType> renderTypes, @NotNull Supplier<RenderType> vanillaRenderType) {
		int color = getColor();
		if (17 >= color && color >= 0) {
			return renderTypes.get(color);
		}
		return vanillaRenderType.get();
	}
}
