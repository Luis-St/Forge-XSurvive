package net.luis.xsurvive.world.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

@FunctionalInterface
public interface CustomAiFactory<T extends LivingEntity> {
	
	@NotNull CustomAi create(@NotNull T entity, @NotNull ServerLevel level);
}
