package net.luis.xsurvive.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class EnderSlayerEnchantment extends DamageEnchantment {
	
	public EnderSlayerEnchantment(Rarity rarity, EquipmentSlot... slots) {
		super(rarity, -1, slots);
	}
	
	@Override
	public int getMinCost(int level) {
		return 5 + (level - 1) * 5;
	}
	
	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 20;
	}
	
	@Override
	public float getDamageBonus(int level, @NotNull MobType mobType) {
		return 0.0F;
	}
}
