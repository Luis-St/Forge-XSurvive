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

package net.luis.xsurvive.world.item;

import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class ItemStackHelper {
	
	public static @NotNull ItemStack setupItemForDifficulty(@NotNull Difficulty difficulty, @NotNull LivingEntity entity, @NotNull ItemStack stack) {
		RandomSource rng = entity.getRandom();
		int count = 1 + rng.nextInt(2);
		if (difficulty == Difficulty.HARD) {
			count = 2 + rng.nextInt(2);
		}
		int cost = 20 + rng.nextInt(difficulty == Difficulty.HARD ? 30 : 18);
		XSEnchantmentHelper.enchantItem(entity.registryAccess(), rng, stack, count, cost, false, false);
		return stack;
	}
	
	public static @NotNull ItemStack getArmorForDifficulty(@NotNull Difficulty difficulty, @NotNull EquipmentSlot slot) {
		return new ItemStack(ItemEquipmentHelper.getArmorWeights(difficulty).next().get(slot));
	}
	
	public static @NotNull ItemStack getWeaponForDifficulty(@NotNull Difficulty difficulty, @NotNull WeaponType weapon, @NotNull LivingEntity entity) {
		return switch (weapon) {
			case SWORD -> getSwordForDifficulty(difficulty, entity);
			case AXE -> getAxeForDifficulty(difficulty, entity);
			case BOW -> getBowForDifficulty(difficulty);
			case CROSSBOW -> getCrossbowForDifficulty(difficulty);
		};
	}
	
	public static @NotNull ItemStack getSwordForDifficulty(@NotNull Difficulty difficulty, @NotNull LivingEntity entity) {
		List<Item> weapons = ItemEquipmentHelper.getWeaponWeights(difficulty).next();
		weapons.removeIf((item) -> !(item instanceof SwordItem));
		if (!weapons.isEmpty()) {
			return new ItemStack(weapons.get(entity.getRandom().nextInt(weapons.size())));
		}
		return new ItemStack(Items.IRON_SWORD);
	}
	
	public static @NotNull ItemStack getAxeForDifficulty(@NotNull Difficulty difficulty, @NotNull LivingEntity entity) {
		List<Item> weapons = ItemEquipmentHelper.getWeaponWeights(difficulty).next();
		weapons.removeIf((item) -> !(item instanceof AxeItem));
		if (!weapons.isEmpty()) {
			return new ItemStack(weapons.get(entity.getRandom().nextInt(weapons.size())));
		}
		return new ItemStack(Items.IRON_AXE);
	}
	
	public static @NotNull ItemStack getBowForDifficulty(@NotNull Difficulty difficulty) {
		return new ItemStack(ItemEquipmentHelper.getBowWeights(difficulty).next());
	}
	
	public static @NotNull ItemStack getCrossbowForDifficulty(@NotNull Difficulty difficulty) {
		return new ItemStack(ItemEquipmentHelper.getCrossbowWeights(difficulty).next());
	}
}
