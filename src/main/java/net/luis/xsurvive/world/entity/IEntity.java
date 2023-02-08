package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

public interface IEntity extends INetworkCapability {
	
	Entity getEntity();
	
	default Level getLevel() {
		return this.getEntity().getLevel();
	}
	
	void tick();
	
	EntityFireType getFireType();
	
	default void setFireType(EntityFireType fireType) {
		
	}
	
}
