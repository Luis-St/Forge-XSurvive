package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.dependency.DependencyCallWrapper;
import net.luis.xsurvive.world.item.alchemy.BrewingRecipeUtils;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomAiManager;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomElderGuardianAi;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomEnderManAi;
import net.luis.xsurvive.world.level.entity.ai.custom.CustomZombifiedPiglinAi;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.Items;
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
public class CommonSetupEventHandler {
	
	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
		DependencyCallWrapper.wrapperXBackpackCommonSetup();
		registerBrewingRecipies();
		registerCustomAis();
	}
	
	private static void registerBrewingRecipies() {
		BrewingRecipeUtils.addRecipes(Items.SNOWBALL, XSPotions.FORST.get(), XSPotions.LONG_FORST.get(), XSPotions.STRONG_FORST.get());
		BrewingRecipeUtils.addRecipes(Items.WITHER_ROSE, XSPotions.WITHER.get(), XSPotions.LONG_WITHER.get(), XSPotions.STRONG_WITHER.get());
		BrewingRecipeUtils.addRecipes(Items.IRON_PICKAXE, XSPotions.DIG_SPEED.get(), XSPotions.LONG_DIG_SPEED.get(), XSPotions.STRONG_DIG_SPEED.get());
	}
	
	private static void registerCustomAis() {
		CustomAiManager.INSTANCE.register(ElderGuardian.class, CustomElderGuardianAi::new);
		CustomAiManager.INSTANCE.register(EnderMan.class, CustomEnderManAi::new);
		CustomAiManager.INSTANCE.register(ZombifiedPiglin.class, CustomZombifiedPiglinAi::new);
	}
	
}
