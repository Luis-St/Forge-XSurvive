package net.luis.xsurvive.world.entity.npc;

import net.luis.xsurvive.capability.ICapability;

/**
 *
 * @author Luis-st
 *
 */

public interface IVillager extends ICapability {
	
	int getResetCount();
	
	void resetResetCount();
	
	void increaseResetCount();
	
}
