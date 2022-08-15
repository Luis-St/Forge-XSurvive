package net.luis.xsurvive.event.fml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import net.luis.xbackpack.BackpackConstans;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.wiki.file.EffectsWikiFile;
import net.luis.xsurvive.wiki.file.EnchantmentWikiFile;
import net.luis.xsurvive.wiki.file.PotionsWikiFile;
import net.luis.xsurvive.world.item.alchemy.XSurviveBrewingRecipe;
import net.luis.xsurvive.world.item.alchemy.XSurvivePotions;
import net.luis.xsurvive.world.item.crafting.XSurviveRecipeTypes;
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
	public static void commonSetup(FMLCommonSetupEvent event) throws IOException {
		BackpackConstans.FURNACE_RECIPE_TYPES.add(XSurviveRecipeTypes.SMELTING.get());
		registerBrewingRecipies();
		registerCustomAis();
		createWikis(new File("D:/Git Repositories/Forge-XSurvive").toPath().resolve("wiki files"));
	}
	
	private static void registerBrewingRecipies() {
		BrewingRecipeRegistry.addRecipe(new XSurviveBrewingRecipe.Builder(Items.SNOWBALL, XSurvivePotions.FORST.get()).useDefaultInput().addTimeUpgrade(XSurvivePotions.LONG_FORST.get())
			.addPowerUpgrade(XSurvivePotions.STRONG_FORST.get()).build());
		BrewingRecipeRegistry.addRecipe(new XSurviveBrewingRecipe.Builder(Items.WITHER_ROSE, XSurvivePotions.WITHER.get()).useDefaultInput().addTimeUpgrade(XSurvivePotions.LONG_WITHER.get())
			.addPowerUpgrade(XSurvivePotions.STRONG_WITHER.get()).build());
		BrewingRecipeRegistry.addRecipe(new XSurviveBrewingRecipe.Builder(Items.IRON_PICKAXE, XSurvivePotions.DIG_SPEED.get()).useDefaultInput().addTimeUpgrade(XSurvivePotions.LONG_DIG_SPEED.get())
			.addPowerUpgrade(XSurvivePotions.STRONG_DIG_SPEED.get()).build());
	}
	
	private static void registerCustomAis() {
		CustomAiManager.INSTANCE.register(ElderGuardian.class, CustomElderGuardianAi::new);
		CustomAiManager.INSTANCE.register(EnderMan.class, CustomEnderManAi::new);
		CustomAiManager.INSTANCE.register(ZombifiedPiglin.class, CustomZombifiedPiglinAi::new);
	}
	
	private static void createWikis(Path path) throws IOException {
		EnchantmentWikiFile.create().write(path);
		EffectsWikiFile.create().write(path);
		PotionsWikiFile.create().write(path);
	}
	
}
