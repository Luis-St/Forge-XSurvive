package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;

public class PoisonAspectEnchantment extends FireAspectEnchantment implements WikiFileEntry {

	public PoisonAspectEnchantment(Rarity rarity, EquipmentSlot... slots) {
		super(rarity, slots);
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When an Entity is hit by an Item with this Enchantment, the hit Entity gets the Potion Effect,").endLine();
			builder.append("the duration of the Effect is calculated as follows:").endLine();
			builder.appendFormatted("100 * poisonAspectLevel", WikiFormat.CODE).endLine();
		});
	}
	
}
