package net.luis.xsurvive.world.entity.player;

import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

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
	
	default void setEndAspectCooldown(int endAspectCooldown) {
	
	}
	
	double getEndAspectPercent();
	
	CombinedInvWrapper getCombinedInventory();
	
	CompoundTag serializePersistent();
	
	void deserializePersistent(CompoundTag tag);
	
}
