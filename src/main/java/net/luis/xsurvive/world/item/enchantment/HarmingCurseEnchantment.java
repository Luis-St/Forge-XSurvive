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

public class HarmingCurseEnchantment extends Enchantment implements WikiFileEntry {
	
	public HarmingCurseEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
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
			builder.append("When a Entity is hit by an Item with this Enchantment,").endLine();
			builder.append("the damage from the hit Entity is reflected back to the attacker.").endLine();
			builder.append("The reflected damage is calculated as follows:").endLine();
			builder.appendFormatted("(amount / 2) * harmingCurseLevel", WikiFormat.CODE).endLine();
		});
	}
	
}
