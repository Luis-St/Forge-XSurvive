package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;

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
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		if (enchantment instanceof FireAspectEnchantment) {
			return enchantment != Enchantments.FIRE_ASPECT;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When an entity is struck by an item with this enchantment, the struck entity gains the frost effect, the duration of the effect calculated as follows:").endLine();
			builder.appendFormatted("100 * FrostAspectLevel", WikiFormat.CODE).endLine();
		});
	}
}
