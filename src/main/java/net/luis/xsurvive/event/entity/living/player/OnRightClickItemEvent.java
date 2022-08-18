package net.luis.xsurvive.event.entity.living.player;

import java.util.Map.Entry;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.entity.EntityHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnRightClickItemEvent {
	
	@SubscribeEvent
	public static void rightClickItem(RightClickItem event) {
		Player player = event.getEntity();
		Entry<EquipmentSlot, ItemStack> entry = XSEnchantmentHelper.getItemWithEnchantment(XSEnchantments.ASPECT_OF_THE_END.get(), player);
		int aspectOfTheEnd = entry.getValue().getEnchantmentLevel(XSEnchantments.ASPECT_OF_THE_END.get());
		if (aspectOfTheEnd > 0 && !player.level.isClientSide) {
			Vec3 clipVector = EntityHelper.clipWithDistance(player, player.level, 6.0 * aspectOfTheEnd);
			player.teleportToWithTicket(clipVector.x, clipVector.y, clipVector.z);
			entry.getValue().hurtAndBreak(aspectOfTheEnd * 2, player, (p) -> {
				p.broadcastBreakEvent(entry.getKey());
			});
		}
	}
	
}
