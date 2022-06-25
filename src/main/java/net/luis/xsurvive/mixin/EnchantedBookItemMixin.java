package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantmentCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

@Mixin(EnchantedBookItem.class)
public abstract class EnchantedBookItemMixin {
	
	@SuppressWarnings("deprecation")
	@Inject(method = "fillItemCategory", at = @At("HEAD"), cancellable = true)
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks, CallbackInfo callback) {
		if (tab == CreativeModeTab.TAB_SEARCH) {
			for (Enchantment enchantment : Registry.ENCHANTMENT) {
				if (enchantment.category != null) {
					if (enchantment instanceof IEnchantment ench) {
						if (!ench.isUpgrade()) {
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
			for (Enchantment enchantment : Registry.ENCHANTMENT) {
				if (enchantment instanceof IEnchantment ench) {
					if (this.isTabForCategory(tab, enchantment) && !ench.isUpgrade()) {
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
		callback.cancel();
	}
	
	protected boolean isTabForCategory(CreativeModeTab tab, Enchantment enchantment) {
		if (enchantment.category == XSurviveEnchantmentCategory.TOOLS && (tab == CreativeModeTab.TAB_COMBAT || tab == CreativeModeTab.TAB_TOOLS)) {
			return true;
		} else if (enchantment.category == XSurviveEnchantmentCategory.WEAPONS && tab == CreativeModeTab.TAB_COMBAT) {
			return true;
		}
		return tab.hasEnchantmentCategory(enchantment.category);
	}
	
}
