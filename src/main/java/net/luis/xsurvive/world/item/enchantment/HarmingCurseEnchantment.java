package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

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
	public void add(@NotNull WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When an entity is hit by an item with this enchantment, the damage is reflected back to the attacking entity.").endLine();
			builder.append("Reflected damage is calculated as follows:").endLine();
			builder.appendFormatted("(Amount / 2) * HarmingCurseLevel", WikiFormat.CODE).endLine();
		});
	}
}
