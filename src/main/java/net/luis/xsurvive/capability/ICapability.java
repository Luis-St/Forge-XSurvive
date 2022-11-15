package net.luis.xsurvive.capability;

import net.minecraft.nbt.CompoundTag;

/**
 *
 * @author Luis-st
 *
 */

public interface ICapability {
	
	CompoundTag serializeDisk();
	
	void deserializeDisk(CompoundTag tag);
	
}
