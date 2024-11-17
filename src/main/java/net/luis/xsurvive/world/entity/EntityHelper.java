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

package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.world.item.ItemStackHelper;
import net.luis.xsurvive.world.item.WeaponType;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Luis-St
 *
 */

public class EntityHelper {
	
	public static boolean isAffectedByFrost(@NotNull Entity entity) {
		return entity instanceof MagmaCube || entity instanceof Ghast || entity instanceof Blaze || entity instanceof Strider;
	}
	
	public static int getGrowthLevel(@NotNull LivingEntity entity, @NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
		int growth = 0;
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			ItemEnchantments enchantments;
			if (equipmentSlot.getType() == Type.HUMANOID_ARMOR) {
				if (equipmentSlot == slot) {
					enchantments = stack.getEnchantments();
				} else {
					enchantments = entity.getItemBySlot(equipmentSlot).getEnchantments();
				}
				growth += enchantments.getLevel(XSEnchantments.GROWTH.getOrThrow(entity));
			}
		}
		return growth;
	}
	
	public static void equipEntityForDifficulty(@NotNull ServerLevel level, @Nullable WeaponType weapon, @NotNull LivingEntity entity) {
		Difficulty difficulty = level.getDifficulty();
		if (difficulty == Difficulty.PEACEFUL || difficulty == Difficulty.EASY) {
			return;
		}
		List<EquipmentSlot> slots = Stream.of(EquipmentSlot.values()).filter((slot) -> slot.getType() != Type.ANIMAL_ARMOR && slot != EquipmentSlot.OFFHAND).collect(Collectors.toList());
		Collections.shuffle(slots, () -> entity.getRandom().nextLong());
		
		int slotsEquipped = 0;
		for (EquipmentSlot slot : slots) {
			if (!entity.getItemBySlot(slot).isEmpty()) {
				continue;
			}
			double rng = Math.max(0, entity.getRandom().nextDouble() - (slotsEquipped * 0.075));
			boolean equip = rng > (difficulty == Difficulty.HARD ? 0.4 : 0.5);
			if (equip) {
				ItemStack equipment = ItemStack.EMPTY;
				if (slot == EquipmentSlot.MAINHAND && weapon != null) {
					equipment = ItemStackHelper.getWeaponForDifficulty(difficulty, weapon, entity);
				} else if (slot.getType() == Type.HUMANOID_ARMOR) {
					equipment = ItemStackHelper.getArmorForDifficulty(difficulty, slot);
				}
				if (!equipment.isEmpty()) {
					entity.setItemSlot(slot, ItemStackHelper.setupItemForDifficulty(difficulty, entity, equipment));
					if (entity instanceof Monster monster) {
						monster.setDropChance(slot, slot.getType() == Type.HAND ? 0.04F : 0.02F);
					}
				}
				++slotsEquipped;
			}
		}
	}
	
	public static @NotNull Vec3 clipWithDistance(@NotNull Player player, @NotNull Level level, double clipDistance) {
		double vecX = Math.sin(-player.getYRot() * (Math.PI / 180.0) - Math.PI) * -Math.cos(-player.getXRot() * (Math.PI / 180.0));
		double vecY = Math.sin(-player.getXRot() * (Math.PI / 180.0));
		double vecZ = Math.cos(-player.getYRot() * (Math.PI / 180.0) - Math.PI) * -Math.cos(-player.getXRot() * (Math.PI / 180.0));
		return level.clip(new ClipContext(player.getEyePosition(), player.getEyePosition().add(vecX * clipDistance, vecY * clipDistance, vecZ * clipDistance), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)).getLocation();
	}
	
	public static void updateAttributeModifier(@NotNull Player player, @NotNull Holder<Attribute> attribute, @NotNull Operation operation, @NotNull ResourceLocation location, int to, int from, double multiplier) {
		AttributeInstance instance = player.getAttribute(attribute);
		if (instance != null) {
			AttributeModifier modifier = new AttributeModifier(location, to * multiplier, operation);
			boolean hasModifier = instance.getModifier(location) != null;
			if (to == from && !hasModifier) {
				instance.addTransientModifier(modifier);
			} else if (to != from) {
				if (hasModifier) {
					instance.removeModifier(location);
					instance.addTransientModifier(modifier);
				} else {
					instance.addTransientModifier(modifier);
				}
			}
		}
	}
	
	public static void addAttributeModifier(@NotNull LivingEntity entity, @NotNull Holder<Attribute> attribute, @NotNull AttributeModifier modifier) {
		AttributeMap attributes = entity.getAttributes();
		if (attributes.hasAttribute(attribute)) {
			AttributeInstance instance = attributes.getInstance(attribute);
			if (instance != null && instance.getModifier(modifier.id()) == null) {
				instance.addPermanentModifier(modifier);
			}
		}
	}
	
	public static boolean isUsingItem(@NotNull LivingEntity entity, @NotNull Predicate<ItemStack> predicate) {
		return predicate.test(entity.getMainHandItem()) || predicate.test(entity.getOffhandItem());
	}
}
