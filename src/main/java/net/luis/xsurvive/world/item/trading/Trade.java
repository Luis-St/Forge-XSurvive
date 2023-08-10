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
 * @author Luis-St
 *
 */

public record Trade(ItemStack getCost, ItemStack getSecondCost, ItemStack getResult, int getMaxUses, int getVillagerXp, float getPriceMultiplier) implements ItemListing {
	
	public static final int[] VILLAGER_XP = {
		2, 10, 20, 30, 40
	};
	
	public Trade {
		Objects.requireNonNull(getCost);
		Objects.requireNonNull(getSecondCost);
		Objects.requireNonNull(getResult);
	}
	
	@Override
	public MerchantOffer getOffer(@NotNull Entity villager, @NotNull RandomSource rng) {
		return new MerchantOffer(this.getCost, this.getSecondCost, this.getResult, this.getMaxUses, this.getVillagerXp, this.getPriceMultiplier);
	}
}
