package net.luis.xsurvive.client.gui.screens.recipebook;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-st
 *
 */

public class SmeltingRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
	
	private static final Component FILTER_NAME = Component.translatable(XSurvive.MOD_ID + ".gui.recipebook.toggleRecipes.smeltable");
	
	@Override
	protected @NotNull Component getRecipeFilterName() {
		return FILTER_NAME;
	}
	
	@Override
	protected @NotNull Set<Item> getFuelItems() {
		return ForgeRegistries.ITEMS.getValues().stream().filter((item) -> {
			return item.getBurnTime(new ItemStack(item), XSRecipeTypes.SMELTING.get()) > 0;
		}).collect(Collectors.toSet());
	}
}
