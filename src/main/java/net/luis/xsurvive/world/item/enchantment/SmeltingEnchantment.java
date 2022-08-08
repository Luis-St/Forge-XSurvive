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

public class SmeltingEnchantment extends Enchantment implements WikiFileEntry {

	public SmeltingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
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
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment == Enchantments.SILK_TOUCH) {
			return false;
		} else if (enchantment == Enchantments.BLOCK_FORTUNE) {
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
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a Block is destroyed by an Item with this Enchantment,").endLine();
			builder.append("the Block drops it's furnace smelting result.").endLine();
		});
	}

}
