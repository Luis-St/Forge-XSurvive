package net.luis.xsurvive.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface NetworkPacket {
	
	void encode(@NotNull FriendlyByteBuf buffer);
	
	void handle(@NotNull CustomPayloadEvent.Context context);
}

