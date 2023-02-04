package net.luis.xsurvive.event.client;

import static net.luis.xsurvive.client.XSRecipeBookCategories.SMELTING_FURNACE_BLOCKS;
import static net.luis.xsurvive.client.XSRecipeBookCategories.SMELTING_FURNACE_MISC;
import static net.luis.xsurvive.client.XSRecipeBookCategories.SMELTING_FURNACE_SEARCH;

import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.luis.xbackpack.XBackpack;
import net.luis.xsurvive.client.commands.GammaCommand;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XBackpack.MOD_ID, value = Dist.CLIENT)
public class RegisterClientEventHandler {
	
	@SubscribeEvent
	public static void registerClientCommands(RegisterClientCommandsEvent event) {
		GammaCommand.register(event.getDispatcher());
	}
	

	
}
