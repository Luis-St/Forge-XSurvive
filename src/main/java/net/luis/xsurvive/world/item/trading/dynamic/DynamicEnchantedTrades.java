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

package net.luis.xsurvive.world.item.trading.dynamic;

import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static net.luis.xsurvive.world.item.trading.dynamic.DynamicTradeHelper.*;

/**
 *
 * @author Luis-St
 *
 */

public class DynamicEnchantedTrades {
	
	public static @NotNull ItemListing randomEnchantedItem(@NotNull Item item, int emeralds, int maxUses, int villagerXp, float priceMultiplier) {
		return (villager, rng) -> {
			Registry<Enchantment> registry = villager.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
			ItemStack stack = EnchantmentHelper.enchantItem(rng, new ItemStack(item), Math.max(emeralds / 2, 1), getNonTreasureEnchantments(XSEnchantmentHelper.getEnchantments(registry)).stream());
			return new MerchantOffer(new ItemCost(Items.EMERALD, emeralds + getEmeraldCount(stack)), stack, maxUses, villagerXp, priceMultiplier);
		};
	}
	
	public static @NotNull ItemListing randomEnchantedBook(int villagerLevel, @NotNull List<Rarity> rarities) {
		return (villager, rng) -> {
			Holder<Enchantment> enchantment = random(getValidEnchantments(villager, rarities), rng);
			ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
			stack.enchant(enchantment, 1);
			return new MerchantOffer(new ItemCost(Items.EMERALD, getEmeraldCount(rng, enchantment.value().getMaxLevel())), Optional.of(new ItemCost(Items.BOOK)), stack, 1, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static @NotNull ItemListing randomEnchantedGoldenBook(int villagerLevel) {
		return (villager, rng) -> {
			Registry<Enchantment> registry = villager.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
			Holder<Enchantment> enchantment = random(getValidGoldenEnchantments(XSEnchantmentHelper.getEnchantments(registry)), rng);
			ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
			stack.enchant(enchantment, enchantment.value().getMaxLevel());
			return new MerchantOffer(new ItemCost(Items.EMERALD, 64), Optional.empty(), stack, 1, getVillagerXp(villagerLevel), 0.02F);
		};
	}
}
