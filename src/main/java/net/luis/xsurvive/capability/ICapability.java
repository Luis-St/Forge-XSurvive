package net.luis.xsurvive.capability;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public interface ICapability {
	
	@NotNull CompoundTag serializeDisk();
	
	void deserializeDisk(@NotNull CompoundTag tag);
}
