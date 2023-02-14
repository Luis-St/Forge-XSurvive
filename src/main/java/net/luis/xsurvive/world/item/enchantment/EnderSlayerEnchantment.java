package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class EnderSlayerEnchantment extends DamageEnchantment implements WikiFileEntry {
	
	public EnderSlayerEnchantment(Rarity rarity, EquipmentSlot... slots) {
		super(rarity, -1, slots);
	}
	
	@Override
	public int getMinCost(int level) {
		return 5 + (level - 1) * 5;
	}
	
	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 20;
	}
	
	@Override
	public float getDamageBonus(int level, @NotNull MobType mobType) {
		return 0.0F;
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a entity is hit by an item with this enchantment, the damage is multiplied by 2.5.").endLine();
			builder.append("End Entities:").append(",").append("Endermite and").append("Shulker").endLine();
		});
		wikiBuilder.pointList((builder) -> {
			builder.append("Ender man").endLine();
			builder.append("Endermite").endLine();
		});
	}
	
}
