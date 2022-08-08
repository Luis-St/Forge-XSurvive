package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {
	
	@Shadow
	@Final
	public ProtectionEnchantment.Type type;
	
	private ProtectionEnchantmentMixin(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public boolean canEnchant(ItemStack stack) {
		if (stack.getItem() instanceof ElytraItem) {
			return this.type != ProtectionEnchantment.Type.FALL;
		}
		return super.canEnchant(stack);
	}

}
