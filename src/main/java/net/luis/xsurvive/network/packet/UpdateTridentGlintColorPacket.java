package net.luis.xsurvive.network.packet;

import java.util.function.Supplier;

import net.luis.xsurvive.client.XSClientNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

/**
 *
 * @author Luis-st
 *
 */

public class UpdateTridentGlintColorPacket {
	
	private final int tridentEntityId;
	private final ItemStack tridentStack;
	
	public UpdateTridentGlintColorPacket(int tridentEntityId, ItemStack tridentStack) {
		this.tridentEntityId = tridentEntityId;
		this.tridentStack = tridentStack;
	}
	
	public UpdateTridentGlintColorPacket(FriendlyByteBuf byteBuf) {
		this.tridentEntityId = byteBuf.readInt();
		this.tridentStack = byteBuf.readItem();
	}
	
	public void encode(FriendlyByteBuf byteBuf) {
		byteBuf.writeInt(this.tridentEntityId);
		byteBuf.writeItem(this.tridentStack);
	}
	
	public static class Handler {
		
		public static void handle(UpdateTridentGlintColorPacket packet, Supplier<Context> context) {
			context.get().enqueueWork(() -> {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> XSClientNetworkHandler.handleTridentGlintColorUpdate(packet.tridentEntityId, packet.tridentStack));
			});
			context.get().setPacketHandled(true);
		}
		
	}
	
}
