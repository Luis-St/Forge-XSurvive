package net.luis.xsurvive.network.packet;

import java.util.function.Supplier;

import net.luis.xsurvive.client.XSurviveClientNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class LocalPlayerCapabilityUpdatePacket {
	
	private final CompoundTag tag;
	
	public LocalPlayerCapabilityUpdatePacket(CompoundTag tag) {
		this.tag = tag;
	}
	
	public LocalPlayerCapabilityUpdatePacket(FriendlyByteBuf byteBuf) {
		this.tag = byteBuf.readNbt();
	}
	
	public void encode(FriendlyByteBuf byteBuf) {
		byteBuf.writeNbt(this.tag);
	}
	
	public static class Handler {
		
		public static void handle(LocalPlayerCapabilityUpdatePacket packet, Supplier<Context> context) {
			context.get().enqueueWork(() -> {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> XSurviveClientNetworkHandler.handleCapabilityUpdate(packet.tag));
			});
			context.get().setPacketHandled(true);
		}
		
	}
	
}
