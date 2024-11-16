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

import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.GoldenEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(EnchantmentHelper.class)
@SuppressWarnings({ "DataFlowIssue", "ReturnOfNull" })
public abstract class EnchantmentHelperMixin {
	
	//region Mixin
	@Shadow
	private static @NotNull DataComponentType<ItemEnchantments> getComponentType(@NotNull ItemStack stack) {
		return null;
	}
	//endregion
	
	@Inject(method = "getItemEnchantmentLevel", at = @At("HEAD"), cancellable = true)
	private static void getItemEnchantmentLevel(@NotNull Holder<Enchantment> enchantment, @NotNull ItemStack stack, @NotNull CallbackInfoReturnable<Integer> callback) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			if (enchantment == EnchantedGoldenBookItem.getEnchantment(stack)) {
				callback.setReturnValue(1);
			} else {
				callback.setReturnValue(0);
			}
		}
	}
	
	@Inject(method = "updateEnchantments", at = @At("HEAD"), cancellable = true)
	private static void updateEnchantments(@NotNull ItemStack stack, @NotNull Consumer<ItemEnchantments.Mutable> action, @NotNull CallbackInfoReturnable<ItemEnchantments> callback) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			DataComponentType<ItemEnchantments> componentType = getComponentType(stack);
			ItemEnchantments originalEnchantments = stack.get(componentType);
			if (originalEnchantments == null) {
				callback.setReturnValue(ItemEnchantments.EMPTY);
			} else {
				ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(originalEnchantments);
				action.accept(mutable);
				stack.set(componentType, mutable.toImmutable());
				
				ItemEnchantments.Mutable finalEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
				finalEnchantments.set(EnchantedGoldenBookItem.getEnchantment(stack), 1);
				stack.set(componentType, finalEnchantments.toImmutable());
				callback.setReturnValue(stack.getOrDefault(componentType, ItemEnchantments.EMPTY));
			}
		}
	}
	
	@Inject(method = "enchantItem(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;ILjava/util/stream/Stream;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	private static void enchantItem(@NotNull RandomSource rng, @NotNull ItemStack originalStack, int cost, @NotNull Stream<Holder<Enchantment>> enchantmentStream, @NotNull CallbackInfoReturnable<ItemStack> callback) {
		if (originalStack.getItem() instanceof EnchantedGoldenBookItem) {
			List<Holder<Enchantment>> enchantments = enchantmentStream.filter(GoldenEnchantmentHelper::isEnchantment).toList();
			if (enchantments.isEmpty()) {
				callback.setReturnValue(originalStack);
				return;
			}
			Holder<Enchantment> enchantment = enchantments.get(rng.nextInt(enchantments.size()));
			EnchantedGoldenBookItem.setEnchantment(originalStack, enchantment);
			callback.setReturnValue(originalStack);
		}
	}
}
