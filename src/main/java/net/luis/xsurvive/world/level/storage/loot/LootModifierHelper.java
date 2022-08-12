package net.luis.xsurvive.world.level.storage.loot;

import static net.luis.xsurvive.world.item.XSurviveItems.BLACK_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.BLUE_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.BROWN_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.CYAN_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.GREEN_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.LIGHT_BLUE_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.LIGHT_GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.LIME_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.MAGENTA_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.ORANGE_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.PINK_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.PURPLE_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.RAINBOW_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.RED_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.WHITE_RUNE;
import static net.luis.xsurvive.world.item.XSurviveItems.YELLOW_RUNE;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.BLASTING;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.ENDER_SLAYER;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.FROST_ASPECT;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.GROWTH;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.MULTI_DROP;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.POISON_ASPECT;
import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.VOID_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.ALL_DAMAGE_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.BANE_OF_ARTHROPODS;
import static net.minecraft.world.item.enchantment.Enchantments.BLAST_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.BLOCK_EFFICIENCY;
import static net.minecraft.world.item.enchantment.Enchantments.BLOCK_FORTUNE;
import static net.minecraft.world.item.enchantment.Enchantments.DEPTH_STRIDER;
import static net.minecraft.world.item.enchantment.Enchantments.FALL_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.FIRE_ASPECT;
import static net.minecraft.world.item.enchantment.Enchantments.FIRE_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.FISHING_LUCK;
import static net.minecraft.world.item.enchantment.Enchantments.FISHING_SPEED;
import static net.minecraft.world.item.enchantment.Enchantments.KNOCKBACK;
import static net.minecraft.world.item.enchantment.Enchantments.LOYALTY;
import static net.minecraft.world.item.enchantment.Enchantments.MOB_LOOTING;
import static net.minecraft.world.item.enchantment.Enchantments.PIERCING;
import static net.minecraft.world.item.enchantment.Enchantments.POWER_ARROWS;
import static net.minecraft.world.item.enchantment.Enchantments.PROJECTILE_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.PUNCH_ARROWS;
import static net.minecraft.world.item.enchantment.Enchantments.QUICK_CHARGE;
import static net.minecraft.world.item.enchantment.Enchantments.RESPIRATION;
import static net.minecraft.world.item.enchantment.Enchantments.RIPTIDE;
import static net.minecraft.world.item.enchantment.Enchantments.SHARPNESS;
import static net.minecraft.world.item.enchantment.Enchantments.SMITE;
import static net.minecraft.world.item.enchantment.Enchantments.SOUL_SPEED;
import static net.minecraft.world.item.enchantment.Enchantments.SWEEPING_EDGE;
import static net.minecraft.world.item.enchantment.Enchantments.SWIFT_SNEAK;
import static net.minecraft.world.item.enchantment.Enchantments.UNBREAKING;

import com.google.common.collect.Lists;

import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.util.RarityList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * 
 * @author Luis-st
 *
 */

public class LootModifierHelper {
	
	public static RarityList<Enchantment> getCommonEnchantments() {
		return RarityList.of(Rarity.COMMON, Lists.newArrayList(FIRE_PROTECTION, BLAST_PROTECTION, PROJECTILE_PROTECTION, BANE_OF_ARTHROPODS, PUNCH_ARROWS, KNOCKBACK, BLASTING.get()));
	}
	
	public static RarityList<Enchantment> getRareEnchantments() {
		return RarityList.of(Rarity.RARE, Lists.newArrayList(ALL_DAMAGE_PROTECTION, SHARPNESS, SMITE, ENDER_SLAYER.get(), BLOCK_EFFICIENCY, POWER_ARROWS, GROWTH.get(), PIERCING, FIRE_ASPECT, FROST_ASPECT.get(), POISON_ASPECT.get()));
	}
	
	public static RarityList<Enchantment> getVeryRareEnchantments() {
		return RarityList.of(Rarity.VERY_RARE, Lists.newArrayList(FALL_PROTECTION, RESPIRATION, DEPTH_STRIDER, SWEEPING_EDGE, UNBREAKING, BLOCK_FORTUNE, MOB_LOOTING, LOYALTY, RIPTIDE, QUICK_CHARGE, FISHING_LUCK, FISHING_SPEED));
	}
	
	public static RarityList<Enchantment> getTreasureEnchantments() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(MULTI_DROP.get()));
	}
	
	public static RarityList<Enchantment> getExtraOverworldTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(SWIFT_SNEAK));
	}
	
	public static RarityList<Enchantment> getExtraNetherTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(SOUL_SPEED));
	}
	
	public static RarityList<Enchantment> getExtraEndTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(VOID_PROTECTION.get()));
	}
	
	public static RarityList<Item> getCommonRunes() {
		return RarityList.of(Rarity.COMMON, Lists.newArrayList(WHITE_RUNE.get(), GRAY_RUNE.get(), LIGHT_GRAY_RUNE.get(), BROWN_RUNE.get(), BLACK_RUNE.get()));
	}
	
	public static RarityList<Item> getRareRunes() {
		return RarityList.of(Rarity.RARE, Lists.newArrayList(ORANGE_RUNE.get(), MAGENTA_RUNE.get(), LIGHT_BLUE_RUNE.get(), YELLOW_RUNE.get(), LIME_RUNE.get(), PINK_RUNE.get(), CYAN_RUNE.get(), PURPLE_RUNE.get(), BLUE_RUNE.get(), GREEN_RUNE.get(), 
			RED_RUNE.get()));
	}
	
	public static RarityList<Item> getTreasureRunes() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(RAINBOW_RUNE.get()));
	}
	
}
