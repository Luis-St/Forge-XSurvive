package net.luis.xsurvive.world.level.storage.loot;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import static net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments.*;
import net.minecraft.world.item.enchantment.Enchantment;
import static net.minecraft.world.item.enchantment.Enchantments.*;

/**
 * 
 * @author Luis-st
 *
 */

public class LootModifierHelper {
	
	public static List<Enchantment> getTrashEnchantments() {
		return ImmutableList.of(FIRE_PROTECTION, BLAST_PROTECTION, PROJECTILE_PROTECTION, KNOCKBACK);
	}
	
	public static List<Enchantment> getEnchantments() {
		return ImmutableList.of(SHARPNESS, ALL_DAMAGE_PROTECTION, BLOCK_EFFICIENCY, UNBREAKING, PIERCING, SMITE, PUNCH_ARROWS, POWER_ARROWS,
			FISHING_SPEED, BANE_OF_ARTHROPODS, BLASTING.get(), GROWTH.get(), ENDER_SLAYER.get());
	}
	
	public static List<Enchantment> getRareEnchantments() {
		return ImmutableList.of(FALL_PROTECTION, RESPIRATION, DEPTH_STRIDER, SWEEPING_EDGE, FISHING_LUCK, FIRE_ASPECT, QUICK_CHARGE, 
			FROST_ASPECT.get(), POISON_ASPECT.get());
	}
	
	public static List<Enchantment> getVeryRareEnchantments() {
		return ImmutableList.of(BLOCK_FORTUNE, MOB_LOOTING, LOYALTY, RIPTIDE);
	}
	
	public static List<Enchantment> getEndVeryRareEnchantments() {
		return ImmutableList.<Enchantment>builder().addAll(getVeryRareEnchantments()).add(VOID_PROTECTION.get()).build();
	}
	
	public static List<Enchantment> getTreasureEnchantments() {
		return Lists.newArrayList(SWIFT_SNEAK, MULTI_DROP.get());
	}
	
	public static List<Enchantment> getNetherTreasureEnchantments() {
		return ImmutableList.<Enchantment>builder().addAll(getTreasureEnchantments()).add(SOUL_SPEED).build();
	}
	
}
