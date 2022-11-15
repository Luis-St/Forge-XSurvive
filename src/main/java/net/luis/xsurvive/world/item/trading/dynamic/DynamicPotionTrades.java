package net.luis.xsurvive.world.item.trading.dynamic;

import static net.luis.xsurvive.world.item.trading.dynamic.DynamicTradeHelper.*;

import java.util.Collection;

import com.google.common.collect.Lists;

import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

public class DynamicPotionTrades {
	
	public static ItemListing randomPotion(int villagerLevel) {
		return (villager, rng) -> {
			Potion potion = random(Lists.newArrayList(ForgeRegistries.POTIONS.getValues()), rng);
			int emeralds = Math.min(getEmeraldCount(rng, potion.getEffects()), 64);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), PotionUtils.setPotion(new ItemStack(Items.POTION), potion), 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomPotion(int emeralds, int villagerLevel) {
		return randomPotion(emeralds, ForgeRegistries.POTIONS.getValues(), villagerLevel);
	}
	
	public static ItemListing randomPotion(int emeralds, Collection<Potion> potions, int villagerLevel) {
		return (villager, rng) -> {
			Potion potion = random(Lists.newArrayList(potions), rng);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), PotionUtils.setPotion(new ItemStack(Items.POTION), potion), 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
}
