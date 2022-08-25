package net.luis.xsurvive.event.client;

import java.util.stream.Collectors;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.renderer.item.XSItemDecorator;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnRegisterItemDecorationsEvent {
	
	@SubscribeEvent
	public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
		for (Item item : ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(XSEnchantments.ASPECT_OF_THE_END.get()::canEnchant).map(ItemStack::getItem).collect(Collectors.toList())) {
			event.register(item, new XSItemDecorator(Minecraft.getInstance()));
		}
	}
	
}
