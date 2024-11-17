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

package net.luis.xsurvive.core;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfigs;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public record TrialSpawnerConfigKey(@NotNull ResourceKey<TrialSpawnerConfig> normal, @NotNull ResourceKey<TrialSpawnerConfig> ominous) {
	
	public static @NotNull TrialSpawnerConfigKey of(@NotNull String name) {
		return new TrialSpawnerConfigKey(XSResourceKeys.createVanillaTrialSpawnerConfigKey(name + "/normal"), XSResourceKeys.createVanillaTrialSpawnerConfigKey(name + "/ominous"));
	}
}
