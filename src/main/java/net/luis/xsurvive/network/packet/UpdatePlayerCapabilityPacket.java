package net.luis.xsurvive.network.packet;

import java.util.function.Supplier;

import net.luis.xsurvive.client.XSClientNetworkHandler;
import net.luis.xsurvive.network.NetworkPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

/**
 * 
 * @author Luis-st
 *
 */

public class UpdatePlayerCapabilityPacket implements NetworkPacket {
	
	private final CompoundTag tag;
	
	public UpdatePlayerCapabilityPacket(CompoundTag tag) {
		this.tag = tag;
	}
	
	public UpdatePlayerCapabilityPacket(FriendlyByteBuf byteBuf) {
		this.tag = byteBuf.readNbt();
	}
	
	@Override
	public void encode(FriendlyByteBuf byteBuf) {
		byteBuf.writeNbt(this.tag);
	}
	
	@Override
	public void handle(Supplier<Context> context) {
		context.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientNetworkHandler.handleCapabilityUpdate(this.tag);
			});
		});
	}
	
}
