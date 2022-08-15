package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerBreakSpeedEvent {
	
	@SubscribeEvent
	public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
		Player player = event.getEntity();
		if (!player.isOnGround() && player.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(XSEnchantments.VOID_WALKER.get()) > 0) {
			event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
		}
	}
	
}
