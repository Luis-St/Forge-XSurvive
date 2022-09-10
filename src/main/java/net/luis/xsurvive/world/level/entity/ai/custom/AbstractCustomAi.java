package net.luis.xsurvive.world.level.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 *
 * @author Luis-st
 *
 */

public abstract class AbstractCustomAi<T extends LivingEntity> implements CustomAi {
	
	protected final T entity;
	protected final ServerLevel level;
	
	public AbstractCustomAi(T entity, ServerLevel level) {
		this.entity = entity;
		this.level = level;
	}
	
}
