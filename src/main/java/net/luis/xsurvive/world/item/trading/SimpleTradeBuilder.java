package net.luis.xsurvive.world.item.trading;

import java.util.Objects;

import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

/**
 * 
 * @author Luis-st
 *
 */

public class SimpleTradeBuilder {
	
	private final ItemStack cost;
	private final ItemStack secondCost;
	private final ItemStack result;
	private int maxUses;
	private int villagerXp;
	private float priceMultiplier;
	
	private SimpleTradeBuilder(ItemStack cost, ItemStack secondCost, ItemStack result) {
		this.cost = Objects.requireNonNull(cost);
		this.secondCost = Objects.requireNonNull(secondCost);
		this.result = result;
	}
	
	public static SimpleTradeBuilder simple(ItemLike cost, int costCount, ItemLike result, int resultCount) {
		return new SimpleTradeBuilder(new ItemStack(cost, costCount), ItemStack.EMPTY, new ItemStack(result, resultCount));
	}
	
	public static SimpleTradeBuilder emerald(ItemLike cost, int count, int emeralds) {
		return simple(cost, count, Items.EMERALD, emeralds);
	}
	
	public static SimpleTradeBuilder item(int emeralds, ItemLike result, int count) {
		return simple(Items.EMERALD, emeralds, result, count);
	}
	
	public SimpleTradeBuilder defaultMaxUses() {
		return this.maxUses(16);
	}
	
	public SimpleTradeBuilder maxUses(int maxUses) {
		this.maxUses = maxUses;
		return this;
	}
	
	public SimpleTradeBuilder defaultVillagerXp(int level) {
		return this.villagerXp(Trade.VILLAGER_XP[level - 1]);
	}
	
	public SimpleTradeBuilder villagerXp(int villagerXp) {
		this.villagerXp = villagerXp;
		return this;
	}
	
	public SimpleTradeBuilder defaultMultiplier() {
		return this.multiplier(0.05F);
	}
	
	public SimpleTradeBuilder toolMultiplier() {
		return this.multiplier(0.2F);
	}
	
	public SimpleTradeBuilder multiplier(float priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
		return this;
	}
	
	public SimpleTradeBuilder defaultValues(int level) {
		return this.defaultMaxUses().defaultVillagerXp(level).defaultMultiplier();
	}
	
	public ItemListing build() {
		return new Trade(this.cost, this.secondCost, this.result, this.maxUses, this.villagerXp, this.priceMultiplier);
	}
	
	public ItemListing defaultBuild(int level) {
		return this.defaultValues(level).build();
	}
	
}
