package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

/**
 * 
 * @author Luis-st
 *
 */

public class VoidWalkerEnchantment extends Enchantment implements WikiFileEntry {

	public VoidWalkerEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public int getMinCost(int level) {
		return 20;
	}
	
	@Override
	public int getMaxCost(int level) {
		return 50;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment == Enchantments.DEPTH_STRIDER) {
			return false;
		} else if (enchantment == Enchantments.FROST_WALKER) {
			return false;
		}	
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isDiscoverable() {
		return false;
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("If an Entity wears boots with this Enchantment, this Entity is be able to fly.").endLine();
			builder.append("If a player is sneaking while flying the he will slowly float down.").endLine();
		});
	}

}
