package net.luis.xsurvive.common.extension;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.common.enchantment.EnchantedItem;
import net.luis.xsurvive.common.enchantment.GoldenEnchantmentInstance;
import net.luis.xsurvive.common.handler.EnchantmentHandler;
import net.luis.xsurvive.common.item.EnchantedGoldenBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public interface IEnchantment {
	
	private Enchantment self() {
		return (Enchantment) this;
	}
	
	boolean isAllowedOnGoldenBooks();
	
	default int getMinGoldenBookLevel() {
		return this.self().getMaxLevel() + 1;
	}
	
	default int getMaxGoldenBookLevel() {
		return this.getMinGoldenBookLevel();
	}
	
	default int getAnvilCost(int level) {
		return Math.max(0, level - this.self().getMaxLevel()) * 10 + 50;
	}
	
	default boolean isGolden(int level) {
		return this.getMaxGoldenBookLevel() >= level && level >= this.getMinGoldenBookLevel();
	}
	
	default int getUpgradeLevel() {
		return -1;
	}
	
	default boolean isUpgrade() {
		return this.isAllowedOnGoldenBooks() && this.getUpgradeLevel() > 0;
	}
	
	default GoldenEnchantmentInstance createGoldenInstance(int level) {
		if (this.getMinGoldenBookLevel() > level) {
			return new GoldenEnchantmentInstance(this.self(), this.getMinGoldenBookLevel());
		} else if (level > this.getMaxGoldenBookLevel()) {
			return new GoldenEnchantmentInstance(this.self(), this.getMaxGoldenBookLevel());
		}
		return new GoldenEnchantmentInstance(this.self(), level);
	}
	
	static EnchantedItem merge(ItemStack left, ItemStack right) {
		ItemStack result = left.copy();
		if (right.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
			Enchantment enchantment = goldenBook.getEnchantment(right);
			if (enchantment instanceof IEnchantment ench) {
				int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, result);
				GoldenEnchantmentInstance instance = ench.createGoldenInstance(level + 1);
				if (!EnchantmentHandler.hasEnchantment(enchantment, result)) {
					if (EnchantmentHandler.isEnchantmentCompatible(result, enchantment)) {
						EnchantmentHandler.addEnchantment(new EnchantmentInstance(enchantment, 1), result, false);
						return new EnchantedItem(result, 10);
					}
					return EnchantedItem.EMPTY;
				} else if (instance.isGolden() && instance.level > enchantment.getMaxLevel() && level >= enchantment.getMaxLevel()) {
					if (ench.getMaxGoldenBookLevel() > level) {
						EnchantmentHandler.replaceEnchantment(instance, result);
						return new EnchantedItem(result, ench.getAnvilCost(level));
					}
					return EnchantedItem.EMPTY;
				} else {
					EnchantmentHandler.increaseEnchantment(enchantment, result, false);
					return new EnchantedItem(result, 10);
				}
			}
			XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", enchantment.getRegistryName());
			return EnchantedItem.EMPTY;
		}
		XSurvive.LOGGER.error("Can not merge {} with {}, since the right Item must be a instance of EnchantedGoldenBookItem", left.getItem().getRegistryName(), right.getItem().getRegistryName());
		return EnchantedItem.EMPTY;
	}
	
	static EnchantedItem upgrade(ItemStack left, ItemStack right) {
		ItemStack result = left.copy();
		if (right.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
			Enchantment enchantment = goldenBook.getEnchantment(right);
			if (enchantment instanceof IEnchantment ench) {
				int level = EnchantmentHelper.getItemEnchantmentLevel(enchantment, result);
				if (ench.isUpgrade() && ench.getUpgradeLevel() > level) {
					EnchantmentHandler.increaseEnchantment(enchantment, result, false);
					return new EnchantedItem(result, 10);
				} else {
					return merge(left, right);
				}
			}
			XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", enchantment.getRegistryName());
			return EnchantedItem.EMPTY;
		}
		XSurvive.LOGGER.error("Can not upgrade {} with {}, since the right Item must be a instance of EnchantedGoldenBookItem", left.getItem().getRegistryName(), right.getItem().getRegistryName());
		return EnchantedItem.EMPTY;
	}
	
}
