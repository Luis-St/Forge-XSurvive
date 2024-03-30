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

import com.google.common.collect.Lists;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static net.luis.xsurvive.world.item.trading.dynamic.DynamicTradeHelper.*;

/**
 *
 * @author Luis-St
 *
 */

public class DynamicPotionTrades {
	
	public static @NotNull ItemListing randomPotion(int villagerLevel) {
		return (villager, rng) -> {
			Potion potion = random(Lists.newArrayList(ForgeRegistries.POTIONS.getValues()), rng);
			int emeralds = Math.min(getEmeraldCount(rng, potion.getEffects()), 64);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), PotionUtils.setPotion(new ItemStack(Items.POTION), potion), 16, getVillagerXp(villagerLevel),
				0.2F);
		};
	}
	
}
