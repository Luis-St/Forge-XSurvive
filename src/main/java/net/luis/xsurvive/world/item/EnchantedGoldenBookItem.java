package net.luis.xsurvive.world.item;

import net.luis.xsurvive.wiki.file.WikiFileBuilder;
import net.luis.xsurvive.wiki.file.WikiFileEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luis-st
 *
 */

public class EnchantedGoldenBookItem extends Item implements WikiFileEntry {
	
	public EnchantedGoldenBookItem(Properties properties) {
		super(properties);
	}
	
	public static ItemStack createForEnchantment(Enchantment enchantment) {
		ItemStack stack = new ItemStack(XSItems.ENCHANTED_GOLDEN_BOOK.get());
		((EnchantedGoldenBookItem) stack.getItem()).setEnchantment(stack, enchantment);
		return stack;
	}
	
	@Override
	public boolean isFoil(@NotNull ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean isEnchantable(@NotNull ItemStack stack) {
		return true;
	}
	
	@Override
	public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltip) {
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
			if (enchantments.size() == 1) {
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
	
	@Override
	public void add(@NotNull WikiFileBuilder wikiBuilder) {
		wikiBuilder.lines((builder) -> {
			builder.append("Add enchantment:").endLine();
			builder.append("The golden book can be used to add the enchantment to the item in the anvil for 10 xp.").endLine();
			builder.append("Note: This is not a recommended use case.").endLine();
			builder.append("").endLine();
			builder.append("Increase/upgrade enchantment level:").endLine();
			builder.append("The golden book can be used to increase the enchanting level of an item for 10 xp in the anvil.").endLine();
			builder.append("Note: This can be helpful for very rare enchantments.").endLine();
			builder.append("").endLine();
			builder.append("Increase max enchantment level:").endLine();
			builder.append("The golden book can be used to increase the maximum enchanting level of an enchantment.").endLine();
			builder.append("Use the golden book in the anvil on an item with the maximum vanilla enchantment level.").endLine();
		});
	}
}
