package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.client.renderer.gui.overlay.FrostMobEffectOverlay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnRegisterGuiOverlaysEvent {
	
	@SubscribeEvent
	public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "frost_mob_effect_overlay", new FrostMobEffectOverlay(Minecraft.getInstance()));
	}
	
}
