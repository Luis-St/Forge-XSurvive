package net.luis.xsurvive.network.packet;

import java.util.function.Supplier;

import net.luis.xsurvive.client.XSClientPacketHandler;
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

public class UpdateEntityCapabilityPacket implements NetworkPacket {
	
	private final int entityId;
	private final CompoundTag tag;
	
	public UpdateEntityCapabilityPacket(int entityId, CompoundTag tag) {
		this.entityId = entityId;
		this.tag = tag;
	}
	
	public UpdateEntityCapabilityPacket(FriendlyByteBuf buffer) {
		this.entityId = buffer.readInt();
		this.tag = buffer.readNbt();
	}
	
	@Override
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
		buffer.writeNbt(this.tag);
	}
	
	@Override
	public void handle(Supplier<Context> context) {
		context.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleEntityCapabilityUpdate(this.entityId, this.tag);
			});
		});
	}
	
}
