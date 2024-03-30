/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.world.entity.ai.custom;

import com.google.common.collect.Maps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 *
 * @author Luis-St
 *
 */

public enum CustomAiManager {
	
	INSTANCE;
	
	private final Map<Class<?>, CustomAiFactory<?>> factories = Maps.newHashMap();
	
	public <T extends LivingEntity> void register(@NotNull Class<T> entityClass, @NotNull CustomAiFactory<T> factory) {
		if (this.factories.get(entityClass) != null) {
			throw new RuntimeException("Can not register a CustomAiFactory for Entity " + entityClass.getSimpleName() + ", since a CustomAiFactory has already been registered to it");
		} else {
			this.factories.put(entityClass, factory);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> boolean hasFactory(@NotNull T entity) {
		return this.getFactory((Class<T>) entity.getClass()) != null;
	}
	
	public <T extends LivingEntity> boolean hasFactory(@NotNull Class<T> entityClass) {
		return this.getFactory(entityClass) != null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> CustomAiFactory<T> getFactory(@NotNull Class<T> entityClass) {
		return (CustomAiFactory<T>) this.factories.get(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> @NotNull CustomAi createFactory(@NotNull T entity, @NotNull ServerLevel level) {
		return this.getFactory((Class<T>) entity.getClass()).create(entity, level);
	}
}
