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
