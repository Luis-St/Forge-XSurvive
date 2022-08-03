package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class SmeltingEnchantment extends Enchantment {

	public SmeltingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMinCost(int level) {
		return 20;
	}
	
	@Override
	public int getMaxCost(int level) {
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment == Enchantments.SILK_TOUCH) {
			return false;
		} else if (enchantment == Enchantments.BLOCK_FORTUNE) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}

}
