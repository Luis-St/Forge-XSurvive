package net.luis.xsurvive.capability;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;

/**
 *
 * @author Luis-st
 *
 */

public interface INetworkCapability extends ICapability {
	
	default void setChanged() {
		
	}
	
	default void doAndBroadcast(Runnable action) {
		
	}
	
	@Nullable
	default <T> T doAndBroadcast(Supplier<T> action) {
		return null;
	}
	
	default void broadcastChanges() {
		
	}
	
	CompoundTag serializeNetwork();
	
	void deserializeNetwork(CompoundTag tag);
	
}
