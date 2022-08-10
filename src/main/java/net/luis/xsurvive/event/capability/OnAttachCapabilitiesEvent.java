package net.luis.xsurvive.event.capability;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.server.capability.ServerPlayerCapabilityHandler;
import net.luis.xsurvive.world.item.GlintColorProvider;
import net.luis.xsurvive.world.item.IGlintColor;
import net.luis.xsurvive.world.level.entity.player.PlayerCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnAttachCapabilitiesEvent {
	
	@SubscribeEvent
	public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof ServerPlayer player) {
			event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "server_player_capability"), new PlayerCapabilityProvider(new ServerPlayerCapabilityHandler(player)));
		}
	}
	
	@SubscribeEvent
	public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
			event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "enchanted_golden_apple_glint"), new GlintColorProvider(IGlintColor.simple(DyeColor.YELLOW)));
		}
	}
	
}
