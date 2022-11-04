package net.luis.xsurvive.capability;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;

/**
 *
 * @author Luis-st
 *
 */

public interface ICapability {
	
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
	
	CompoundTag serializeDisk();
	
	void deserializeDisk(CompoundTag tag);
	
	CompoundTag serializeNetwork();
	
	void deserializeNetwork(CompoundTag tag);
	
}
