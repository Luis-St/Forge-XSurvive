package net.luis.xsurvive.world.item.trading.dynamic;

import static net.luis.xsurvive.world.item.trading.dynamic.DynamicTradeHelper.*;

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

/**
 * 
 * @author Luis-st
 *
 */

public class DynamicEnchantedTrades {
	
	public static ItemListing randomEnchantedItem(Item item, int emeralds, int maxUses, int villagerXp, float priceMultiplier) {
		return (villager, rng) -> {
			ItemStack stack = EnchantmentHelper.enchantItem(rng, new ItemStack(item), 5 + rng.nextInt(20), false);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds + getEmeraldCount(stack)), stack, maxUses, villagerXp, priceMultiplier);
		};
	}
		
	public static ItemListing randomEnchantedBook(int villagerLevel) {
		return randomEnchantedBook(0, Integer.MAX_VALUE, villagerLevel);
	}
	
	public static ItemListing randomEnchantedBook(int minLevel, int maxLevel, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			int level = getRandomLevel(rng, enchantment, minLevel, maxLevel, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, level)), new ItemStack(Items.BOOK), stack, 12, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomEnchantedGoldenBook(int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidGoldenEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			if (enchantment instanceof IEnchantment ench) {
				int level = ench.getMaxGoldenBookLevel();
				int emeralds = getGoldenEmeraldCount(rng, level);
				if (64 >= emeralds) {
					return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), new ItemStack(Items.BOOK), EnchantedGoldenBookItem.createForEnchantment(enchantment), 16, getVillagerXp(villagerLevel), 0.02F);
				} else {
					return new MerchantOffer(new ItemStack(Items.EMERALD, 64), new ItemStack(Items.EMERALD, emeralds - 64), EnchantedGoldenBookItem.createForEnchantment(enchantment), 16, getVillagerXp(villagerLevel), 0.02F);
				}
			} else {
				XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return randomEnchantedBook(villagerLevel).getOffer(villager, rng);
			}
		};
	}
	
}
