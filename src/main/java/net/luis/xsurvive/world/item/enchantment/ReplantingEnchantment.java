package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 *
 * @author Luis-st
 *
 */

public class ReplantingEnchantment extends Enchantment implements WikiFileEntry {


	public ReplantingEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public int getMinCost(int level) {
		return 20;
	}
	
	@Override
	public int getMaxCost(int level) {
		return 50;
	}
	
	@Override
	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof HoeItem;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a Plant block is destroyed by an Item with this Enchantment,").endLine();
			builder.append("at the position of the destroyed Block a Plant will be replanted with  the lowest age.").endLine();
			builder.append("The following Blocks are Plant blocks:").endLine();
			builder.append("Wheat, Carrot, Potato, Beetroot, Sweet Berries, Cocoa Beans, Nether Wart, Sugar Cane,").endLine();
			builder.append("Cactus, Bamboo, Pumpkin-, Melon- and Honey Melon stem").endLine();
		});
	}
	
}
