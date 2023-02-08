package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.world.entity.ai.custom.CustomAi;
import org.jetbrains.annotations.Nullable;

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
