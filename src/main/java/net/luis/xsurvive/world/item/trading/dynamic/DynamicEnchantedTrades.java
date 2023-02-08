package net.luis.xsurvive.world.item.trading.dynamic;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static net.luis.xsurvive.world.item.trading.dynamic.DynamicTradeHelper.*;

/**
 *
 * @author Luis-st
 *
 */

public class DynamicEnchantedTrades {
	
	public static ItemListing randomEnchantedItem(Item item, int emeralds, int maxUses, int villagerXp, float priceMultiplier) {
		return (villager, rng) -> {
			ItemStack stack = EnchantmentHelper.enchantItem(rng, new ItemStack(item), Math.max(emeralds / 2, 1), false);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds + getEmeraldCount(stack)), stack, maxUses, villagerXp, priceMultiplier);
		};
	}
	
	public static ItemListing randomEnchantedBook(int villagerLevel, List<Enchantment.Rarity> allowedRarities) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(ForgeRegistries.ENCHANTMENTS.getValues(), Lists.newArrayList(allowedRarities.isEmpty() ? Lists.newArrayList(Enchantment.Rarity.values()) : allowedRarities)), rng);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, 1));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, enchantment.getMaxLevel())), new ItemStack(Items.BOOK), stack, 1, getVillagerXp(villagerLevel), 0.2F);
			
		};
	}
	
	public static ItemListing randomEnchantedGoldenBook(int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidGoldenEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel()));
			if (enchantment instanceof IEnchantment) {
				return new MerchantOffer(new ItemStack(Items.EMERALD, 64), stack, EnchantedGoldenBookItem.createForEnchantment(enchantment), 1, getVillagerXp(villagerLevel), 0.02F);
			} else {
				XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return randomEnchantedBook(villagerLevel, Lists.newArrayList(Enchantment.Rarity.COMMON, Enchantment.Rarity.UNCOMMON)).getOffer(villager, rng);
			}
		};
	}
	
}
