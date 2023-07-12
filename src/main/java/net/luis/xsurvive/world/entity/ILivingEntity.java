package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.world.entity.ai.custom.CustomAi;

/**
 *
 * @author Luis-st
 *
 */

public interface ILivingEntity {
	
	boolean hasCustomAi();
	
	CustomAi getCustomAi();
}
