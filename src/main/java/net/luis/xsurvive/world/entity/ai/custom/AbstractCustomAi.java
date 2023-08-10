package net.luis.xsurvive.world.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 *
 * @author Luis-St
 *
 */

public abstract class AbstractCustomAi<T extends LivingEntity> implements CustomAi {
	
	protected final T entity;
	protected final ServerLevel level;
	
	protected AbstractCustomAi(T entity, ServerLevel level) {
		this.entity = entity;
		this.level = level;
	}
}
