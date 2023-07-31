package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class BlastingEnchantment extends Enchantment implements IEnchantment {
	
	public BlastingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
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
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		if (enchantment == Enchantments.SILK_TOUCH) {
			return false;
		} else if (enchantment == XSEnchantments.SMELTING.get()) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		return true;
	}
}
