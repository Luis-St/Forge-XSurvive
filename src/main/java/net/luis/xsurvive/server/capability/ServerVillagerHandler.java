package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.world.entity.npc.IVillager;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class ServerVillagerHandler implements IVillager {
	
	private int resetCount;
	
	@Override
	public int getResetCount() {
		return this.resetCount;
	}
	
	@Override
	public void resetResetCount() {
		this.resetCount = 0;
	}
	
	@Override
	public void increaseResetCount() {
		this.resetCount++;
	}
	
	@Override
	public @NotNull CompoundTag serializeDisk() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("reset_count", this.resetCount);
		return tag;
	}
	
	@Override
	public void deserializeDisk(@NotNull CompoundTag tag) {
		this.resetCount = tag.getInt("reset_count");
	}
}
