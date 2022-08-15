package net.luis.xsurvive.client.renderer.item;

import java.util.List;
import java.util.function.Supplier;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.renderer.XSurviveRenderType;
import net.luis.xsurvive.world.item.IGlintColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

/**
 * 
 * @author Luis-st
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
			} else if (stack.hasTag() && stack.getTag().contains(XSurvive.MOD_NAME)) {
				CompoundTag tag = stack.getTag().getCompound(XSurvive.MOD_NAME);
				if (tag.contains(XSurvive.MOD_NAME + "GlintColor")) {
					return tag.getInt(XSurvive.MOD_NAME + "GlintColor");
				} else if (tag.contains(XSurvive.MOD_NAME + "ItemColor")) {
					return tag.getInt(XSurvive.MOD_NAME + "ItemColor");
				}
			}
		}
		return -1;
	}
	
	public static RenderType getGlint() {
		return renderType(XSurviveRenderType.glint, RenderType::glint);
	}

	public static RenderType getGlintTranslucent() {
		return renderType(XSurviveRenderType.glintTranslucent, RenderType::glintTranslucent);
	}

	public static RenderType getEntityGlint() {
		return renderType(XSurviveRenderType.entityGlint, RenderType::entityGlint);
	}

	public static RenderType getGlintDirect() {
		return renderType(XSurviveRenderType.glintDirect, RenderType::glintDirect);
	}

	public static RenderType getEntityGlintDirect() {
		return renderType(XSurviveRenderType.entityGlintDirect, RenderType::entityGlintDirect);
	}

	public static RenderType getArmorGlint() {
		return renderType(XSurviveRenderType.armorGlint, RenderType::armorGlint);
	}

	public static RenderType getArmorEntityGlint() {
		return renderType(XSurviveRenderType.armorEntityGlint, RenderType::armorEntityGlint);
	}
	
	private static RenderType renderType(List<RenderType> renderTypes, Supplier<RenderType> vanillaRenderType) {
		int color = getColor();
		if (17 >= color && color >= 0) {
			return renderTypes.get(color) ;
		}
		return vanillaRenderType.get();
	}
	
}
