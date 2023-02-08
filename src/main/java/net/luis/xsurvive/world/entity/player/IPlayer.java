package net.luis.xsurvive.world.entity.player;

import net.luis.xsurvive.capability.INetworkCapability;
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
public interface IPlayer extends INetworkCapability {
	
	Player getPlayer();
	
	default Level getLevel() {
		return this.getPlayer().getLevel();
	}
	
	void tick();
	
	int getFrostTime();
	
	default void setFrostTime(int frostTime) {
		
	}
	
	double getFrostPercent();
	
	int getEndAspectCooldown();
	
	double getEndAspectPercent();
	
	default void setEndAspectCooldown(int endAspectCooldown) {
		
	}
	
	CompoundTag serializePersistent();
	
	void deserializePersistent(CompoundTag tag);
	
}
