package net.luis.xsurvive.world.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class EnchantedGoldenBookItem extends Item {

	public EnchantedGoldenBookItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag tooltip) {
		super.appendHoverText(stack, level, components, tooltip);
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if (!enchantments.isEmpty()) {
			Enchantment enchantment = this.getEnchantment(stack);
			if (enchantment != null) {
				components.add(Component.translatable(enchantment.getDescriptionId()).withStyle(ChatFormatting.DARK_PURPLE));
			}
		}
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
		if (tab == XSurvive.GOLDEN_BOOK_TAB || tab == CreativeModeTab.TAB_SEARCH) {
			for (Enchantment enchantment : Registry.ENCHANTMENT.stream().toList()) {
				if (enchantment instanceof IEnchantment ench) {
					if (ench.isAllowedOnGoldenBooks()) {
						ItemStack stack = new ItemStack(this);
						this.setEnchantment(stack, enchantment);
						stacks.add(stack);
					}
				} else {
					XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
				}
			}
		}
	}
	
	@Override
	public int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
		return 1;
	}
	
	@Nullable
	public Enchantment getEnchantment(ItemStack stack) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
			if (!enchantments.isEmpty() && enchantments.size() == 1) {
				return enchantments.keySet().stream().toList().get(0);
			}
		}
		return null;
	}
	
	public void setEnchantment(ItemStack stack, Enchantment enchantment) {
		if (stack.getItem() instanceof EnchantedGoldenBookItem) {
			EnchantmentHelper.setEnchantments(Map.of(enchantment, 1), stack);
		}
	}
	
	public static ItemStack createForEnchantment(Enchantment enchantment) {
		ItemStack stack = new ItemStack(XSurviveItems.ENCHANTED_GOLDEN_BOOK.get());
		((EnchantedGoldenBookItem) stack.getItem()).setEnchantment(stack, enchantment);
		return stack;
	}

}
