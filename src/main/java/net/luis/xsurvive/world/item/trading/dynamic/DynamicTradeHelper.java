package net.luis.xsurvive.world.item.trading.dynamic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.trading.Trade;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

class DynamicTradeHelper {
	
	static List<Enchantment> getValidEnchantments(Collection<Enchantment> enchantments) {
		return enchantments.stream().filter((enchantment) -> {
			return enchantment.isTradeable() && !enchantment.isCurse() && !enchantment.isTreasureOnly();
		}).collect(Collectors.toList());
	}
	
	static List<Enchantment> getValidGoldenEnchantments(Collection<Enchantment> enchantments) {
		return enchantments.stream().filter((enchantment) -> {
			return enchantment.isTradeable() && !enchantment.isCurse() && !enchantment.isTreasureOnly();
		}).filter((enchantment) -> {
			if (enchantment instanceof IEnchantment ench) {
				return ench.isAllowedOnGoldenBooks();
			}
			XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			return false;
		}).collect(Collectors.toList());
	}
	
	static int clampLevel(RandomSource rng, Enchantment enchantment, int minLevel, int maxLevel) {
		int level = Mth.randomBetweenInclusive(rng, minLevel, maxLevel);
		if (level > enchantment.getMaxLevel()) {
			return enchantment.getMaxLevel();
		} else if (enchantment.getMinLevel() > level) {
			return enchantment.getMinLevel();
		}
		return level;
	}
	
	static int modifyLevel(RandomSource rng, Enchantment enchantment, int level, int villagerLevel) {
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
						yield Mth.clamp(level + rng.nextInt(2), minLevel, maxLevel);
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
	
	static int getRandomLevel(RandomSource rng, Enchantment enchantment, int minLevel, int maxLevel, int villagerLevel) {
		return modifyLevel(rng, enchantment, clampLevel(rng, enchantment, minLevel, maxLevel), villagerLevel);
	}
	
	static int getEmeraldCount(RandomSource rng, int level) {
		return Math.min(2 + rng.nextInt(5 + level * 10) + 3 * level + 5, 64);
	}
	
	static int getGoldenEmeraldCount(RandomSource rng, int level) {
		return Math.min(2 + rng.nextInt(5 + level * 10) + 4 * level + 5, 128);
	}
	
	static int getEmeraldCount(RandomSource rng, List<MobEffectInstance> effects) {
		int emeralds = 1;
		for (MobEffectInstance effect : effects) {
			emeralds += effect.getAmplifier() + 1;
		}
		return getEmeraldCount(rng, emeralds);
	}
	
	static int getEmeraldCount(RandomSource rng, ItemStack stack) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		int emeralds = enchantments.size();
		for (Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			Enchantment enchantment = entry.getKey();
			if (enchantment.getRarity().ordinal() > 1) {
				if (enchantment.getRarity().ordinal() > 2) {
					emeralds += entry.getValue() + entry.getValue();
				} else {
					emeralds += entry.getValue() + (entry.getValue() / 2);
				}
			} else {
				emeralds += entry.getValue();
			}
		}
		return Math.min(emeralds, 64);
	}
	
	static int getVillagerXp(int villagerLevel) {
		return Trade.VILLAGER_XP[villagerLevel - 1];
	}
	
	static <T> T random(List<T> list, RandomSource rng) {
		return list.get(rng.nextInt(list.size()));
	}
	
}
