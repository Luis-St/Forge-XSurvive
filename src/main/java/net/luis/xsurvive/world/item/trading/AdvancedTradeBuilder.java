package net.luis.xsurvive.world.item.trading;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class AdvancedTradeBuilder {
	
	private static final RandomSource RNG = RandomSource.create();
	
	private final ItemStack cost;
	private final ItemStack secondCost;
	private final ItemStack result;
	private int maxUses;
	private int villagerXp;
	private float priceMultiplier;
	
	private AdvancedTradeBuilder(ItemStack cost, ItemStack secondCost, ItemStack result) {
		this.cost = Objects.requireNonNull(cost);
		this.secondCost = Objects.requireNonNull(secondCost);
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
	
	public static @NotNull AdvancedTradeBuilder enchantedBook(@NotNull Enchantment enchantment, int level) {
		int emeralds = Math.min(2 + RNG.nextInt(5 + level * 10) + 3 * level + 5, 64);
		return simple(new ItemStack(Items.EMERALD, emeralds), new ItemStack(Items.BOOK), EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level)));
	}
	
	public static @NotNull AdvancedTradeBuilder enchantedBook(@NotNull Enchantment enchantment, int minLevel, int maxLevel) {
		return enchantedBook(enchantment, Mth.randomBetweenInclusive(RNG, minLevel, maxLevel));
	}
	
	public static @NotNull AdvancedTradeBuilder enchantedGoldenBook(@NotNull Enchantment enchantment) {
		if (enchantment instanceof IEnchantment ench) {
			int level = ench.getMaxGoldenBookLevel();
			int emeralds = Math.min(2 + RNG.nextInt(5 + level * 10) + 4 * level + 5, 128);
			if (64 >= emeralds) {
				return simple(new ItemStack(Items.EMERALD, emeralds), new ItemStack(Items.BOOK), EnchantedGoldenBookItem.createForEnchantment(enchantment));
			} else {
				return simple(new ItemStack(Items.EMERALD, 64), new ItemStack(Items.EMERALD, emeralds - 64), EnchantedGoldenBookItem.createForEnchantment(enchantment));
			}
		} else {
			XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			return enchantedBook(enchantment, enchantment.getMinLevel(), enchantment.getMaxLevel());
		}
	}
	
	public static @NotNull AdvancedTradeBuilder firework(int emeralds, int count, int flightDuration) {
		ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET, count);
		stack.getOrCreateTagElement("Fireworks").putByte("Flight", (byte) flightDuration);
		return simple(new ItemStack(Items.EMERALD, emeralds), ItemStack.EMPTY, stack);
	}
	
	public static @NotNull AdvancedTradeBuilder potion(int emeralds, @NotNull Potion potion) {
		return simple(new ItemStack(Items.EMERALD, emeralds), ItemStack.EMPTY, PotionUtils.setPotion(new ItemStack(Items.POTION), potion));
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
	
	public @NotNull AdvancedTradeBuilder toolMultiplier() {
		return this.multiplier(0.2F);
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
