package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class HarvestingEnchantment extends Enchantment {

	public HarvestingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	@Override
	public int getMinCost(int level) {
		return 1 + 10 * (level - 1);
	}
	
	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 50;
	}
	
	@Override
	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof AxeItem;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof AxeItem;
	}

}
