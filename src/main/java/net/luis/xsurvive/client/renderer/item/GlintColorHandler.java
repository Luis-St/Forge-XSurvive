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

import net.luis.xsurvive.client.renderer.XSurviveRenderType;
import net.luis.xsurvive.core.components.XSDataComponents;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Luis-St
 *
 */

public class GlintColorHandler { // ToDo: Remove comment after testing
	
	private static final ThreadLocal<ItemStack> STACK = new ThreadLocal<>();
	
	public static @Nullable ItemStack getStack() {
		return STACK.get();
	}
	
	public static void setStack(@NotNull ItemStack stack) {
		STACK.set(stack);
	}
	
	private static int getColor() {
		ItemStack stack = getStack();
		if (stack != null && !stack.isEmpty()) {
			return stack.getOrDefault(XSDataComponents.GLINT_COLOR.get(), -1);
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
	
	//public static @NotNull RenderType getGlintDirect() {
	//	return renderType(XSurviveRenderType.glintDirect, RenderType::glintDirect);
	//}
	
	public static @NotNull RenderType getEntityGlintDirect() {
		return renderType(XSurviveRenderType.entityGlintDirect, RenderType::entityGlintDirect);
	}
	
	//public static @NotNull RenderType getArmorGlint() {
	//	return renderType(XSurviveRenderType.armorGlint, RenderType::armorGlint);
	//}
	
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
