package net.luis.xsurvive.event.client;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.gui.screens.inventory.tooltip.ClientShulkerBoxTooltip;
import net.luis.xsurvive.client.renderer.gui.overlay.FrostMobEffectOverlay;
import net.luis.xsurvive.client.renderer.item.XSItemDecorator;
import net.luis.xsurvive.world.entity.XSEntityTypes;
import net.luis.xsurvive.world.inventory.XSRecipeBookTypes;
import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxTooltip;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static net.luis.xsurvive.client.XSRecipeBookCategories.*;

/**
 *
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterClientModEventHandler {
	
	@SubscribeEvent
	public static void registerClientTooltipComponentFactories(@NotNull RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(ShulkerBoxTooltip.class, ClientShulkerBoxTooltip::new);
	}
	
	@SubscribeEvent
	public static void registerColorHandlers(@NotNull RegisterColorHandlersEvent.Block event) {
		event.register((state, blockGetter, pos, i) -> {
			int age = state.getValue(StemBlock.AGE);
			int red = age * 32;
			int green = 255 - age * 8;
			int blue = age * 4;
			return red << 16 | green << 8 | blue;
		}, XSBlocks.HONEY_MELON_STEM.get());
		event.register((state, blockGetter, pos, i) -> 14731036, XSBlocks.ATTACHED_HONEY_MELON_STEM.get());
	}
	
	@SubscribeEvent
	public static void registerGuiOverlays(@NotNull RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "frost_mob_effect_overlay", new FrostMobEffectOverlay(Minecraft.getInstance()));
	}
	
	@SubscribeEvent
	public static void registerItemDecorations(@NotNull RegisterItemDecorationsEvent event) {
		for (Item item : ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(XSEnchantments.ASPECT_OF_THE_END.get()::canEnchant).map(ItemStack::getItem).toList()) {
			event.register(item, new XSItemDecorator(Minecraft.getInstance()));
		}
	}
	
	@SubscribeEvent
	public static void registerRecipeBookCategories(@NotNull RegisterRecipeBookCategoriesEvent event) {
		event.registerBookCategories(XSRecipeBookTypes.SMELTING, Lists.newArrayList(SMELTING_FURNACE_SEARCH, SMELTING_FURNACE_BLOCKS, SMELTING_FURNACE_MISC));
		event.registerAggregateCategory(SMELTING_FURNACE_SEARCH, Lists.newArrayList(SMELTING_FURNACE_BLOCKS, SMELTING_FURNACE_MISC));
		event.registerRecipeCategoryFinder(XSRecipeTypes.SMELTING.get(), (recipe) -> {
			if (recipe.getResultItem(Minecraft.getInstance().level.registryAccess()).getItem() instanceof BlockItem) {
				return SMELTING_FURNACE_BLOCKS;
			}
			return SMELTING_FURNACE_MISC;
		});
	}
	
	@SubscribeEvent
	public static void registerRenderers(@NotNull EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(XSEntityTypes.CURSED_ENDER_EYE.get(), (context) -> new ThrownItemRenderer<>(context, 1.0F, true));
	}
}
