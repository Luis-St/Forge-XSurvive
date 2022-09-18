package net.luis.xsurvive.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {
	
	@Inject(method = "fillItemCategory", at = @At("HEAD"), cancellable = true)
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks, CallbackInfo callback) {
		if (tab == CreativeModeTab.TAB_SEARCH) {
			for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
				if (enchantment.category != null && enchantment.isAllowedOnBooks()) {
					if (enchantment instanceof IEnchantment ench) {
						if (!ench.isUpgradeEnchantment()) {
							for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
								stacks.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)));
							}
						}
					} else {
						XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
						XSurvive.LOGGER.info("A deprecate vanilla logic is called");
						for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
							stacks.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i)));
						}
					}

				}
			}
		} else if (tab.getEnchantmentCategories().length != 0) {
			for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
				if (enchantment.isAllowedOnBooks()) {
					if (enchantment instanceof IEnchantment ench) {
						if (this.isTabForCategory(tab, enchantment) && !ench.isUpgradeEnchantment()) {
							stacks.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
						}
					} else {
						XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
						XSurvive.LOGGER.info("A deprecate vanilla logic is called");
						if (this.isTabForCategory(tab, enchantment)) {
							stacks.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel())));
						}
					}
				}
			}
		}
		callback.cancel();
	}
	
	private boolean isTabForCategory(CreativeModeTab tab, Enchantment enchantment) {
		if (enchantment.category == XSEnchantmentCategory.HOE && tab == CreativeModeTab.TAB_TOOLS) {
			return true;
		} else if (enchantment.category == XSEnchantmentCategory.TOOLS && tab == CreativeModeTab.TAB_TOOLS) {
			return true;
		} else if (enchantment.category == XSEnchantmentCategory.WEAPONS && tab == CreativeModeTab.TAB_COMBAT) {
			return true;
		} else if (enchantment.category == XSEnchantmentCategory.TOOLS_AND_WEAPONS && (tab == CreativeModeTab.TAB_COMBAT || tab == CreativeModeTab.TAB_TOOLS)) {
			return true;
		} else if (enchantment.category == XSEnchantmentCategory.ELYTRA && tab == CreativeModeTab.TAB_COMBAT) {
			return true;
		}
		return tab.hasEnchantmentCategory(enchantment.category);
	}
	
}
