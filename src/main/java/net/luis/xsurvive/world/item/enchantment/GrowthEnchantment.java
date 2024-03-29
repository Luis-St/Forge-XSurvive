package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 *
 * @author Luis-St
 *
 */

public class GrowthEnchantment extends Enchantment implements IEnchantment {
	
	public GrowthEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	@Override
	public int getMinCost(int level) {
		return 1 + (level - 1) * 9;
	}
	
	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 50;
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		return true;
	}
}
