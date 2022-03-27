package net.luis.xsurvive.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ExperienceEnchantment extends Enchantment {

	public ExperienceEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
	
	public int getMinCost(int level) {
		return 15 + (level - 1) * 9;
	}

	public int getMaxCost(int level) {
		return this.getMinCost(level) + 50;
	}
	
}
