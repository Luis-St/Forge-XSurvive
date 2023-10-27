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
public class UpdateLevelCapabilityPacket implements NetworkPacket {
	
	private final CompoundTag tag;
	
	public UpdateLevelCapabilityPacket(CompoundTag tag) {
		this.tag = tag;
	}
	
	public UpdateLevelCapabilityPacket(@NotNull FriendlyByteBuf buffer) {
		this.tag = buffer.readNbt();
	}
	
	@Override
	public void encode(@NotNull FriendlyByteBuf buffer) {
		buffer.writeNbt(this.tag);
	}
	
	@Override
	public void handle(@NotNull CustomPayloadEvent.Context context) {
		context.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleLevelCapabilityUpdate(this.tag);
			});
		});
	}
}
