package net.luis.xsurvive.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneItem extends Item implements IRuneColorProvider {
	
	private final boolean foil;
	private final int color;
	
	public RuneItem(Properties properties, boolean foil, int color) {
		super(properties);
		this.foil = foil;
		this.color = color;
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return this.foil;
	}
	
	@Override
	public int getRuneColor(ItemStack stack) {
		return this.color;
	}

}
