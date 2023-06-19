package net.luis.xsurvive.world.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
public interface CustomAiFactory<T extends LivingEntity> {
	
	CustomAi create(T entity, ServerLevel level);
}
