package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

public class GoldenEnchantmentInstance extends EnchantmentInstance {
	
	public GoldenEnchantmentInstance(Enchantment enchantment, int level) {
		super(enchantment, level);
	}
	
	public boolean isGolden() {
		if (this.enchantment instanceof IEnchantment ench) {
			return ench.isAllowedOnGoldenBooks() && ench.isGoldenLevel(this.level);
		}
		XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(this.enchantment));
		return false;
	}
}
