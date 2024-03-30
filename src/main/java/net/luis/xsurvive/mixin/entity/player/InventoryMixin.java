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

package net.luis.xsurvive.mixin.entity.player;

import net.luis.xsurvive.tag.XSDamageTypeTags;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Inventory.class)
public abstract class InventoryMixin {
	
	private static final float ONE_THIRD = 1.0F / 3.0F;
	private static final float TWO_THIRD = 2.0F / 3.0F;
	
	//region Mixin
	@Shadow public NonNullList<ItemStack> armor;
	@Shadow public Player player;
	//endregion
	
	private static float calculateArmorDamage(@NotNull DamageSource source, float damage, @NotNull EquipmentSlot slot) {
		float armorDamage = damage;
		if (source.is(XSDamageTypeTags.DAMAGE_FROM_ABOVE)) {
			armorDamage = switch (slot) {
				case HEAD -> armorDamage * TWO_THIRD;
				case CHEST -> armorDamage * ONE_THIRD;
				default -> 0.0F;
			};
		} else if (source.is(XSDamageTypeTags.DAMAGE_FROM_FRONT)) {
			armorDamage = switch (slot) {
				case CHEST, LEGS -> armorDamage * 0.5F;
				default -> 0.0F;
			};
		} else if (source.is(XSDamageTypeTags.DAMAGE_FROM_BELOW)) {
			armorDamage = switch (slot) {
				case FEET -> armorDamage * TWO_THIRD;
				case LEGS -> armorDamage * ONE_THIRD;
				default -> 0.0F;
			};
		}
		if (source.is(XSDamageTypeTags.FULL_BODY_DAMAGE)) {
			armorDamage *= 0.25F;
		} else if (source.is(XSDamageTypeTags.HEAD_ONLY_DAMAGE)) {
			armorDamage = slot == EquipmentSlot.HEAD ? armorDamage : 0.0F;
		} else if (source.is(XSDamageTypeTags.FEET_ONLY_DAMAGE)) {
			armorDamage = slot == EquipmentSlot.FEET ? armorDamage : 0.0F;
		}
		if (0.0F >= armorDamage) {
			return 0.0F;
		}
		return (float) Math.ceil(armorDamage);
	}
	
	@Inject(method = "hurtArmor", at = @At("HEAD"), cancellable = true)
	public void hurtArmor(@NotNull DamageSource source, float damage, int @NotNull [] slots, CallbackInfo callback) {
		if (damage > 0.0) {
			for (int slot : slots) {
				ItemStack stack = this.armor.get(slot);
				if (stack.isEmpty()) {
					continue;
				}
				EquipmentSlot equipmentSlot = EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, slot);
				float slotDamage = calculateArmorDamage(source, (damage / 4.0F) / 2.0F, equipmentSlot);
				if (!(source.is(DamageTypeTags.IS_FIRE) && stack.getItem().isFireResistant()) && slotDamage > 0.0F) {
					if (stack.getItem() instanceof ArmorItem) {
						stack.hurtAndBreak((int) slotDamage, this.player, (p) -> p.broadcastBreakEvent(equipmentSlot));
					}
				}
			}
		}
		callback.cancel();
	}
}
