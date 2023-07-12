package net.luis.xsurvive.world.item.enchantment;

import net.luis.xores.world.item.ElytraChestplateItem;
import net.luis.xsurvive.wiki.WikiFormat;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class VoidProtectionEnchantment extends Enchantment implements IEnchantment, WikiFileEntry {
	
	public VoidProtectionEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
		super(rarity, category, slots);
	}
	
	@Override
	public int getMaxLevel() {
		return 4;
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
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		if (enchantment instanceof ProtectionEnchantment protectionEnchantment) {
			return protectionEnchantment.type == ProtectionEnchantment.Type.ALL;
		} else if (enchantment == Enchantments.THORNS) {
			return false;
		}
		return super.checkCompatibility(enchantment);
	}
	
	@Override
	public boolean canEnchant(@NotNull ItemStack stack) {
		if (stack.getItem() instanceof ElytraItem) {
			return true;
		} else if (stack.getItem() instanceof ElytraChestplateItem) {
			return true;
		}
		return super.canEnchant(stack);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean isTradeable() {
		return false;
	}
	
	@Override
	public boolean isDiscoverable() {
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
	public int getMaxGoldenBookLevel() {
		return 5;
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
	public void add(@NotNull WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("If a entity wears an elytra with this enchantment and it takes void damage, the damage is reduced.").endLine();
			builder.append("Damage reduction is calculated as follows:").endLine();
			builder.appendFormatted("1 - (VoidProtectionLevel * 0,2)", WikiFormat.CODE).endLine();
		});
	}
}
