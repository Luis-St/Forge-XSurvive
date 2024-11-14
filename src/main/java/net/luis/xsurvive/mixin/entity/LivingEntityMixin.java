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

package net.luis.xsurvive.mixin.entity;

import net.luis.xsurvive.tag.XSDamageTypeTags;
import net.luis.xsurvive.world.entity.ILivingEntity;
import net.luis.xsurvive.world.entity.ai.custom.CustomAi;
import net.luis.xsurvive.world.entity.ai.custom.CustomAiManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

@Mixin(LivingEntity.class)
@SuppressWarnings("DataFlowIssue")
public abstract class LivingEntityMixin extends Entity implements ILivingEntity {
	
	private static final float ONE_THIRD = 1.0F / 3.0F;
	private static final float TWO_THIRD = 2.0F / 3.0F;
	
	//region Mixin
	private @Nullable CustomAi customAi;
	
	protected LivingEntityMixin(@NotNull EntityType<?> entityType, @NotNull Level level) {
		super(entityType, level);
	}
	
	@Shadow
	public abstract ItemStack getItemBySlot(@NotNull EquipmentSlot slot);
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
	
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void init(@NotNull CallbackInfo callback) {
		if (this.level() instanceof ServerLevel && CustomAiManager.INSTANCE.hasFactory((LivingEntity) (Object) this)) {
			this.customAi = CustomAiManager.INSTANCE.createFactory((LivingEntity) (Object) this, (ServerLevel) this.level());
		} else {
			this.customAi = null;
		}
	}
	
	@Inject(method = "doHurtEquipment", at = @At("HEAD"), cancellable = true)
	protected void doHurtEquipment(@NotNull DamageSource source, float damage, EquipmentSlot @NotNull [] slots, @NotNull CallbackInfo callback) {
		if (damage > 0.0) {
			for (EquipmentSlot slot : slots) {
				ItemStack stack = this.getItemBySlot(slot);
				if (stack.isEmpty()) {
					continue;
				}
				float slotDamage = calculateArmorDamage(source, (damage / 4.0F) / 2.0F, slot);
				DamageResistant resistant = stack.get(DataComponents.DAMAGE_RESISTANT);
				if (!(resistant != null && resistant.isResistantTo(source)) && slotDamage > 0.0F) {
					if (stack.getItem() instanceof ArmorItem) {
						stack.hurtAndBreak((int) slotDamage, (LivingEntity) (Object) this, slot);
					}
				}
			}
		}
		callback.cancel();
	}
	
	@Override
	public boolean hasCustomAi() {
		return !this.level().isClientSide && this.customAi != null;
	}
	
	@Override
	public @Nullable CustomAi getCustomAi() {
		return !this.level().isClientSide ? this.customAi : null;
	}
}
