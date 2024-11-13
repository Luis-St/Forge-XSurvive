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

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class AdvancedTradeBuilder {
	
	private final ItemStack cost;
	private final ItemStack secondCost;
	private final ItemStack result;
	private int maxUses;
	private int villagerXp;
	private float priceMultiplier;
	
	private AdvancedTradeBuilder(@NotNull ItemStack cost, @NotNull ItemStack secondCost, @NotNull ItemStack result) {
		this.cost = cost;
		this.secondCost = secondCost;
		this.result = result;
	}
	
	public static @NotNull AdvancedTradeBuilder simple(@NotNull ItemStack cost, @NotNull ItemStack secondCost, @NotNull ItemStack result) {
		return new AdvancedTradeBuilder(cost, secondCost, result);
	}
	
	public static @NotNull AdvancedTradeBuilder simple(@NotNull ItemLike cost, int costCount, @NotNull ItemLike secondCost, int secondCostCount, @NotNull ItemLike result, int resultCount) {
		return simple(new ItemStack(cost, costCount), new ItemStack(secondCost, secondCostCount), new ItemStack(result, resultCount));
	}
	
	public static @NotNull AdvancedTradeBuilder expensiveItem(int emeralds, @NotNull ItemLike result, int count) {
		if (64 >= emeralds) {
			return simple(new ItemStack(Items.EMERALD, emeralds), ItemStack.EMPTY, new ItemStack(result, count));
		}
		emeralds = Math.min(emeralds, 128);
		return simple(Items.EMERALD, 64, Items.EMERALD, emeralds - 64, result, count);
	}
	
	public static @NotNull AdvancedTradeBuilder processItem(@NotNull ItemLike cost, int costCount, int emeralds, @NotNull ItemLike result, int resultCount) {
		return simple(cost, costCount, Items.EMERALD, emeralds, result, resultCount);
	}
	
	public static @NotNull AdvancedTradeBuilder firework(int emeralds, int count, int flightDuration) {
		ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET, count);
		stack.set(DataComponents.FIREWORKS, new Fireworks(flightDuration, List.of()));
		return simple(new ItemStack(Items.EMERALD, emeralds), ItemStack.EMPTY, stack);
	}
	
	public @NotNull AdvancedTradeBuilder defaultMaxUses() {
		return this.maxUses(16);
	}
	
	public @NotNull AdvancedTradeBuilder maxUses(int maxUses) {
		this.maxUses = maxUses;
		return this;
	}
	
	public @NotNull AdvancedTradeBuilder defaultVillagerXp(int level) {
		return this.villagerXp(Trade.VILLAGER_XP[level - 1]);
	}
	
	public @NotNull AdvancedTradeBuilder villagerXp(int villagerXp) {
		this.villagerXp = villagerXp;
		return this;
	}
	
	public @NotNull AdvancedTradeBuilder defaultMultiplier() {
		return this.multiplier(0.05F);
	}
	
	public @NotNull AdvancedTradeBuilder multiplier(float priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
		return this;
	}
	
	public @NotNull AdvancedTradeBuilder defaultValues(int level) {
		return this.defaultMaxUses().defaultVillagerXp(level).defaultMultiplier();
	}
	
	public @NotNull ItemListing build() {
		return new Trade(this.cost, this.secondCost, this.result, this.maxUses, this.villagerXp, this.priceMultiplier);
	}
	
	public @NotNull ItemListing defaultBuild(int level) {
		return this.defaultValues(level).build();
	}
}
