package net.luis.xsurvive.world.item.enchantment;

import net.luis.xores.world.item.ElytraChestplateItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class VoidProtectionEnchantment extends Enchantment {

	public VoidProtectionEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
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
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment instanceof ProtectionEnchantment protectionEnchantment) {
			return protectionEnchantment.type == ProtectionEnchantment.Type.ALL;
		} else if (enchantment == Enchantments.THORNS) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean canEnchant(ItemStack stack) {
		if (stack.getItem() instanceof ElytraItem) {
			return true;
		} else if (stack.getItem() instanceof ElytraChestplateItem) {
			return true;
		}
		return super.canEnchant(stack);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isDiscoverable() {
		return false;
	}

}
