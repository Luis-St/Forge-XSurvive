package net.luis.xsurvive.world.item.enchantment;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class EnchantmentHandler {
	
	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
		List<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet().stream().toList();
		return enchantments.contains(enchantment);
	}
	
	public static boolean hasGoldenEnchantment(ItemStack stack) {
		return !getGoldenEnchantments(stack).isEmpty();
	}
	
	public static boolean hasMinEnchantment(Enchantment enchantment, ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			return stack.getEnchantmentLevel(enchantment) == enchantment.getMinLevel();
		}
		return false;
	}
	
	public static boolean hasMaxEnchantment(Enchantment enchantment, ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			return stack.getEnchantmentLevel(enchantment) == enchantment.getMaxLevel();
		}
		return false;
	}
	
	public static boolean isEnchantmentCompatible(ItemStack stack, Enchantment enchantment) {
		return EnchantmentHelper.isEnchantmentCompatible(EnchantmentHelper.getEnchantments(stack).keySet(), enchantment);
	}
	
	public static List<Enchantment> getGoldenEnchantments(ItemStack stack) {
		List<Enchantment> enchantments = Lists.newArrayList();
		for (Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet().stream().toList()) {
			if (enchantment instanceof IEnchantment ench) {
				if (ench.isAllowedOnGoldenBooks() && Math.max(0, stack.getEnchantmentLevel(enchantment) - enchantment.getMaxLevel()) > 0) {
					enchantments.add(enchantment);
				}
			} else {
				XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			}
		}
		return enchantments;
	}
	
	public static int getEnchantmentLevel(Enchantment enchantment, LivingEntity entity) {
		int level = entity.getMainHandItem().getEnchantmentLevel(enchantment);
		if (level > 0) {
			return level;
		}
		return entity.getOffhandItem().getEnchantmentLevel(enchantment);
	}
	
	public static void addEnchantment(EnchantmentInstance instance, ItemStack stack, boolean present) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if ((!hasEnchantment(instance.enchantment, stack) || present) && EnchantmentHelper.isEnchantmentCompatible(enchantments.keySet(), instance.enchantment)) {
			enchantments.put(instance.enchantment, instance.level);
		}
		EnchantmentHelper.setEnchantments(enchantments, stack);
	}
	
	public static void removeEnchantment(Enchantment enchantment, ItemStack stack) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if (hasEnchantment(enchantment, stack)) {
			enchantments.remove(enchantment);
		}
		EnchantmentHelper.setEnchantments(enchantments, stack);
	}
	
	public static void replaceEnchantment(EnchantmentInstance instance, ItemStack stack) {
		if (hasEnchantment(instance.enchantment, stack)) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			enchantments.replace(instance.enchantment, instance.level);
			EnchantmentHelper.setEnchantments(enchantments, stack);
		}
	}
	
	public static void increaseEnchantment(Enchantment enchantment, int count, ItemStack stack, boolean golden) {
		for (int i = 0; i < count; i++) {
			increaseEnchantment(enchantment, stack, golden);
		}
	}
	
	public static void increaseEnchantment(Enchantment enchantment, ItemStack stack, boolean golden) {
		if (hasEnchantment(enchantment, stack)) {
			if (!hasMaxEnchantment(enchantment, stack) || golden) {
				replaceEnchantment(new EnchantmentInstance(enchantment, stack.getEnchantmentLevel(enchantment) + 1), stack);
			}
		} else {
			addEnchantment(new EnchantmentInstance(enchantment, 1), stack, false);
		}
	}
	
	public static void decreaseEnchantment(Enchantment enchantment, int count, ItemStack stack) {
		for (int i = 0; i < count; i++) {
			decreaseEnchantment(enchantment, stack);
		}
	}
	
	public static void decreaseEnchantment(Enchantment enchantment, ItemStack stack) {
		if (hasEnchantment(enchantment, stack)) {
			if (hasMinEnchantment(enchantment, stack)) {
				removeEnchantment(enchantment, stack);
			} else {
				replaceEnchantment(new EnchantmentInstance(enchantment, stack.getEnchantmentLevel(enchantment) - 1), stack);
			}
		}
	}
	
	public static Map<EquipmentSlot, ItemStack> getItemsWith(Enchantment enchantment, Player player, Predicate<ItemStack> predicate) {
		Map<EquipmentSlot, ItemStack> items = Maps.newHashMap();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = player.getItemBySlot(slot);
			if (hasEnchantment(enchantment, stack) && predicate.test(stack)) {
				items.put(slot, stack);
			}
		}
		return items;
	}
	
}
