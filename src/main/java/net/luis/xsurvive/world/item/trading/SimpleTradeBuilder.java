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

import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class SimpleTradeBuilder {
	
	private final ItemStack cost;
	private final ItemStack secondCost;
	private final ItemStack result;
	private int maxUses;
	private int villagerXp;
	private float priceMultiplier;
	
	private SimpleTradeBuilder(@NotNull ItemStack cost, @NotNull ItemStack secondCost, @NotNull ItemStack result) {
		this.cost = cost;
		this.secondCost = secondCost;
		this.result = result;
	}
	
	public static @NotNull SimpleTradeBuilder simple(@NotNull ItemLike cost, int costCount, @NotNull ItemLike result, int resultCount) {
		return new SimpleTradeBuilder(new ItemStack(cost, costCount), ItemStack.EMPTY, new ItemStack(result, resultCount));
	}
	
	public static @NotNull SimpleTradeBuilder emerald(@NotNull ItemLike cost, int count, int emeralds) {
		return simple(cost, count, Items.EMERALD, emeralds);
	}
	
	public static @NotNull SimpleTradeBuilder item(int emeralds, @NotNull ItemLike result, int count) {
		return simple(Items.EMERALD, emeralds, result, count);
	}
	
	public @NotNull SimpleTradeBuilder defaultMaxUses() {
		return this.maxUses(16);
	}
	
	public @NotNull SimpleTradeBuilder maxUses(int maxUses) {
		this.maxUses = maxUses;
		return this;
	}
	
	public @NotNull SimpleTradeBuilder defaultVillagerXp(int level) {
		return this.villagerXp(Trade.VILLAGER_XP[level - 1]);
	}
	
	public @NotNull SimpleTradeBuilder villagerXp(int villagerXp) {
		this.villagerXp = villagerXp;
		return this;
	}
	
	public @NotNull SimpleTradeBuilder defaultMultiplier() {
		return this.multiplier(0.05F);
	}
	
	public @NotNull SimpleTradeBuilder multiplier(float priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
		return this;
	}
	
	public @NotNull SimpleTradeBuilder defaultValues(int level) {
		return this.defaultMaxUses().defaultVillagerXp(level).defaultMultiplier();
	}
	
	public @NotNull ItemListing build() {
		return new Trade(this.cost, this.secondCost, this.result, this.maxUses, this.villagerXp, this.priceMultiplier);
	}
	
	public @NotNull ItemListing defaultBuild(int level) {
		return this.defaultValues(level).build();
	}
}
