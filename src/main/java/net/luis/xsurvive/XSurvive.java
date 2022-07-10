package net.luis.xsurvive;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.luis.xsurvive.world.effect.XSurviveMobEffects;
import net.luis.xsurvive.world.inventory.XSurviveMenuTypes;
import net.luis.xsurvive.world.item.XSurviveCreativeModeTab;
import net.luis.xsurvive.world.item.XSurviveItems;
import net.luis.xsurvive.world.item.alchemy.XSurvivePotions;
import net.luis.xsurvive.world.item.crafting.XSurviveRecipeTypes;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.luis.xsurvive.world.level.block.entity.XSurviveBlockEntityTypes;
import net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes;
import net.luis.xsurvive.world.level.entity.npc.XSurviveVillagerProfessions;
import net.luis.xsurvive.world.level.storage.loot.XSurviveGlobalLootModifiers;
import net.luis.xsurvive.world.level.storage.loot.predicates.XSurviveLootItemConditions;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(XSurvive.MOD_ID)
public class XSurvive {
	
	public static final String MOD_ID = "xsurvive";
	public static final String MOD_NAME = "XSurvive";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final CreativeModeTab TAB = new XSurviveCreativeModeTab(XSurvive.MOD_ID);
	
	public XSurvive() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		XSurviveEnchantments.ENCHANTMENTS.register(eventBus);
		XSurviveLootItemConditions.LOOT_ITEM_CONDITIONS.register(eventBus);
		XSurviveGlobalLootModifiers.LOOT_MODIFIERS.register(eventBus);
		XSurviveBlocks.BLOCKS.register(eventBus);
		XSurviveBlocks.ITEMS.register(eventBus);
		XSurviveItems.ITEMS.register(eventBus);
		XSurviveMobEffects.MOB_EFFECTS.register(eventBus);
		XSurvivePotions.POTIONS.register(eventBus);
		XSurvivePoiTypes.POI_TYPES.register(eventBus);
		XSurviveVillagerProfessions.PROFESSIONS.register(eventBus);
		XSurviveBlockEntityTypes.BLOCK_ENTITIES.register(eventBus);
		XSurviveMenuTypes.MENUS.register(eventBus);
		XSurviveRecipeTypes.RECIPE_TYPES.register(eventBus);
	}
	
}
