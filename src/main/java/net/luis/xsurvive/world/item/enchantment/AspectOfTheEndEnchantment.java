package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class AspectOfTheEndEnchantment extends Enchantment implements IEnchantment {
	
	public AspectOfTheEndEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
	
	@Override
	public int getMinCost(int cost) {
		return 20;
	}
	
	@Override
	public int getMaxCost(int cost) {
		return 50;
	}
	
	@Override
	public boolean canEnchant(@NotNull ItemStack stack) {
		return stack.getItem() instanceof SwordItem;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isDiscoverable() {
		return false;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		return true;
	}
	
	@Override
	public int getMinUpgradeLevel() {
		return this.getMinLevel();
	}
	
	@Override
	public int getMaxUpgradeLevel() {
		return this.getMaxLevel();
	}
}
