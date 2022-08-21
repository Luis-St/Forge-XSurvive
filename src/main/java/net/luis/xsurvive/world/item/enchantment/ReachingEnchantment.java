package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 *
 * @author Luis-st
 *
 */

public class ReachingEnchantment extends Enchantment implements IEnchantment, WikiFileEntry {
	
	public ReachingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public int getMinCost(int level) {
		return super.getMinCost(level) + 20;
	}
	
	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 50;
	}
	
	@Override
	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof TieredItem;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isDiscoverable() {
		return false;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean isAllowedOnGoldenBooks() {
		return true;
	}
	
	@Override
	public int getMinUpgradeLevel() {
		return this.getMinLevel();
	}
	
	@Override
	public int getMaxUpgradeLevel() {
		return this.getMaxLevel();
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a this Enchantment is applied on a Sword or a Tool,").endLine();
			builder.append("the reach and attack range will be increased by a half Block per level").endLine();
		});
	}
	
}
