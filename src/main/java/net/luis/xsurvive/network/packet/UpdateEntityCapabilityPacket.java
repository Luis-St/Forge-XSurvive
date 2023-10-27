package net.luis.xsurvive.network.packet;

import net.luis.xsurvive.client.XSClientPacketHandler;
import net.luis.xsurvive.network.NetworkPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class UpdateEntityCapabilityPacket implements NetworkPacket {
	
	private final int entityId;
	private final CompoundTag tag;
	
	public UpdateEntityCapabilityPacket(int entityId, CompoundTag tag) {
		this.entityId = entityId;
		this.tag = tag;
	}
	
	public UpdateEntityCapabilityPacket(@NotNull FriendlyByteBuf buffer) {
		this.entityId = buffer.readInt();
		this.tag = buffer.readNbt();
	}
	
	@Override
	public void encode(@NotNull FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
		buffer.writeNbt(this.tag);
	}
	
	@Override
	public void handle(@NotNull CustomPayloadEvent.Context context) {
		context.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleEntityCapabilityUpdate(this.entityId, this.tag);
			});
		});
	}
}
