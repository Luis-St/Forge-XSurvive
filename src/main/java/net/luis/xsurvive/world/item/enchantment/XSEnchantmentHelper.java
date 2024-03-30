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

package net.luis.xsurvive.world.item.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.SimpleEntry;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Luis-St
 *
 */

public class XSEnchantmentHelper {
	
	public static boolean hasEnchantment(@NotNull Enchantment enchantment, @NotNull ItemStack stack) {
		List<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet().stream().toList();
		return enchantments.contains(enchantment);
	}
	
	public static boolean hasMaxEnchantment(@NotNull Enchantment enchantment, @NotNull ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			return stack.getEnchantmentLevel(enchantment) == enchantment.getMaxLevel();
		}
		return false;
	}
	
	public static boolean hasMaxGoldenEnchantment(@NotNull Enchantment enchantment, @NotNull ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			if (enchantment instanceof IEnchantment ench) {
				return stack.getEnchantmentLevel(enchantment) == ench.getMaxGoldenBookLevel();
			} else {
				XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			}
		}
		return false;
	}
	
	public static boolean isEnchantmentCompatible(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
		return EnchantmentHelper.isEnchantmentCompatible(EnchantmentHelper.getEnchantments(stack).keySet(), enchantment);
	}
	
	public static @NotNull List<Enchantment> getGoldenEnchantments(@NotNull ItemStack stack) {
		List<Enchantment> enchantments = Lists.newArrayList();
		for (Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet().stream().toList()) {
			if (enchantment instanceof IEnchantment ench) {
				if (ench.isAllowedOnGoldenBooks() && Math.max(0, stack.getEnchantmentLevel(enchantment) - enchantment.getMaxLevel()) > 0) {
					enchantments.add(enchantment);
				}
			} else {
				XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			}
		}
		return enchantments;
	}
	
	public static int getEnchantmentLevel(@NotNull Enchantment enchantment, @NotNull LivingEntity entity) {
		int level = entity.getMainHandItem().getEnchantmentLevel(enchantment);
		if (level > 0) {
			return level;
		}
		return entity.getOffhandItem().getEnchantmentLevel(enchantment);
	}
	
	public static @NotNull Pair<Integer, Integer> getTotalEnchantmentLevel(@NotNull LivingEntity entity, @NotNull Enchantment enchantment) {
		int total = 0;
		int items = 0;
		for (EquipmentSlot slot : Stream.of(EquipmentSlot.values()).filter(EquipmentSlot::isArmor).toList()) {
			int level = entity.getItemBySlot(slot).getEnchantmentLevel(enchantment);
			if (level > 0) {
				total += level;
				items++;
			}
		}
		return Pair.of(total, items);
	}
	
	public static int getAverageEnchantmentLevel(@NotNull LivingEntity entity, @NotNull Enchantment enchantment) {
		Pair<Integer, Integer> pair = getTotalEnchantmentLevel(entity, enchantment);
		return 0 >= pair.getSecond() ? 0 : pair.getFirst() / pair.getSecond();
	}
	
	public static void addEnchantment(@NotNull EnchantmentInstance instance, @NotNull ItemStack stack, boolean present) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if ((!hasEnchantment(instance.enchantment, stack) || present) && EnchantmentHelper.isEnchantmentCompatible(enchantments.keySet(), instance.enchantment)) {
			enchantments.put(instance.enchantment, instance.level);
		}
		EnchantmentHelper.setEnchantments(enchantments, stack);
	}
	
	public static void removeEnchantment(@NotNull Enchantment enchantment, @NotNull ItemStack stack) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if (hasEnchantment(enchantment, stack)) {
			enchantments.remove(enchantment);
		}
		EnchantmentHelper.setEnchantments(enchantments, stack);
	}
	
	public static void replaceEnchantment(@NotNull EnchantmentInstance instance, @NotNull ItemStack stack) {
		if (hasEnchantment(instance.enchantment, stack)) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			enchantments.replace(instance.enchantment, instance.level);
			EnchantmentHelper.setEnchantments(enchantments, stack);
		}
	}
	
	public static void increaseEnchantment(@NotNull Enchantment enchantment, @NotNull ItemStack stack, boolean golden) {
		if (hasEnchantment(enchantment, stack)) {
			if (!hasMaxEnchantment(enchantment, stack)) {
				replaceEnchantment(new EnchantmentInstance(enchantment, stack.getEnchantmentLevel(enchantment) + 1), stack);
			} else if (golden && !hasMaxGoldenEnchantment(enchantment, stack)) {
				replaceEnchantment(new EnchantmentInstance(enchantment, stack.getEnchantmentLevel(enchantment) + 1), stack);
			}
		} else {
			addEnchantment(new EnchantmentInstance(enchantment, 1), stack, false);
		}
	}
	
	public static @NotNull EnchantmentInstance increaseEnchantment(@NotNull EnchantmentInstance instance, boolean golden) {
		if (instance.level != instance.enchantment.getMaxLevel()) {
			return new EnchantmentInstance(instance.enchantment, instance.level + 1);
		} else if (golden) {
			if (instance.enchantment instanceof IEnchantment ench) {
				if (instance.level != ench.getMaxGoldenBookLevel()) {
					return new EnchantmentInstance(instance.enchantment, instance.level + 1);
				}
			} else {
				XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(instance.enchantment));
			}
		}
		return instance;
	}
	
	public static @NotNull Map<EquipmentSlot, ItemStack> getItemsWith(@NotNull Enchantment enchantment, @NotNull LivingEntity entity, @NotNull Predicate<ItemStack> predicate) {
		Map<EquipmentSlot, ItemStack> items = Maps.newHashMap();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = entity.getItemBySlot(slot);
			if (hasEnchantment(enchantment, stack) && predicate.test(stack)) {
				items.put(slot, stack);
			}
		}
		return items;
	}
	
	public static List<ItemStack> getItemsForEnchantment(Enchantment enchantment) {
		return ForgeRegistries.ITEMS.getValues().stream().filter((item) -> enchantment.canEnchant(new ItemStack(item))).map(ItemStack::new).collect(Collectors.toList());
	}
	
	public static @NotNull Entry<EquipmentSlot, ItemStack> getItemWithEnchantment(@NotNull Enchantment enchantment, @NotNull LivingEntity entity) {
		for (Entry<EquipmentSlot, ItemStack> entry : getItemsWith(enchantment, entity, itemStack -> true).entrySet()) {
			if (entry.getKey().getType() == EquipmentSlot.Type.HAND) {
				return entry;
			}
		}
		return new SimpleEntry<>(null, ItemStack.EMPTY);
	}
	
	public static void enchantItem(RandomSource rng, ItemStack stack, int count, int cost, boolean treasure, boolean golden) {
		List<EnchantmentInstance> instances = Lists.newArrayList();
		List<EnchantmentInstance> availableInstances = EnchantmentHelper.getAvailableEnchantmentResults(cost, stack, treasure);
		Consumer<? super EnchantmentInstance> action = (instance) -> {
			if (golden) {
				instances.add(increaseEnchantment(instance, true));
			} else {
				instances.add(instance);
			}
		};
		if (!availableInstances.isEmpty()) {
			WeightedRandom.getRandomItem(rng, availableInstances).ifPresent(action);
			for (int i = 0; i < count - 1; i++) {
				if (!instances.isEmpty()) {
					EnchantmentHelper.filterCompatibleEnchantments(availableInstances, Util.lastOf(instances));
				}
				if (availableInstances.isEmpty()) {
					break;
				}
				WeightedRandom.getRandomItem(rng, availableInstances).ifPresent(action);
			}
		}
		for (EnchantmentInstance instance : instances) {
			stack.enchant(instance.enchantment, instance.level);
		}
	}
}
