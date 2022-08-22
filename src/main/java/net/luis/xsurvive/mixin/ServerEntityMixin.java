package net.luis.xsurvive.mixin;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateTridentGlintColorPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {
	
	private static final Map<ThrownTrident, ItemStack> TRIDENT_STACK_REFERENCES = new WeakHashMap<>();
	
	@Shadow
	private Entity entity;
	
	@Shadow
	protected abstract void broadcastAndSend(Packet<?> packet);
	
	@Inject(method = "sendDirtyEntityData", at = @At("HEAD"))
	private void sendDirtyEntityData(CallbackInfo callback) {
		if (this.entity instanceof ThrownTrident trident) {
			this.broadcastThrownTrident(trident, false);
		}
	}
	
	@Inject(method = "sendPairingData", at = @At("HEAD"))
	private void sendPairingData(Consumer<Packet<?>> send, CallbackInfo callback) {
		if (this.entity instanceof ThrownTrident trident) {
			this.broadcastThrownTrident(trident, true);
		}
	}
	
	private void broadcastThrownTrident(ThrownTrident trident, boolean forced) {
		ItemStack tridentStack = trident.tridentItem.copy();
		ItemStack previousStack = TRIDENT_STACK_REFERENCES.get(trident);
		if (forced || previousStack == null || ItemStack.isSameItemSameTags(tridentStack, previousStack)) {
			this.broadcastAndSend(XSNetworkHandler.getChannel().toVanillaPacket(new UpdateTridentGlintColorPacket(trident.getId(), tridentStack), NetworkDirection.PLAY_TO_CLIENT));
		}
		TRIDENT_STACK_REFERENCES.put(trident, tridentStack);
	}
	
}
