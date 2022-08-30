package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.IGlintColor;
import net.luis.xsurvive.world.item.enchantment.EnchantedItem;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnAnvilEvent {
	
	@SubscribeEvent
	public static void anvilUpdate(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if (!left.isEmpty()) {
			ItemStack result = left.copy();
			if (right.isEmpty()) {
				if ((left.isDamageableItem() || left.isEnchantable() || left.isEnchanted()) && left.getBaseRepairCost() > 0) {
					event.setCost(1);
					result.setRepairCost(result.getBaseRepairCost() / 2);
					event.setOutput(result);
				}
			} else if (left.getItem() instanceof EnchantedGoldenBookItem) {
				event.setCanceled(true);
			} else if ((left.isEnchantable() || left.isEnchanted()) && right.getItem() instanceof EnchantedGoldenBookItem goldenBook) {
				Enchantment enchantment = goldenBook.getEnchantment(right);
				if (enchantment != null && enchantment instanceof IEnchantment ench) {
					EnchantedItem enchantedItem = EnchantedItem.EMPTY;
					if (ench.isUpgradeEnchantment()) {
						enchantedItem = IEnchantment.upgrade(left, right);
					} else {
						enchantedItem = IEnchantment.merge(left, right);
					}
					event.setOutput(enchantedItem.stack());
					event.setCost(enchantedItem.cost());
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				}
			} else if (left.isEnchanted() || left.getItem().isFoil(left)) {
				if (right.getItem() instanceof IGlintColor glintColor && !left.is(Items.ENCHANTED_BOOK)) {
					int color = glintColor.getGlintColor(right);
					if (17 >= color && color >= 0) {
						CompoundTag tag = result.getOrCreateTag();
						if (tag.contains(XSurvive.MOD_NAME)) {
							CompoundTag modTag = tag.getCompound(XSurvive.MOD_NAME);
							tag.remove(XSurvive.MOD_NAME);
							modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
							tag.put(XSurvive.MOD_NAME, modTag);
						} else {
							CompoundTag modTag = new CompoundTag();
							modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
							tag.put(XSurvive.MOD_NAME, modTag);
						}
						result.setTag(tag);
						event.setOutput(result);
						event.setCost(5);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void anvilRepair(AnvilRepairEvent event) {
		if (event.getRight().getItem() instanceof EnchantedGoldenBookItem) {
			event.setBreakChance(0.0F);
		} else {
			event.setBreakChance(0.06F);
		}
	}
	
}
