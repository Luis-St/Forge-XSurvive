package net.luis.xsurvive.mixin.enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentImpalerEnchantment;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(TridentImpalerEnchantment.class)
public abstract class TridentImpalerEnchantmentMixin extends Enchantment {

	private TridentImpalerEnchantmentMixin(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment instanceof DamageEnchantment) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		if (stack.getItem() instanceof SwordItem) {
			return true;
		} else if (stack.getItem() instanceof AxeItem) {
			return true;
		} else if (stack.getItem() instanceof TridentItem) {
			return true;
		}
		return super.canApplyAtEnchantingTable(stack);
	}
	
	@Inject(method = "getDamageBonus", at = @At("HEAD"), cancellable = true)
	public void getDamageBonus(int level, MobType mobType, CallbackInfoReturnable<Float> callback) {
		callback.setReturnValue(0.0F);
	}
	
}
