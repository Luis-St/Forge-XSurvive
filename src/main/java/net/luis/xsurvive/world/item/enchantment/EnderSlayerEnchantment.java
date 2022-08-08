package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;

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
	public float getDamageBonus(int level, MobType mobType) {
		return 0.0F;
	}

	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When an End Entity is hit by an Item with this Enchantment,").endLine();
			builder.append("the damage will be multiplied by 2.5.").endLine();
			builder.append("End Entities:").append("Ender Man,").append("Endermite and").append("Shulker").endLine();
		});
	}

}
