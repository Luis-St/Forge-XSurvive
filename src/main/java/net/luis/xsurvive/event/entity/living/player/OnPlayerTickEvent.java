package net.luis.xsurvive.event.entity.living.player;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.capability.XSurviveCapabilities;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnPlayerTickEvent {

	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent event) {
		Player player = event.player;
		if (event.phase == TickEvent.Phase.START) {
			if (player instanceof ServerPlayer serverPlayer) {
				serverPlayer.getCapability(XSurviveCapabilities.PLAYER, null).ifPresent((handler) -> {
					handler.tick();
				});
			} else if (player instanceof LocalPlayer localPlayer) {
				localPlayer.getCapability(XSurviveCapabilities.PLAYER, null).ifPresent((handler) -> {
					handler.tick();
				});
			}
		}
		boolean hasVoidWalker = player.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(XSurviveEnchantments.VOID_WALKER.get()) > 0;
		BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
		if (hasVoidWalker) {
			player.fallDistance = 0.0F;
		}
		if (player.isShiftKeyDown() && hasVoidWalker && player.getLevel().getBlockState(pos).isAir()) {
			player.setDeltaMovement(player.getDeltaMovement().x(), -0.25, player.getDeltaMovement().z());
		} else if (!player.isShiftKeyDown() && hasVoidWalker && player.getDeltaMovement().y() != 0.0) {
			player.setDeltaMovement(player.getDeltaMovement().x(), 0.0, player.getDeltaMovement().z());
		}
	}

}
