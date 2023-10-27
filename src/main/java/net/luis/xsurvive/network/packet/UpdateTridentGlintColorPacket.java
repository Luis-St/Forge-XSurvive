package net.luis.xsurvive.network.packet;

import net.luis.xsurvive.client.XSClientPacketHandler;
import net.luis.xsurvive.network.NetworkPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
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
public class UpdateTridentGlintColorPacket implements NetworkPacket {
	
	private final int tridentEntityId;
	private final ItemStack tridentStack;
	
	public UpdateTridentGlintColorPacket(int tridentEntityId, ItemStack tridentStack) {
		this.tridentEntityId = tridentEntityId;
		this.tridentStack = tridentStack;
	}
	
	public UpdateTridentGlintColorPacket(@NotNull FriendlyByteBuf buffer) {
		this.tridentEntityId = buffer.readInt();
		this.tridentStack = buffer.readItem();
	}
	
	@Override
	public void encode(@NotNull FriendlyByteBuf buffer) {
		buffer.writeInt(this.tridentEntityId);
		buffer.writeItem(this.tridentStack);
	}
	
	@Override
	public void handle(@NotNull CustomPayloadEvent.Context context) {
		context.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleTridentGlintColorUpdate(this.tridentEntityId, this.tridentStack);
			});
		});
	}
}
