package net.luis.xsurvive.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Luis-st
 *
 */

public class GlintColorItem extends Item implements IGlintColor {
	
	private final IGlintColor glintColor;
	
	public GlintColorItem(Properties properties, int color) {
		this(properties, IGlintColor.simple(color));
	}
	
	public GlintColorItem(Properties properties, IGlintColor glintColor) {
		super(properties);
		this.glintColor = glintColor;
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public int getGlintColor(ItemStack stack) {
		return this.glintColor.getGlintColor(stack);
	}
	
}
