package net.luis.xsurvive.event.level;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnCreateFluidSourceEvent {
	
	@SubscribeEvent
	public static void createFluidSource(CreateFluidSourceEvent event) {
		if (event.getLevel() instanceof Level level && level.dimension().equals(Level.NETHER)) {
			event.setResult(Result.ALLOW);
		}
	}
	
}
