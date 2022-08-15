package net.luis.xsurvive.mixin.enchantment;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin implements IEnchantment {
	
	@Shadow
	public abstract int getMaxLevel();
	
	@Shadow
	public abstract String getDescriptionId();
	
	@Shadow
	public abstract boolean isCurse();
	
	@Shadow
	protected abstract boolean checkCompatibility(Enchantment enchantment);
	
	@Inject(method = "isCompatibleWith", at = @At("HEAD"), cancellable = true)
	public void isCompatibleWith(Enchantment enchantment, CallbackInfoReturnable<Boolean> callback) {
		Enchantment ench = (Enchantment) (Object) this;
		if ((ench == Enchantments.INFINITY_ARROWS && enchantment == Enchantments.MENDING) || (ench == Enchantments.MENDING && enchantment == Enchantments.INFINITY_ARROWS)) {
			callback.setReturnValue(true);
		} else if ((ench == Enchantments.PIERCING && enchantment == Enchantments.MULTISHOT) || (ench == Enchantments.MULTISHOT && enchantment == Enchantments.PIERCING)) {
			callback.setReturnValue(true);
		} else if ((ench == Enchantments.LOYALTY && enchantment == Enchantments.CHANNELING) || (ench == Enchantments.CHANNELING && enchantment == Enchantments.LOYALTY)) {
			callback.setReturnValue(true);
		}
	}
	
	@Inject(method = "getFullname", at = @At("RETURN"), cancellable = true)
	public void getFullname(int level, CallbackInfoReturnable<Component> callback) {
		if (this.isAllowedOnGoldenBooks()) {
			if ((this.getMaxGoldenBookLevel() >= level && level >= this.getMinGoldenBookLevel()) || this.isUpgrade()) {
				MutableComponent component = Component.translatable(this.getDescriptionId());
				component.append(" ").append(Component.translatable("enchantment.level." + level));
				if (this.getUpgradeLevel() >= level && level > 0) {
					callback.setReturnValue(component.withStyle(ChatFormatting.BLUE));
				} else {
					callback.setReturnValue(component.withStyle(ChatFormatting.DARK_PURPLE));
				}
			}
		}
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		Enchantment enchantment = (Enchantment) (Object) this;
		List<Enchantment> goldenEnchantments = Lists.newArrayList(Enchantments.RESPIRATION, Enchantments.DEPTH_STRIDER, Enchantments.SOUL_SPEED, Enchantments.KNOCKBACK, Enchantments.MOB_LOOTING, Enchantments.SWEEPING_EDGE,
			Enchantments.BLOCK_EFFICIENCY, Enchantments.UNBREAKING, Enchantments.BLOCK_FORTUNE, Enchantments.POWER_ARROWS, Enchantments.PUNCH_ARROWS, Enchantments.FISHING_LUCK, Enchantments.FISHING_SPEED, Enchantments.LOYALTY, 
			Enchantments.RIPTIDE, Enchantments.QUICK_CHARGE, Enchantments.PIERCING, Enchantments.SWIFT_SNEAK, XSEnchantments.GROWTH.get(), XSEnchantments.BLASTING.get(), XSEnchantments.HARVESTING.get());
		if (enchantment instanceof ProtectionEnchantment) {
			return true;
		} else if (enchantment instanceof DamageEnchantment) {
			return true;
		} else if (enchantment instanceof FireAspectEnchantment) {
			return true;
		}
		return goldenEnchantments.contains(enchantment);
	}
	
}
