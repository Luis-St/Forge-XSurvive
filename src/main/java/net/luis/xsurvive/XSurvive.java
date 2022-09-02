package net.luis.xsurvive;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.luis.xsurvive.dependency.DependencyItems;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.luis.xsurvive.world.inventory.XSRecipeBookTypes;
import net.luis.xsurvive.world.item.XSCreativeModeTab;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.item.crafting.XSRecipeSerializers;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.luis.xsurvive.world.level.block.entity.XSBlockEntityTypes;
import net.luis.xsurvive.world.level.entity.XSEntityTypes;
import net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes;
import net.luis.xsurvive.world.level.entity.npc.XSVillagerProfessions;
import net.luis.xsurvive.world.level.storage.loot.XSGlobalLootModifiers;
import net.luis.xsurvive.world.level.storage.loot.predicates.XSLootItemConditions;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * 
 * @author Luis-st
 *
 */

@Mod(XSurvive.MOD_ID)
public class XSurvive {
	
	public static final String MOD_ID = "xsurvive";
	public static final String MOD_NAME = "XSurvive";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final CreativeModeTab GOLDEN_BOOK_TAB = new XSCreativeModeTab(XSurvive.MOD_ID + ".golden_book", XSItems.ENCHANTED_GOLDEN_BOOK);
	public static final CreativeModeTab RUNE_TAB = new XSCreativeModeTab(XSurvive.MOD_ID + ".rune", XSItems.RAINBOW_RUNE);
	
	public XSurvive() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		XSEnchantments.ENCHANTMENTS.register(eventBus);
		XSLootItemConditions.LOOT_ITEM_CONDITIONS.register(eventBus);
		XSGlobalLootModifiers.LOOT_MODIFIERS.register(eventBus);
		XSBlocks.BLOCKS.register(eventBus);
		XSBlocks.ITEMS.register(eventBus);
		XSItems.ITEMS.register(eventBus);
		DependencyItems.register();
		XSMobEffects.MOB_EFFECTS.register(eventBus);
		XSPotions.POTIONS.register(eventBus);
		XSPoiTypes.POI_TYPES.register(eventBus);
		XSVillagerProfessions.VILLAGER_PROFESSIONS.register(eventBus);
		XSBlockEntityTypes.BLOCK_ENTITY_TYPES.register(eventBus);
		XSMenuTypes.MENU_TYPES.register(eventBus);
		XSRecipeTypes.RECIPE_TYPES.register(eventBus);
		XSRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
		XSEntityTypes.ENTITY_TYPES.register(eventBus);
		XSRecipeBookTypes.register();
		XSNetworkHandler.register();
	}
	
}
