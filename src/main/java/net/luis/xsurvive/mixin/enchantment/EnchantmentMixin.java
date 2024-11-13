/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.mixin.enchantment;

/**
 *
 * @author Luis-St
 *
 */

/*@Mixin(Enchantment.class)
@SuppressWarnings("DataFlowIssue")
public abstract class EnchantmentMixin implements IEnchantment {
	
	private static final Enchantment[] goldenEnchantments = {
		Enchantments.RESPIRATION, Enchantments.DEPTH_STRIDER, Enchantments.SOUL_SPEED, Enchantments.KNOCKBACK, Enchantments.MOB_LOOTING, Enchantments.SWEEPING_EDGE,
		Enchantments.BLOCK_EFFICIENCY, Enchantments.UNBREAKING, Enchantments.BLOCK_FORTUNE, Enchantments.POWER_ARROWS, Enchantments.PUNCH_ARROWS, Enchantments.FISHING_LUCK, Enchantments.FISHING_SPEED, Enchantments.LOYALTY,
		Enchantments.RIPTIDE, Enchantments.QUICK_CHARGE, Enchantments.PIERCING, Enchantments.SWIFT_SNEAK
	};
	
	//region Mixin
	@Shadow
	public abstract int getMaxLevel();
	
	@Shadow
	public abstract String getDescriptionId();
	//endregion
	
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
			if ((this.getMaxGoldenBookLevel() >= level && level >= this.getMinGoldenBookLevel()) || this.isUpgradeEnchantment()) {
				MutableComponent component = Component.translatable(this.getDescriptionId());
				component.append(" ").append(Component.translatable("enchantment.level." + level));
				if (this.getMaxUpgradeLevel() >= level && level > 0) {
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
		if (enchantment instanceof ProtectionEnchantment) {
			return true;
		} else if (enchantment instanceof DamageEnchantment) {
			return true;
		} else if (enchantment instanceof FireAspectEnchantment) {
			return true;
		}
		return ArrayUtils.contains(goldenEnchantments, enchantment);
	}
}*/
