package net.luis.xsurvive.world.item.trading;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class DynamicTrades {
	
	public static ItemListing randomEnchantedBook(List<Enchantment> enchantments, int level, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(enchantments), rng);
			int l = getLevel(rng, enchantment, level, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, l));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, l)), new ItemStack(Items.BOOK), stack, 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomEnchantedBook(List<Enchantment> enchantments, int minLevel, int maxLevel, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(enchantments), rng);
			int level = getRandomLevel(rng, enchantment, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, level)), new ItemStack(Items.BOOK), stack, 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomEnchantedBook(List<Enchantment> enchantments, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(enchantments), rng);
			int level = getRandomLevel(rng, enchantment, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, level)), new ItemStack(Items.BOOK), stack, 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomEnchantedBook(int level, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			int l = getLevel(rng, enchantment, level, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, l));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, l)), new ItemStack(Items.BOOK), stack, 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}

	public static ItemListing randomEnchantedBook(int minLevel, int maxLevel, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			int level = getRandomLevel(rng, enchantment, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, level)), new ItemStack(Items.BOOK), stack, 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomEnchantedBook(int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			int level = getRandomLevel(rng, enchantment, villagerLevel);
			ItemStack stack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
			return new MerchantOffer(new ItemStack(Items.EMERALD, getEmeraldCount(rng, level)), new ItemStack(Items.BOOK), stack, 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomEnchantedGoldenBook(List<Enchantment> enchantments, int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidGoldenEnchantments(enchantments), rng);
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
	
	public static ItemListing randomEnchantedGoldenBook(int villagerLevel) {
		return (villager, rng) -> {
			Enchantment enchantment = random(getValidGoldenEnchantments(ForgeRegistries.ENCHANTMENTS.getValues()), rng);
			if (enchantment instanceof IEnchantment ench) {
				int level = ench.getMaxGoldenBookLevel();
				int emeralds = getGoldenEmeraldCount(rng, level);
				if (64 >= emeralds) {
					return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), ItemStack.EMPTY, EnchantedGoldenBookItem.createForEnchantment(enchantment), 16, getVillagerXp(villagerLevel), 0.02F);
				} else {
					return new MerchantOffer(new ItemStack(Items.EMERALD, 64), new ItemStack(Items.EMERALD, emeralds - 64), EnchantedGoldenBookItem.createForEnchantment(enchantment), 16, getVillagerXp(villagerLevel), 0.02F);
				}
			} else {
				XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				return randomEnchantedBook(villagerLevel).getOffer(villager, rng);
			}
		};
	}
	
	public static ItemListing randomPotion(int emeralds, List<Potion> potions, int villagerLevel) {
		return (villager, rng) -> {
			Potion potion = random(potions, rng);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), PotionUtils.setPotion(new ItemStack(Items.POTION), potion), 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomPotion(int emeralds, int villagerLevel) {
		return (villager, rng) -> {
			Potion potion = random(Lists.newArrayList(ForgeRegistries.POTIONS.getValues()), rng);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), PotionUtils.setPotion(new ItemStack(Items.POTION), potion), 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	public static ItemListing randomPotion(int villagerLevel) {
		return (villager, rng) -> {
			Potion potion = random(Lists.newArrayList(ForgeRegistries.POTIONS.getValues()), rng);
			int emeralds = Math.min(getEmeraldCount(rng, potion.getEffects()), 64);
			return new MerchantOffer(new ItemStack(Items.EMERALD, emeralds), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), PotionUtils.setPotion(new ItemStack(Items.POTION), potion), 16, getVillagerXp(villagerLevel), 0.2F);
		};
	}
	
	private static List<Enchantment> getValidEnchantments(Collection<Enchantment> enchantments) {
		return enchantments.stream().filter(Enchantment::isTradeable).filter((enchantment) -> {
			return !enchantment.isCurse();
		}).collect(Collectors.toList());
	}
	
	private static List<Enchantment> getValidGoldenEnchantments(Collection<Enchantment> enchantments) {
		return enchantments.stream().filter(Enchantment::isTradeable).filter((enchantment) -> {
			return !enchantment.isCurse();
		}).filter((enchantment) -> {
			if (enchantment instanceof IEnchantment ench) {
				return ench.isAllowedOnGoldenBooks();
			}
			XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			return false;
		}).collect(Collectors.toList());
	}
	
	private static int modifyLevel(RandomSource rng, Enchantment enchantment, int level, int villagerLevel) {
		int minLevel = enchantment.getMinLevel();
		int maxLevel = enchantment.getMaxLevel();
		if (minLevel == maxLevel) {
			return level;
		} else if (level == maxLevel) {
			return level;
		} else {
			double d = rng.nextDouble();
			return switch (villagerLevel) {
				case 2 -> d >= 0.75 ? Mth.clamp(level + 1, minLevel, maxLevel) : level;
				case 3 -> d >= 0.5 ? Mth.clamp(level + 1, minLevel, maxLevel) : level;
				case 4 -> {
					if (level == minLevel) {
						yield Mth.clamp(level + rng.nextInt(3), minLevel, maxLevel);
					} else if (d >= 0.75) {
						yield Mth.clamp(level + 2, minLevel, maxLevel);
					}
					yield d >= 0.25 ? Mth.clamp(level + 1, minLevel, maxLevel) : level;
				}
				case 5 -> {
					if (level == minLevel) {
						yield Mth.clamp(level + rng.nextInt(4), minLevel, maxLevel);
					} else if (d >= 0.75) {
						yield Mth.clamp(level + 3, minLevel, maxLevel);
					}
					yield d >= 0.5 ? Mth.clamp(level + 2, minLevel, maxLevel) : Mth.clamp(level + 1, minLevel, maxLevel);
				}
				default -> level;
			};
		}
	}
	
	private static int getLevel(RandomSource rng, Enchantment enchantment, int level, int villagerLevel) {
		level = Mth.clamp(level, enchantment.getMinLevel(), enchantment.getMaxLevel());
		return modifyLevel(rng, enchantment, level, villagerLevel);
	}
	
	private static int getRandomLevel(RandomSource rng, Enchantment enchantment, int villagerLevel) {
		int level = Mth.randomBetweenInclusive(rng, enchantment.getMinLevel(), enchantment.getMaxLevel());
		return modifyLevel(rng, enchantment, level, villagerLevel);
	}
	
	private static int getEmeraldCount(RandomSource rng, int level) {
		return Math.min(2 + rng.nextInt(5 + level * 10) + 3 * level + 5, 64);
	}
	
	private static int getGoldenEmeraldCount(RandomSource rng, int level) {
		return Math.min(2 + rng.nextInt(5 + level * 10) + 4 * level + 5, 128);
	}
	
	private static int getEmeraldCount(RandomSource rng, List<MobEffectInstance> effects) {
		int emeralds = 1;
		for (MobEffectInstance effect : effects) {
			emeralds += effect.getAmplifier() + 1;
		}
		return getEmeraldCount(rng, emeralds);
	}
	
	private static int getVillagerXp(int villagerLevel) {
		return Trade.VILLAGER_XP[villagerLevel - 1];
	}
	
	private static <T> T random(List<T> list, RandomSource rng) {
		return list.get(rng.nextInt(list.size()));
	}
	
}
