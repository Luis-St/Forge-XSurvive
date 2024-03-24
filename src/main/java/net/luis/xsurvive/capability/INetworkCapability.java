package net.luis.xsurvive.capability;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface INetworkCapability {
	
	default void setChanged() {}
	
	default void broadcastChanges() {}
	
	@NotNull CompoundTag serializeNetwork();
	
	void deserializeNetwork(@NotNull CompoundTag tag);
}
