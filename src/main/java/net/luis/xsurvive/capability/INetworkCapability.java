package net.luis.xsurvive.capability;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public interface INetworkCapability extends ICapability {
	
	default void setChanged() {
		
	}
	
	default void broadcastChanges() {
		
	}
	
	@NotNull CompoundTag serializeNetwork();
	
	void deserializeNetwork(@NotNull CompoundTag tag);
}
