package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * 
 * @author Luis-st
 *
 */

public class BreakingCurseEnchantment extends Enchantment implements WikiFileEntry {

	public BreakingCurseEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public int getMinCost(int level) {
		return 25;
	}
	
	@Override
	public int getMaxCost(int level) {
		return 50;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}

	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("If an Item has this Enchantment the Item will be damaged every 5 seconds,").endLine();
			builder.append("the damage is calculated as follows:").endLine();
			builder.appendFormatted("breakingCurseLevel * 2", WikiFormat.CODE).endLine();
		});
	}
	
}
