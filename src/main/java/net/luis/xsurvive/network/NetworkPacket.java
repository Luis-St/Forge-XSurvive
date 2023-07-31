package net.luis.xsurvive.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 *
 * @author Luis-St
 *
 */

public interface NetworkPacket {
	
	void encode(@NotNull FriendlyByteBuf buffer);
	
	void handle(@NotNull Supplier<Context> context);
}

