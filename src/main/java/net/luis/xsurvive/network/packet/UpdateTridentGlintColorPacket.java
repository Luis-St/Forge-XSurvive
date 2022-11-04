package net.luis.xsurvive.network.packet;

import java.util.function.Supplier;

import net.luis.xsurvive.network.NetworkPacket;
import net.luis.xsurvive.client.XSClientPacketHandler;
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

public class UpdateTridentGlintColorPacket implements NetworkPacket {
	
	private final int tridentEntityId;
	private final ItemStack tridentStack;
	
	public UpdateTridentGlintColorPacket(int tridentEntityId, ItemStack tridentStack) {
		this.tridentEntityId = tridentEntityId;
		this.tridentStack = tridentStack;
	}
	
	public UpdateTridentGlintColorPacket(FriendlyByteBuf buffer) {
		this.tridentEntityId = buffer.readInt();
		this.tridentStack = buffer.readItem();
	}
	
	@Override
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(this.tridentEntityId);
		buffer.writeItem(this.tridentStack);
	}
	
	@Override
	public void handle(Supplier<Context> context) {
		context.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				XSClientPacketHandler.handleTridentGlintColorUpdate(this.tridentEntityId, this.tridentStack);
			});
		});
	}
	
}
