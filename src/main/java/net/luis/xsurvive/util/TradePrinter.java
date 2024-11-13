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

package net.luis.xsurvive.util;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("unused")
public class TradePrinter {
	
	public static void print(@NotNull Villager villager) {
		System.out.println();
		for (Map.Entry<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> entry1 : VillagerTrades.TRADES.entrySet()) {
			System.out.println("VillagerProfession: " + StringUtils.capitalize(entry1.getKey().name()));
			var list = Lists.newArrayList(entry1.getValue().int2ObjectEntrySet());
			list.sort(Comparator.comparingInt(Int2ObjectMap.Entry::getIntKey));
			for (Int2ObjectMap.Entry<VillagerTrades.ItemListing[]> entry2 : list) {
				System.out.println("Level: " + entry2.getIntKey());
				for (VillagerTrades.ItemListing listing : entry2.getValue()) {
					System.out.println(offerToString(listing.getOffer(villager, RandomSource.create())));
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	private static @NotNull String offerToString(@Nullable MerchantOffer offer) {
		if (offer == null) {
			return "null";
		}
		String data = String.format("[MaxUses: %-2d, PriceMultiplier: %-4s, VillagerXp: %-2d]: ", offer.getMaxUses(), offer.getPriceMultiplier(), offer.getXp());
		
		ItemStack costA = offer.getBaseCostA();
		int countA = costA.getCount();
		String nameA = costA.getDisplayName().getString().replace("[", "").replace("]", "");
		
		ItemStack costB = offer.getCostB();
		int countB = costB.getCount();
		String nameB = costB.getDisplayName().getString().replace("[", "").replace("]", "");
		
		ItemStack result = offer.getResult();
		int countResult = result.getCount();
		String resultName = result.getDisplayName().getString().replace("[", "").replace("]", "");
		
		String resultData = " ";
		if (result.getItem() instanceof EnchantedBookItem) {
			resultData += "[Common or rare enchantment, Result level 1]";
		} else if (result.getItem() instanceof EnchantedGoldenBookItem) {
			resultData += "[Random enchantment, Enchanted Book max level]";
		} else if (result.getItem() instanceof PotionItem) {
			resultName = "Potion";
			resultData += "[Random potion]";
		} else if (result.isEnchanted()) {
			resultData += "[Enchanted]";
		}
		if (costB.isEmpty()) {
			return data + countA + "x " + nameA + " -> " + countResult + "x " + resultName + resultData;
		}
		return data + countA + "x " + nameA + " + " + countB + "x " + nameB + " -> " + countResult + "x " + resultName + resultData;
	}
}
