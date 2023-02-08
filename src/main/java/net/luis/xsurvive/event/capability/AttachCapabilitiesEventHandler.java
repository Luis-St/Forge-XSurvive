package net.luis.xsurvive.event.capability;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.capability.ClientEntityHandler;
import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.server.capability.ServerEntityHandler;
import net.luis.xsurvive.server.capability.ServerPlayerHandler;
import net.luis.xsurvive.server.capability.ServerVillagerHandler;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.npc.VillagerProvider;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.item.GlintColorProvider;
import net.luis.xsurvive.world.item.IGlintColor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class AttachCapabilitiesEventHandler {
	
	@SubscribeEvent
	public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof ServerPlayer player) {
			event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "server_player_capability"), new PlayerProvider(new ServerPlayerHandler(player)));
		}
		if (!entity.level.isClientSide) {
			event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "server_entity_capability"), new EntityProvider(new ServerEntityHandler(entity)));
			if (entity instanceof Villager) {
				event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "server_villager_capability"), new VillagerProvider(new ServerVillagerHandler()));
			}
		}
	}
	
	@SubscribeEvent
	public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
			event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "enchanted_golden_apple_glint"), new GlintColorProvider(IGlintColor.simple(DyeColor.YELLOW)));
		}
	}
	
	@EventBusSubscriber(modid = XSurvive.MOD_ID, value = Dist.CLIENT)
	public static class Client {
		
		@SubscribeEvent
		public static void AttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			Entity entity = event.getObject();
			if (entity instanceof LocalPlayer player) {
				event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "local_player_capability"), new PlayerProvider(new LocalPlayerHandler(player)));
			}
			if (entity.level.isClientSide) {
				event.addCapability(new ResourceLocation(XSurvive.MOD_ID, "client_entity_capability"), new EntityProvider(new ClientEntityHandler(entity)));
			}
		}
		
	}
	
}
