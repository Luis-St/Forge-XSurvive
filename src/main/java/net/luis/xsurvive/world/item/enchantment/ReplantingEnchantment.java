package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

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
	public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
		return false;
	}
	
	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("When a plant block is destroyed by an item with this enchantment, a plant of the lowest age is replanted in the place of the destroyed block.").endLine();
			builder.append("The following blocks are plant blocks:").endLine();
		});
		wikiBuilder.pointList((builder) -> {
			builder.append("Wheat").endLine();
			builder.append("Carrot").endLine();
			builder.append("Potato").endLine();
			builder.append("Beetroot").endLine();
			builder.append("Sweet berries").endLine();
			builder.append("Cocoa beans").endLine();
			builder.append("Nether wart").endLine();
			builder.append("Sugar cane").endLine();
			builder.append("Cactus").endLine();
			builder.append("Bamboo").endLine();
			builder.append("Pumpkin stem").endLine();
			builder.append("Melon stem").endLine();
			builder.append("Honey melon stem").endLine();
		});
	}
}
