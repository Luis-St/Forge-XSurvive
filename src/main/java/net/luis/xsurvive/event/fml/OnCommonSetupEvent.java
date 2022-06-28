package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.network.XSurviveNetworkHandler;
import net.luis.xsurvive.world.item.alchemy.XSurviveBrewingRecipe;
import net.luis.xsurvive.world.item.alchemy.XSurvivePotions;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class OnCommonSetupEvent {
	
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		XSurviveNetworkHandler.init();
		BrewingRecipeRegistry.addRecipe(new XSurviveBrewingRecipe.Builder(Items.SNOWBALL, XSurvivePotions.FORST.get()).useDefaultInput().addTimeUpgrade(XSurvivePotions.LONG_FORST.get())
			.addPowerUpgrade(XSurvivePotions.STRONG_FORST.get()).build());
	}
	
}
