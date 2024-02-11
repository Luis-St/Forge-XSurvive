package net.luis.xsurvive;

import com.mojang.logging.LogUtils;
import net.luis.xsurvive.client.XSRecipeBookCategories;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.tag.*;
import net.luis.xsurvive.world.damagesource.XSDamageTypes;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.entity.XSEntityTypes;
import net.luis.xsurvive.world.entity.ai.village.XSPoiTypes;
import net.luis.xsurvive.world.entity.npc.XSVillagerProfessions;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.luis.xsurvive.world.inventory.XSRecipeBookTypes;
import net.luis.xsurvive.world.item.XSCreativeModeTabs;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.item.crafting.XSRecipeSerializers;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.luis.xsurvive.world.level.block.entity.XSBlockEntityTypes;
import net.luis.xsurvive.world.level.storage.loot.XSGlobalLootModifiers;
import net.luis.xsurvive.world.level.storage.loot.predicates.XSLootItemConditions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 *
 * @author Luis-St
 *
 */

@Mod(XSurvive.MOD_ID)
public class XSurvive {
	
	public static final String MOD_ID = "xsurvive";
	public static final String MOD_NAME = "XSurvive";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public XSurvive() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		XSEnchantments.ENCHANTMENTS.register(eventBus);
		XSLootItemConditions.LOOT_ITEM_CONDITIONS.register(eventBus);
		XSGlobalLootModifiers.LOOT_MODIFIERS.register(eventBus);
		XSBlocks.BLOCKS.register(eventBus);
		XSBlocks.ITEMS.register(eventBus);
		XSBlocks.Keys.register();
		XSItems.ITEMS.register(eventBus);
		XSItems.Keys.register();
		XSCreativeModeTabs.TABS.register(eventBus);
		XSMobEffects.MOB_EFFECTS.register(eventBus);
		XSPotions.POTIONS.register(eventBus);
		XSPoiTypes.POI_TYPES.register(eventBus);
		XSVillagerProfessions.VILLAGER_PROFESSIONS.register(eventBus);
		XSBlockEntityTypes.BLOCK_ENTITY_TYPES.register(eventBus);
		XSMenuTypes.MENU_TYPES.register(eventBus);
		XSRecipeTypes.RECIPE_TYPES.register(eventBus);
		XSRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
		XSEntityTypes.ENTITY_TYPES.register(eventBus);
		XSDamageTypes.register();
		XSRecipeBookTypes.register();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> XSRecipeBookCategories::register);
		XSBiomeTags.register();
		XSBlockTags.register();
		XSDamageTypeTags.register();
		XSItemTags.register();
		XSNetworkHandler.INSTANCE.initChannel();
		XSNetworkHandler.INSTANCE.registerPackets();
	}
}
