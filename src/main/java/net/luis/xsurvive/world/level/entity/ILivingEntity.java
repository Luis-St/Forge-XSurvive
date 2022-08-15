package net.luis.xsurvive.world.level.entity;

import org.jetbrains.annotations.Nullable;

import net.luis.xsurvive.world.level.entity.ai.custom.CustomAi;

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
