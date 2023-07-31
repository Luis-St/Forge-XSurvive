package net.luis.xsurvive.world.entity.npc;

import net.luis.xsurvive.capability.ICapability;

/**
 *
 * @author Luis-St
 *
 */

public interface IVillager extends ICapability {
	
	int getResetCount();
	
	void resetResetCount();
	
	void increaseResetCount();
}
