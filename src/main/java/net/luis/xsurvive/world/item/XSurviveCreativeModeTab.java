package net.luis.xsurvive.world.item;

import java.util.function.Supplier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveCreativeModeTab extends CreativeModeTab {
	
	private final Supplier<? extends Item> iconSupplier;
	
	public XSurviveCreativeModeTab(String label, Supplier<? extends Item> iconSupplier) {
		super(label);
		this.iconSupplier = iconSupplier;
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(this.iconSupplier.get());
	}

}
