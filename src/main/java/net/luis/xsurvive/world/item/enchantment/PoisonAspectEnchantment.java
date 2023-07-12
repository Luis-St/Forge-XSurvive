package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class PoisonAspectEnchantment extends FireAspectEnchantment implements WikiFileEntry {
	
	public PoisonAspectEnchantment(Rarity rarity, EquipmentSlot... slots) {
		super(rarity, slots);
	}
	
	@Override
	public void add(@NotNull WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When an entity is struck by an item with this enchantment, the struck entity receives the potion effect, with the duration of the effect calculated as follows:").endLine();
			builder.appendFormatted("100 * PoisonAspectLevel", WikiFormat.CODE).endLine();
		});
	}
}
