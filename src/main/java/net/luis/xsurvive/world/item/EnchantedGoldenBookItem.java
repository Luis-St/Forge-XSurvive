package net.luis.xsurvive.world.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
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

public class EnchantedGoldenBookItem extends Item implements WikiFileEntry {

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
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
		if (tab == XSurvive.GOLDEN_BOOK_TAB || tab == CreativeModeTab.TAB_SEARCH) {
			for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
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
		if (this.getEnchantment(stack) == enchantment) {
			return 1;
		}
		return 0;
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
		ItemStack stack = new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
		((EnchantedGoldenBookItem) stack.getItem()).setEnchantment(stack, enchantment);
		return stack;
	}

	@Override
	public void add(WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("Add enchantment:").endLine();
			builder.append("The Golden Book can be used to add the Enchantment for a cost of 10 xp to the Item in the Anvil.").endLine();
			builder.append("Note: This is not recommend use case").endLine();
			builder.emptyLine();
			builder.append("Increase enchantment level:").endLine();
			builder.append("The Golden Book can be used to increase the Enchantment level on the Item for a cost of 10 xp in the Anvil.").endLine();
			builder.append("Note: This can be helpful for very rare enchantments but this is still not recommend use case").endLine();
			builder.emptyLine();
			builder.append("Increase max enchantment level:").endLine();
			builder.append("The Golden Book can be used to increase the max Enchantment level on a Item.").endLine();
			builder.append("Apply the Golden Book in the Anvil to an Item which has the max vanilla Enchantment level.").endLine();
		});
	}

}
