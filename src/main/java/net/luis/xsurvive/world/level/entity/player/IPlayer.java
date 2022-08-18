package net.luis.xsurvive.world.level.entity.player;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

/**
 * 
 * @author Luis-st
 *
 */

@AutoRegisterCapability
public interface IPlayer {
	
	@Nullable
	Player getPlayer();
	
	@Nullable
	Level getLevel();
	
	void tick();
	
	int getFrostTime();
	
	void setFrostTime(int frostTime);
	
	float getFrostPercent();
	
	default void setChanged() {
		
	}
	
	default void broadcastChanges() {
		
	}
	
	CompoundTag serializeDisk();
	
	void deserializeDisk(CompoundTag tag);
	
	CompoundTag serializeNetwork();
	
	void deserializeNetwork(CompoundTag tag);
	
	CompoundTag serializePersistent();
	
	void deserializePersistent(CompoundTag tag);
    
}
