package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class FrostAspectEnchantment extends FireAspectEnchantment {
	
	public FrostAspectEnchantment(Rarity rarity, EquipmentSlot... slots) {
		super(rarity, slots);
	}
	
	@Override
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		if (enchantment instanceof FireAspectEnchantment) {
			return enchantment != Enchantments.FIRE_ASPECT;
		}
		return super.checkCompatibility(enchantment);
	}
}
