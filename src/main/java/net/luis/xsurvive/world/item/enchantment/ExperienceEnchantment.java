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

public class ExperienceEnchantment extends Enchantment implements IEnchantment, WikiFileEntry {

	public ExperienceEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
	}
	
	public int getMinCost(int level) {
		return 15 + (level - 1) * 9;
	}

	public int getMaxCost(int level) {
		return this.getMinCost(level) + 50;
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		return true;
	}

	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a Block is destroyed by an Item with this Enchantment,").endLine();
			builder.append("the experience dropped by the Block will be modified as follows: ").endLine();
			builder.appendFormatted("(experience * ((experienceLevel + 1) * ((experienceLevel * 2) + fortuneLevel))) * (multiDropLevel + 1)", WikiFormat.CODE).endLine();
			builder.endLine();
			builder.append("When an Entity is killed by an Item with this Enchantment,").endLine();
			builder.append("the experience dropped by the Entity will be modified as follows: ").endLine();
			builder.appendFormatted("experience * ((experienceLevel + 1) * ((experienceLevel * 2) + lootingLevel))", WikiFormat.CODE).endLine();
		});
	}
	
}
