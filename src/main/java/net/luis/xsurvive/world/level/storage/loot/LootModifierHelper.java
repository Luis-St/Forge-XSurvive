package net.luis.xsurvive.world.level.storage.loot;

import static net.luis.xsurvive.world.item.XSItems.BLACK_RUNE;
import static net.luis.xsurvive.world.item.XSItems.BLUE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.BROWN_RUNE;
import static net.luis.xsurvive.world.item.XSItems.CYAN_RUNE;
import static net.luis.xsurvive.world.item.XSItems.GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSItems.GREEN_RUNE;
import static net.luis.xsurvive.world.item.XSItems.LIGHT_BLUE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.LIGHT_GRAY_RUNE;
import static net.luis.xsurvive.world.item.XSItems.LIME_RUNE;
import static net.luis.xsurvive.world.item.XSItems.MAGENTA_RUNE;
import static net.luis.xsurvive.world.item.XSItems.ORANGE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.PINK_RUNE;
import static net.luis.xsurvive.world.item.XSItems.PURPLE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.RAINBOW_RUNE;
import static net.luis.xsurvive.world.item.XSItems.RED_RUNE;
import static net.luis.xsurvive.world.item.XSItems.WHITE_RUNE;
import static net.luis.xsurvive.world.item.XSItems.YELLOW_RUNE;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.ASPECT_OF_THE_END;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.BLASTING;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.ENDER_SLAYER;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.FROST_ASPECT;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.GROWTH;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.MULTI_DROP;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.POISON_ASPECT;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.VOID_PROTECTION;
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
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(VOID_PROTECTION.get(), ASPECT_OF_THE_END.get()));
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
