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

package net.luis.xsurvive.mixin.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(SpawnerBlockEntity.class)
public abstract class SpawnerBlockEntityMixin {
	
	//region Mixin
	@Shadow private BaseSpawner spawner;
	//endregion
	
	@Inject(method = "<init>*", at = @At("TAIL"))
	public void init(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull CallbackInfo callback) {
		this.spawner.minSpawnDelay = 50;
		this.spawner.maxSpawnDelay = 200;
		this.spawner.spawnCount = 8;
		this.spawner.requiredPlayerRange = 64;
		this.spawner.maxNearbyEntities = 12;
	}
	
	@Inject(method = "load", at = @At("HEAD"))
	public void load(@NotNull CompoundTag tag, @NotNull CallbackInfo callback) {
		if (tag.contains("MinSpawnDelay")) {
			tag.remove("MinSpawnDelay");
			tag.putShort("MinSpawnDelay", (short) 50);
		}
		if (tag.contains("MaxSpawnDelay")) {
			tag.remove("MaxSpawnDelay");
			tag.putShort("MaxSpawnDelay", (short) 200);
		}
		if (tag.contains("SpawnCount")) {
			tag.remove("SpawnCount");
			tag.putShort("SpawnCount", (short) 8);
		}
		if (tag.contains("RequiredPlayerRange")) {
			tag.remove("RequiredPlayerRange");
			tag.putShort("RequiredPlayerRange", (short) 64);
		}
		if (tag.contains("MaxNearbyEntities")) {
			tag.remove("MaxNearbyEntities");
			tag.putShort("MaxNearbyEntities", (short) 12);
		}
	}
}
