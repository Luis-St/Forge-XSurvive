package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.init.XSurviveEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerBreakSpeedEvent {
	
	@SubscribeEvent
	public static void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
		Player player = event.getPlayer();
		if (!player.isOnGround() && EnchantmentHelper.getItemEnchantmentLevel(XSurviveEnchantments.VOID_WALKER.get(), player.getItemBySlot(EquipmentSlot.FEET)) > 0) {
			event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
		}
	}
	
}
