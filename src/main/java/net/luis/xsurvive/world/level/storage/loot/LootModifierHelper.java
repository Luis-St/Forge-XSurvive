package net.luis.xsurvive.world.level.storage.loot;

import com.google.common.collect.Lists;
import net.luis.xsurvive.util.Rarity;
import net.luis.xsurvive.util.RarityList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import static net.luis.xsurvive.world.item.XSItems.*;
import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.*;
import static net.minecraft.world.item.enchantment.Enchantments.*;

/**
 *
 * @author Luis-St
 *
 */

public class LootModifierHelper {
	
	public static @NotNull RarityList<Enchantment> getCommonEnchantments() {
		return RarityList.of(Rarity.COMMON, Lists.newArrayList(FIRE_PROTECTION, BLAST_PROTECTION, PROJECTILE_PROTECTION, BANE_OF_ARTHROPODS, PUNCH_ARROWS, KNOCKBACK, BLASTING.get()));
	}
	
	public static @NotNull RarityList<Enchantment> getRareEnchantments() {
		return RarityList.of(Rarity.RARE, Lists.newArrayList(ALL_DAMAGE_PROTECTION, SHARPNESS, SMITE, ENDER_SLAYER.get(), BLOCK_EFFICIENCY, POWER_ARROWS, GROWTH.get(), PIERCING, FIRE_ASPECT, FROST_ASPECT.get(), POISON_ASPECT.get()));
	}
	
	public static @NotNull RarityList<Enchantment> getVeryRareEnchantments() {
		return RarityList.of(Rarity.VERY_RARE, Lists.newArrayList(FALL_PROTECTION, RESPIRATION, DEPTH_STRIDER, SWEEPING_EDGE, UNBREAKING, BLOCK_FORTUNE, MOB_LOOTING, LOYALTY, RIPTIDE, QUICK_CHARGE, FISHING_LUCK, FISHING_SPEED, REPLANTING.get()));
	}
	
	public static @NotNull RarityList<Enchantment> getTreasureEnchantments() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(MULTI_DROP.get(), REACHING.get()));
	}
	
	public static @NotNull RarityList<Enchantment> getExtraOverworldTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(SWIFT_SNEAK));
	}
	
	public static @NotNull RarityList<Enchantment> getExtraNetherTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(SOUL_SPEED));
	}
	
	public static @NotNull RarityList<Enchantment> getExtraEndTreasure() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(VOID_WALKER.get(), VOID_PROTECTION.get(), ASPECT_OF_THE_END.get()));
	}
	
	public static @NotNull RarityList<Enchantment> getAllTreasureEnchantments() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(MULTI_DROP.get(), SWIFT_SNEAK, SOUL_SPEED, VOID_PROTECTION.get(), ASPECT_OF_THE_END.get()));
	}
	
	public static @NotNull RarityList<Item> getCommonRunes() {
		return RarityList.of(Rarity.COMMON, Lists.newArrayList(WHITE_RUNE.get(), GRAY_RUNE.get(), LIGHT_GRAY_RUNE.get(), BROWN_RUNE.get(), BLACK_RUNE.get()));
	}
	
	public static @NotNull RarityList<Item> getRareRunes() {
		return RarityList.of(Rarity.RARE, Lists.newArrayList(ORANGE_RUNE.get(), MAGENTA_RUNE.get(), LIGHT_BLUE_RUNE.get(), YELLOW_RUNE.get(), LIME_RUNE.get(), PINK_RUNE.get(), CYAN_RUNE.get(), PURPLE_RUNE.get(), BLUE_RUNE.get(), GREEN_RUNE.get(), RED_RUNE.get()));
	}
	
	public static @NotNull RarityList<Item> getTreasureRunes() {
		return RarityList.of(Rarity.TREASURE, Lists.newArrayList(RAINBOW_RUNE.get()));
	}
}
