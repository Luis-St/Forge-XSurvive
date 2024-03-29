package net.luis.xsurvive.mixin.entity;

import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateTridentGlintColorPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {
	
	private static final Map<ThrownTrident, ItemStack> TRIDENT_STACK_REFERENCES = new WeakHashMap<>();
	
	//region Mixin
	@Shadow private Entity entity;
	
	@Shadow
	protected abstract void broadcastAndSend(Packet<?> packet);
	//endregion
	
	@Inject(method = "sendDirtyEntityData", at = @At("HEAD"))
	private void sendDirtyEntityData(CallbackInfo callback) {
		if (this.entity instanceof ThrownTrident trident) {
			this.broadcastThrownTrident(trident, false);
		}
	}
	
	@Inject(method = "sendPairingData", at = @At("HEAD"))
	private void sendPairingData(ServerPlayer player, Consumer<Packet<?>> send, CallbackInfo callback) {
		if (this.entity instanceof ThrownTrident trident) {
			this.broadcastThrownTrident(trident, true);
		}
	}
	
	private void broadcastThrownTrident(@NotNull ThrownTrident trident, boolean forced) {
		ItemStack tridentStack = trident.getPickupItemStackOrigin().copy();
		ItemStack previousStack = TRIDENT_STACK_REFERENCES.get(trident);
		if (forced || previousStack == null || ItemStack.isSameItemSameTags(tridentStack, previousStack)) {
			if (trident.level() instanceof ServerLevel level) {
				XSNetworkHandler.INSTANCE.sendToPlayersInLevel(level, new UpdateTridentGlintColorPacket(trident.getId(), tridentStack));
			}
		}
		TRIDENT_STACK_REFERENCES.put(trident, tridentStack);
	}
}
