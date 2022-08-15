package net.luis.xsurvive.event.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnRegisterColorHandlersEvent {
	
	@SubscribeEvent
	public static void registerGuiOverlays(RegisterColorHandlersEvent.Block event) {
		event.register((state, blockGetter, pos, i) -> {
			int age = state.getValue(StemBlock.AGE);
			int red = age * 32;
			int green = 255 - age * 8;
			int blue = age * 4;
			return red << 16 | green << 8 | blue;
		}, XSBlocks.HONEY_MELON_STEM.get());
		event.register((state, blockGetter, pos, i) -> {
			return 14731036;
		}, XSBlocks.ATTACHED_HONEY_MELON_STEM.get());
	}
	
}
