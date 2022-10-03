package net.luis.xsurvive.world.entity;

import org.jetbrains.annotations.Nullable;

import net.luis.xsurvive.world.entity.ai.custom.CustomAi;

/**
 *
 * @author Luis-st
 *
 */

public interface ILivingEntity {
	
	boolean hasCustomAi();
	
	@Nullable
	CustomAi getCustomAi();
	
}
