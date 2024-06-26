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

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	
	@Inject(method = "getEnchantments", at = @At("HEAD"), cancellable = true)
	private static void getEnchantments(@NotNull ItemStack stack, CallbackInfoReturnable<Map<Enchantment, Integer>> callback) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			Map<Enchantment, Integer> enchantments = new LinkedHashMap<>();
			CompoundTag tag = stack.getTag() != null ? stack.getTag().getCompound(XSurvive.MOD_NAME + "GoldenEnchantments") : null;
			if (tag != null) {
				enchantments.put(ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(tag.getString("enchantment"))), 1);
			}
			callback.setReturnValue(enchantments);
		}
	}
	
	@Inject(method = "setEnchantments", at = @At("TAIL"))
	private static void setEnchantments(Map<Enchantment, Integer> enchantments, @NotNull ItemStack stack, CallbackInfo callback) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			if (enchantments.size() == 1) {
				Enchantment enchantment = enchantments.keySet().stream().toList().get(0);
				if (enchantment instanceof IEnchantment ench) {
					if (ench.isAllowedOnGoldenBooks()) {
						CompoundTag tag = new CompoundTag();
						tag.putString("enchantment", Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getKey(enchantment)).toString());
						stack.getOrCreateTag().put(XSurvive.MOD_NAME + "GoldenEnchantments", tag);
					} else {
						XSurvive.LOGGER.info("The Enchantment '{}' which should be set is no allowed on EnchantedGoldenBookItems", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					}
				} else {
					XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				}
				
			} else {
				XSurvive.LOGGER.error("Fail to set the Enchantment ({}) for a EnchantedGoldenBookItem, since the given Map size must be 1", enchantments);
			}
			stack.removeTagKey("Enchantments");
		}
	}
	
	@Inject(method = "enchantItem", at = @At("HEAD"), cancellable = true)
	private static void enchantItem(RandomSource rng, @NotNull ItemStack stack, int cost, boolean treasure, CallbackInfoReturnable<ItemStack> callback) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			List<Enchantment> enchantments = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter((enchantment) -> {
				if (enchantment instanceof IEnchantment ench) {
					return ench.isAllowedOnGoldenBooks();
				}
				XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return false;
			}).toList();
			EnchantmentHelper.setEnchantments(Map.of(enchantments.get(rng.nextInt(enchantments.size())), 1), stack);
			callback.setReturnValue(stack);
		}
	}
}
