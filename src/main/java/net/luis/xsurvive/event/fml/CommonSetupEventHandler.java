package net.luis.xsurvive.event.fml;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.dependency.DependencyCallWrapper;
import net.luis.xsurvive.world.entity.ai.custom.*;
import net.luis.xsurvive.world.item.alchemy.BrewingRecipeUtils;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class CommonSetupEventHandler {
	
	@SubscribeEvent
	public static void commonSetup(@NotNull FMLCommonSetupEvent event) {
		DependencyCallWrapper.wrapCommonSetup();
		event.enqueueWork(() -> {
			registerBrewingRecipes();
			registerCustomAis();
			replaceEntityDimensions();
		});
	}
	
	private static void registerBrewingRecipes() {
		BrewingRecipeUtils.addRecipes(Items.SNOWBALL, XSPotions.FROST.get(), XSPotions.LONG_FROST.get(), XSPotions.STRONG_FROST.get());
		BrewingRecipeUtils.addRecipes(Items.WITHER_ROSE, XSPotions.WITHER.get(), XSPotions.LONG_WITHER.get(), XSPotions.STRONG_WITHER.get());
		BrewingRecipeUtils.addRecipes(Items.IRON_PICKAXE, XSPotions.DIG_SPEED.get(), XSPotions.LONG_DIG_SPEED.get(), XSPotions.STRONG_DIG_SPEED.get());
	}
	
	private static void registerCustomAis() {
		CustomAiManager.INSTANCE.register(ElderGuardian.class, CustomElderGuardianAi::new);
		CustomAiManager.INSTANCE.register(EnderMan.class, CustomEnderManAi::new);
		CustomAiManager.INSTANCE.register(ZombifiedPiglin.class, CustomZombifiedPiglinAi::new);
	}
	
	private static void replaceEntityDimensions() {
		EntityType.SHULKER_BULLET.dimensions = EntityDimensions.scalable(0.45F, 0.45F);
	}
}
