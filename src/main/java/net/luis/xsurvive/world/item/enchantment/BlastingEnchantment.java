package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class BlastingEnchantment extends Enchantment implements WikiFileEntry {

	public BlastingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
	
	@Override
	public int getMinCost(int level) {
		return 1 + 10 * (level - 1);
	}
	
	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 50;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment == Enchantments.SILK_TOUCH) {
			return false;
		} else if (enchantment == XSurviveEnchantments.SMELTING.get()) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a Block is destroyed by an Item with this Enchantment,").endLine();
			builder.append("an explosion occurs at the position of the destroyed Block.").endLine();
			builder.append("The strength of the explosion is calculated as follows:").endLine();
			builder.appendFormatted("2 * (blastingLevel + 1)", WikiFormat.CODE).endLine();
		});
	}
	
}
