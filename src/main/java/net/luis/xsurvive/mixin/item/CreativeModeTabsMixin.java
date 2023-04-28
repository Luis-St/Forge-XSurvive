package net.luis.xsurvive.mixin.item;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin {
	
	@Inject(method = "generateEnchantmentBookTypesOnlyMaxLevel", at = @At("HEAD"), cancellable = true)
	private static void generateEnchantmentBookTypesOnlyMaxLevel(CreativeModeTab.Output populator, HolderLookup<Enchantment> lookup, Set<EnchantmentCategory> categories, CreativeModeTab.TabVisibility visibility, CallbackInfo callback) {
		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
			if (enchantment.isAllowedOnBooks()) {
				if (enchantment instanceof IEnchantment ench) {
					if (!ench.isUpgradeEnchantment()) {
						for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
							populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)), visibility);
						}
					}
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					XSurvive.LOGGER.info("A deprecate vanilla logic is called");
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
						populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)), visibility);
					}
				}
			}
		}
		callback.cancel();
	}
	
	@Inject(method = "generateEnchantmentBookTypesAllLevels", at = @At("HEAD"), cancellable = true)
	private static void generateEnchantmentBookTypesAllLevels(CreativeModeTab.Output populator, HolderLookup<Enchantment> lookup, Set<EnchantmentCategory> categories, CreativeModeTab.TabVisibility visibility, CallbackInfo callback) {
		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
			if (enchantment.isAllowedOnBooks()) {
				if (enchantment instanceof IEnchantment ench) {
					if (enchantment.allowedInCreativeTab(Items.ENCHANTED_BOOK, categories) && !ench.isUpgradeEnchantment()) {
						populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
					}
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
					XSurvive.LOGGER.info("A deprecate vanilla logic is called");
					if (enchantment.allowedInCreativeTab(Items.ENCHANTED_BOOK, categories)) {
						populator.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
					}
				}
			}
		}
		callback.cancel();
	}
	
}
