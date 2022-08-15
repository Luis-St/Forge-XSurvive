package net.luis.xsurvive.client.gui.screens.recipebook;

import java.util.Set;
import java.util.stream.Collectors;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

public class SmeltingRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
	
	private static final Component FILTER_NAME = Component.translatable(XSurvive.MOD_ID + ".gui.recipebook.toggleRecipes.smeltable");
	
	@Override
	protected Component getRecipeFilterName() {
		return FILTER_NAME;
	}
	
	@Override
	protected Set<Item> getFuelItems() {
		return ForgeRegistries.ITEMS.getValues().stream().filter((item) -> {
			return item.getBurnTime(new ItemStack(item), XSRecipeTypes.SMELTING.get()) > 0;
		}).collect(Collectors.toSet());
	}

}
