package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.capability.ICapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/**
 *
 * @author Luis-st
 *
 */

public interface IEntity extends ICapability {
	
	Entity getEntity();
	
	Level getLevel();
	
	void tick();
	
	EntityFireType getFireType();
	
	default void setFireType(EntityFireType fireType) {
		
	}
	
}
