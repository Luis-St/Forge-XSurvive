package net.luis.xsurvive.network.packet;

import java.util.function.Supplier;

import net.luis.xsurvive.server.capability.ServerPlayerCapabilityHandler;
import net.luis.xsurvive.world.capability.CapabilityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

/**
 * 
 * @author Luis-st
 *
 */

public class ServerPlayerCapabilityUpdatePacket {
	
	private final CompoundTag tag;
	
	public ServerPlayerCapabilityUpdatePacket(CompoundTag tag) {
		this.tag = tag;
	}
	
	public ServerPlayerCapabilityUpdatePacket(FriendlyByteBuf byteBuf) {
		this.tag = byteBuf.readNbt();
	}
	
	public void encode(FriendlyByteBuf byteBuf) {
		byteBuf.writeNbt(this.tag);
	}
	
	public static class Handler {
		
		public static void handle(ServerPlayerCapabilityUpdatePacket packet, Supplier<Context> context) {
			ServerPlayer player = context.get().getSender();
			context.get().enqueueWork(() -> {
				ServerPlayerCapabilityHandler handler = CapabilityUtil.getServerPlayer(player);
				handler.deserializeNetwork(packet.tag);
			});
			context.get().setPacketHandled(true);
		}
		
	}
	
}
