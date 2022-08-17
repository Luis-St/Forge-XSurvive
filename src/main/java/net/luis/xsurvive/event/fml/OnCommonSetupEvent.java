package net.luis.xsurvive.event.fml;

import net.luis.xbackpack.BackpackConstans;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.alchemy.XSBrewingRecipe;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomAiManager;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomElderGuardianAi;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomEnderManAi;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomZombifiedPiglinAi;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class OnCommonSetupEvent {
	
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		BackpackConstans.FURNACE_RECIPE_TYPES.add(XSRecipeTypes.SMELTING.get());
		registerBrewingRecipies();
		registerCustomAis();
	}
	
	private static void registerBrewingRecipies() {
		BrewingRecipeRegistry.addRecipe(new XSBrewingRecipe.Builder(Items.SNOWBALL, XSPotions.FORST.get()).useDefaultInput().addTimeUpgrade(XSPotions.LONG_FORST.get()).addPowerUpgrade(XSPotions.STRONG_FORST.get()).build());
		BrewingRecipeRegistry.addRecipe(new XSBrewingRecipe.Builder(Items.WITHER_ROSE, XSPotions.WITHER.get()).useDefaultInput().addTimeUpgrade(XSPotions.LONG_WITHER.get()).addPowerUpgrade(XSPotions.STRONG_WITHER.get()).build());
		BrewingRecipeRegistry.addRecipe(new XSBrewingRecipe.Builder(Items.IRON_PICKAXE, XSPotions.DIG_SPEED.get()).useDefaultInput().addTimeUpgrade(XSPotions.LONG_DIG_SPEED.get()).addPowerUpgrade(XSPotions.STRONG_DIG_SPEED.get()).build());
	}
	
	private static void registerCustomAis() {
		CustomAiManager.INSTANCE.register(ElderGuardian.class, CustomElderGuardianAi::new);
		CustomAiManager.INSTANCE.register(EnderMan.class, CustomEnderManAi::new);
		CustomAiManager.INSTANCE.register(ZombifiedPiglin.class, CustomZombifiedPiglinAi::new);
	}
	
}
