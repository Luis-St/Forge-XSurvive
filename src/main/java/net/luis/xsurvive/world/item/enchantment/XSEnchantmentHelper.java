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
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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
	
	public static @NotNull @Unmodifiable List<Holder<Enchantment>> getEnchantments(@NotNull Registry<Enchantment> registry) {
		return registry.stream().map(registry::wrapAsHolder).toList();
	}
	
	public static boolean hasEnchantment(@NotNull Holder<Enchantment> enchantment, @NotNull ItemStack stack) {
		return stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).keySet().contains(enchantment);
	}
	
	public static boolean hasMaxEnchantment(@NotNull Holder<Enchantment> enchantment, @NotNull ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) == enchantment.value().getMaxLevel();
		}
		return false;
	}
	
	public static boolean hasMaxGoldenEnchantment(@NotNull Holder<Enchantment> enchantment, @NotNull ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) == GoldenEnchantmentHelper.getMaxGoldenBookLevel(enchantment);
		}
		return false;
	}
	
	public static boolean isEnchantmentCompatible(@NotNull ItemStack stack, @NotNull Holder<Enchantment> enchantment) {
		return EnchantmentHelper.isEnchantmentCompatible(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).keySet(), enchantment);
	}
	
	public static @NotNull List<Holder<Enchantment>> getGoldenEnchantments(@NotNull ItemStack stack) {
		List<Holder<Enchantment>>  enchantments = Lists.newArrayList();
		for (Holder<Enchantment> enchantment : stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).keySet().stream().toList()) {
			if (GoldenEnchantmentHelper.isGoldenEnchantment(enchantment)) {
				if (Math.max(0, EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) - enchantment.value().getMaxLevel()) > 0) {
					enchantments.add(enchantment);
				}
			}
		}
		return enchantments;
	}
	
	public static int getEnchantmentLevel(@NotNull Entity entity, @NotNull ResourceKey<Enchantment> enchantmentKey, @NotNull ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(enchantmentKey.getOrThrow(entity), stack);
	}
	
	public static int getEnchantmentLevel(@NotNull ResourceKey<Enchantment> enchantmentKey, @NotNull LivingEntity entity) {
		Holder<Enchantment> enchantment = enchantmentKey.getOrThrow(entity);
		int level = entity.getMainHandItem().getEnchantments().getLevel(enchantment);
		if (level > 0) {
			return level;
		}
		return entity.getOffhandItem().getEnchantments().getLevel(enchantment);
	}
	
	public static @NotNull Pair<Integer, Integer> getTotalEnchantmentLevel(@NotNull LivingEntity entity, @NotNull Holder<Enchantment> enchantment) {
		int total = 0;
		int items = 0;
		for (EquipmentSlot slot : Stream.of(EquipmentSlot.values()).filter(EquipmentSlot::isArmor).toList()) {
			int level = entity.getItemBySlot(slot).getEnchantments().getLevel(enchantment);
			if (level > 0) {
				total += level;
				items++;
			}
		}
		return Pair.of(total, items);
	}
	
	public static int getAverageEnchantmentLevel(@NotNull LivingEntity entity, @NotNull Holder<Enchantment> enchantment) {
		Pair<Integer, Integer> pair = getTotalEnchantmentLevel(entity, enchantment);
		return 0 >= pair.getSecond() ? 0 : pair.getFirst() / pair.getSecond();
	}
	
	public static void addEnchantment(@NotNull EnchantmentInstance instance, @NotNull ItemStack stack, boolean present) {
		ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
		if ((!hasEnchantment(instance.enchantment, stack) || present) && EnchantmentHelper.isEnchantmentCompatible(enchantments.keySet(), instance.enchantment)) {
			enchantments.set(instance.enchantment, instance.level);
		}
		EnchantmentHelper.setEnchantments(stack, enchantments.toImmutable());
	}
	
	@SuppressWarnings("deprecation")
	public static void removeEnchantment(@NotNull Holder<Enchantment> enchantment, @NotNull ItemStack stack) {
		ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
		if (hasEnchantment(enchantment, stack)) {
			enchantments.removeIf(holder -> holder.is(enchantment));
		}
		EnchantmentHelper.setEnchantments(stack, enchantments.toImmutable());
	}
	
	public static void replaceEnchantment(@NotNull EnchantmentInstance instance, @NotNull ItemStack stack) {
		addEnchantment(instance, stack, true);
	}
	
	public static void increaseEnchantment(@NotNull Holder<Enchantment> enchantment, @NotNull ItemStack stack, boolean golden) {
		if (hasEnchantment(enchantment, stack)) {
			if (!hasMaxEnchantment(enchantment, stack)) {
				replaceEnchantment(new EnchantmentInstance(enchantment, EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) + 1), stack);
			} else if (golden && !hasMaxGoldenEnchantment(enchantment, stack)) {
				replaceEnchantment(new EnchantmentInstance(enchantment, EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) + 1), stack);
			}
		} else {
			addEnchantment(new EnchantmentInstance(enchantment, 1), stack, false);
		}
	}
	
	public static @NotNull EnchantmentInstance increaseEnchantment(@NotNull EnchantmentInstance instance, boolean golden) {
		Holder<Enchantment> enchantment = instance.enchantment;
		if (enchantment.value().getMaxLevel() > instance.level) {
			return new EnchantmentInstance(enchantment, instance.level + 1);
		} else if (golden) {
			if (GoldenEnchantmentHelper.isGoldenEnchantment(enchantment)) {
				if (instance.level != GoldenEnchantmentHelper.getMaxGoldenBookLevel(enchantment)) {
					return new EnchantmentInstance(enchantment, instance.level + 1);
				}
			}
		}
		return instance;
	}
	
	public static @NotNull Map<EquipmentSlot, ItemStack> getItemsWith(@NotNull Holder<Enchantment> enchantment, @NotNull LivingEntity entity, @NotNull Predicate<ItemStack> predicate) {
		Map<EquipmentSlot, ItemStack> items = Maps.newHashMap();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = entity.getItemBySlot(slot);
			if (hasEnchantment(enchantment, stack) && predicate.test(stack)) {
				items.put(slot, stack);
			}
		}
		return items;
	}
	
	public static List<ItemStack> getItemsForEnchantment(@NotNull Holder<Enchantment> enchantment) {
		return ForgeRegistries.ITEMS.getValues().stream().filter((item) -> enchantment.value().canEnchant(new ItemStack(item))).map(ItemStack::new).collect(Collectors.toList());
	}
	
	public static @NotNull Entry<EquipmentSlot, ItemStack> getItemWithEnchantment(@NotNull Holder<Enchantment> enchantment, @NotNull LivingEntity entity) {
		for (Entry<EquipmentSlot, ItemStack> entry : getItemsWith(enchantment, entity, itemStack -> true).entrySet()) {
			if (entry.getKey().getType() == EquipmentSlot.Type.HAND) {
				return entry;
			}
		}
		return new SimpleEntry<>(null, ItemStack.EMPTY);
	}
	
	public static void enchantItem(@NotNull RegistryAccess registryAccess, @NotNull RandomSource rng, @NotNull ItemStack stack, int count, int cost, boolean treasure, boolean golden) {
		List<EnchantmentInstance> instances = Lists.newArrayList();
		Registry<Enchantment> registry = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
		List<Holder<Enchantment>> enchantments;
		if (treasure) {
			enchantments = getEnchantments(registry);
		} else {
			enchantments = registry.get(EnchantmentTags.NON_TREASURE).stream().flatMap(HolderSet::stream).collect(Collectors.toList());
		}
		List<EnchantmentInstance> availableInstances = EnchantmentHelper.getAvailableEnchantmentResults(cost, stack, enchantments.stream());
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
