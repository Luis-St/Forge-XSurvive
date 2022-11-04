package net.luis.xsurvive.capability;

import net.minecraft.nbt.CompoundTag;

/**
 *
 * @author Luis-st
 *
 */

public interface ICapability {
	
	default void setChanged() {
		
	}
	
	default void broadcastChanges() {
		
	}
	
	CompoundTag serializeDisk();
	
	void deserializeDisk(CompoundTag tag);
	
	CompoundTag serializeNetwork();
	
	void deserializeNetwork(CompoundTag tag);
	
}
