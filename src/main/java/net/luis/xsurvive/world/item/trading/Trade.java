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

package net.luis.xsurvive.world.item.trading;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

public record Trade(@NotNull ItemStack getCost, @NotNull ItemStack getSecondCost, @NotNull ItemStack getResult, int getMaxUses, int getVillagerXp, float getPriceMultiplier) implements ItemListing {
	
	public static final int[] VILLAGER_XP = { 2, 10, 20, 30, 40 };
	
	@Override
	public @NotNull MerchantOffer getOffer(@NotNull Entity villager, @NotNull RandomSource rng) {
		return new MerchantOffer(new ItemCost(this.getCost.getItem(), this.getCost.getCount()), Optional.of(new ItemCost(this.getSecondCost.getItem(), this.getSecondCost.getCount())), this.getResult, this.getMaxUses, this.getVillagerXp, this.getPriceMultiplier);
	}
}
