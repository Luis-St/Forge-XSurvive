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
	public void init(BlockPos pos, BlockState state, CallbackInfo callback) {
		this.spawner.minSpawnDelay = 50;
		this.spawner.maxSpawnDelay = 200;
		this.spawner.spawnCount = 8;
		this.spawner.requiredPlayerRange = 64;
		this.spawner.maxNearbyEntities = 12;
	}
	
	@Inject(method = "load", at = @At("HEAD"))
	public void load(@NotNull CompoundTag tag, CallbackInfo callback) {
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
