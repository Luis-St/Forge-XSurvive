package net.luis.xsurvive.event.client;

import static net.luis.xsurvive.client.XSurviveRecipeBookCategories.SMELTING_FURNACE_BLOCKS;
import static net.luis.xsurvive.client.XSurviveRecipeBookCategories.SMELTING_FURNACE_MISC;
import static net.luis.xsurvive.client.XSurviveRecipeBookCategories.SMELTING_FURNACE_SEARCH;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.XSurviveRecipeBookTypes;
import net.luis.xsurvive.world.item.crafting.XSurviveRecipeTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnRegisterRecipeBookCategoriesEvent {
	
	@SubscribeEvent
	public static void registerRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
		event.registerBookCategories(XSurviveRecipeBookTypes.SMELTING, Lists.newArrayList(SMELTING_FURNACE_SEARCH, SMELTING_FURNACE_BLOCKS, SMELTING_FURNACE_MISC));
		event.registerAggregateCategory(SMELTING_FURNACE_SEARCH, Lists.newArrayList(SMELTING_FURNACE_BLOCKS, SMELTING_FURNACE_MISC));
		event.registerRecipeCategoryFinder(XSurviveRecipeTypes.SMELTING.get(), (recipe) -> {
			if (recipe.getResultItem().getItem() instanceof BlockItem) {
				return SMELTING_FURNACE_BLOCKS;
			}
			return SMELTING_FURNACE_MISC;
		});
	}
	
}
