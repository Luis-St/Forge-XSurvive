package net.luis.xsurvive.world.level.entity.ai.custom;

import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 *
 * @author Luis-st
 *
 */

public enum CustomAiManager {
	
	INSTANCE;
	
	private final Map<Class<?>, CustomAiFactory<?>> factories = Maps.newHashMap();
	
	private CustomAiManager() {
		
	}
	
	public <T extends LivingEntity> void register(Class<T> entityClass, CustomAiFactory<T> factory) {
		if (this.factories.get(entityClass) != null) {
			throw new RuntimeException("Can not register a CustomAiFactory for Entity " + entityClass.getSimpleName() + ", since a CustomAiFactory has already been registered to it");
		} else {
			this.factories.put(entityClass, factory);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> boolean hasFactory(T entity) {
		return this.getFactory((Class<T>) entity.getClass()) != null;
	}
	
	public <T extends LivingEntity> boolean hasFactory(Class<T> entityClass) {
		return this.getFactory(entityClass) != null;
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> CustomAiFactory<T> getFactory(Class<T> entityClass) {
		return (@Nullable CustomAiFactory<T>) this.factories.get(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> CustomAi createFactory(T entity, ServerLevel level) {
		return this.getFactory((Class<T>) entity.getClass()).create(entity, level);
	}
	
}
