package net.luis.xsurvive.mixin.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {
	
	@Shadow public ProtectionEnchantment.Type type;
	
	private ProtectionEnchantmentMixin(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public boolean canEnchant(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof ElytraItem) {
			return this.type != ProtectionEnchantment.Type.FALL;
		}
		return super.canEnchant(stack);
	}
}
