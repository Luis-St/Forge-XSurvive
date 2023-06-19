package net.luis.xsurvive.world.item.trading;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public class Trade implements ItemListing {
	
	public static final int[] VILLAGER_XP = new int[]{2, 10, 20, 30, 40};
	
	private final ItemStack cost;
	private final ItemStack secondCost;
	private final ItemStack result;
	private final int maxUses;
	private final int villagerXp;
	private final float priceMultiplier;
	
	public Trade(ItemStack cost, ItemStack secondCost, ItemStack result, int maxUses, int villagerXp, float priceMultiplier) {
		this.cost = Objects.requireNonNull(cost);
		this.secondCost = Objects.requireNonNull(secondCost);
		this.result = Objects.requireNonNull(result);
		this.maxUses = maxUses;
		this.villagerXp = villagerXp;
		this.priceMultiplier = priceMultiplier;
	}
	
	public ItemStack getCost() {
		return this.cost;
	}
	
	public ItemStack getSecondCost() {
		return this.secondCost;
	}
	
	public ItemStack getResult() {
		return this.result;
	}
	
	public int getMaxUses() {
		return this.maxUses;
	}
	
	public int getVillagerXp() {
		return this.villagerXp;
	}
	
	public float getPriceMultiplier() {
		return this.priceMultiplier;
	}
	
	@Override
	public MerchantOffer getOffer(@NotNull Entity villager, @NotNull RandomSource rng) {
		return new MerchantOffer(this.cost, this.secondCost, this.result, this.maxUses, this.villagerXp, this.priceMultiplier);
	}
}
