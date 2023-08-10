package net.luis.xsurvive.world.item.trading.dynamic;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.luis.xsurvive.world.item.trading.dynamic.DynamicTradeHelper.*;

/**
 *
 * @author Luis-St
 *
 */

public class DynamicEnchantedTrades {
	
	public static @NotNull ItemListing randomEnchantedItem(@NotNull Item item, int emeralds, int maxUses, int villagerXp, float priceMultiplier) {
		return (villager, rng) -> {
			ItemStack stack = EnchantmentHelper.enchantItem(rng, new ItemStack(item), Math.max(emeralds / 2, 1), false);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds + getEmeraldCount(stack)), stack, maxUses, villagerXp, priceMultiplier);
		};
	}
	
	public static @NotNull ItemListing randomEnchantedBook(int villagerLevel, @NotNull List<Rarity> rarities) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(rarities), rng);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, 1));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, enchantment.getMaxLevel())), new ItemStack(Items.BOOK), stack, 1, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static @NotNull ItemListing randomEnchantedGoldenBook(int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidGoldenEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel()));
			if (enchantment instanceof IEnchantment) {
				return new MerchantOffer(new ItemStack(Items.EMERALD, 64), stack, EnchantedGoldenBookItem.createForEnchantment(enchantment), 1, getVillagerXp(villagerLevel), 0.02F);
			} else {
				XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return randomEnchantedBook(villagerLevel, Lists.newArrayList(Rarity.COMMON, Rarity.RARE)).getOffer(villager, rng);
			}
		};
	}
}
