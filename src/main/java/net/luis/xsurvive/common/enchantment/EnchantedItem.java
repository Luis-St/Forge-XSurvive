package net.luis.xsurvive.common.enchantment;

import net.minecraft.world.item.ItemStack;

public record EnchantedItem(ItemStack stack, int cost) {
	
	public static final EnchantedItem EMPTY = new EnchantedItem(ItemStack.EMPTY, 0);
	
}
