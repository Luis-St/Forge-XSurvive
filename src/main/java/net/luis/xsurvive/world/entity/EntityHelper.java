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

import com.google.common.collect.Lists;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.ItemEquipmentHelper;
import net.luis.xsurvive.world.item.ItemStackHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

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
			if (equipmentSlot.getType() == Type.HUMANOID_ARMOR) {
				if (equipmentSlot == slot) {
					growth += stack.getEnchantments().getLevel(XSEnchantments.GROWTH.getOrThrow(entity));
				} else {
					growth += entity.getItemBySlot(equipmentSlot).getEnchantments().getLevel(XSEnchantments.GROWTH.getOrThrow(entity));
				}
			}
		}
		return growth;
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
	
	public static void equipEntityForDifficulty(@NotNull LivingEntity entity, @NotNull DifficultyInstance instance) {
		double difficulty = instance.getEffectiveDifficulty();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot != EquipmentSlot.OFFHAND && entity.getItemBySlot(slot).isEmpty() && (entity.getRandom().nextDouble() / 2.0) + 0.5 * instance.getSpecialMultiplier() > entity.getRandom().nextDouble()) {
				WeightCollection<List<Item>> itemWeights = slot == EquipmentSlot.MAINHAND ? ItemEquipmentHelper.getWeaponWeightsForDifficulty(difficulty) : ItemEquipmentHelper.getArmorWeightsForDifficulty(difficulty);
				if (!itemWeights.isEmpty()) {
					entity.setItemSlot(slot, ItemStackHelper.setupRandomItemForSlot(entity, slot, Lists.newArrayList(itemWeights.next()), instance.getSpecialMultiplier()));
					if (entity instanceof Monster monster) {
						monster.setDropChance(slot, slot.getType() == Type.HAND ? 0.04F : 0.02F);
					}
				}
			}
		}
	}
	
	public static @NotNull Vec3 clipWithDistance(@NotNull Player player, @NotNull Level level, double clipDistance) {
		double vecX = Math.sin(-player.getYRot() * (Math.PI / 180.0) - Math.PI) * -Math.cos(-player.getXRot() * (Math.PI / 180.0));
		double vecY = Math.sin(-player.getXRot() * (Math.PI / 180.0));
		double vecZ = Math.cos(-player.getYRot() * (Math.PI / 180.0) - Math.PI) * -Math.cos(-player.getXRot() * (Math.PI / 180.0));
		return level.clip(new ClipContext(player.getEyePosition(), player.getEyePosition().add(vecX * clipDistance, vecY * clipDistance, vecZ * clipDistance), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)).getLocation();
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
	
	public static List<ItemStack> getArmorItems(@NotNull LivingEntity entity) {
		return Stream.of(EquipmentSlot.values()).filter(EquipmentSlot::isArmor).map(entity::getItemBySlot).collect(Collectors.toList());
	}
	
	public static boolean isWearing(@NotNull LivingEntity entity, @NotNull Predicate<ItemStack> predicate) {
		return getArmorItems(entity).stream().allMatch(predicate);
	}
	
	public static boolean isUsingItem(@NotNull LivingEntity entity, @NotNull Predicate<ItemStack> predicate) {
		ItemStack main = entity.getMainHandItem();
		ItemStack off = entity.getOffhandItem();
		return predicate.test(main) || predicate.test(off);
	}
}
