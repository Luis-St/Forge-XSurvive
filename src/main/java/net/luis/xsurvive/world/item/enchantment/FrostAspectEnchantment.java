package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;

/**
 * 
 * @author Luis-st
 *
 */

public class FrostAspectEnchantment extends FireAspectEnchantment implements WikiFileEntry {
	
	public FrostAspectEnchantment(Rarity rarity, EquipmentSlot... slots) {
		super(rarity, slots);
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment instanceof FireAspectEnchantment) {
			return enchantment != Enchantments.FIRE_ASPECT;
		}
		return super.checkCompatibility(enchantment);
	}

	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When an Entity is hit by an Item with this Enchantment, the hit Entity gets the Forst Effect,").endLine();
			builder.append("the duration of the Effect is calculated as follows:").endLine();
			builder.appendFormatted("100 * frostAspectLevel", WikiFormat.CODE).endLine();
		});
	}
	
}
