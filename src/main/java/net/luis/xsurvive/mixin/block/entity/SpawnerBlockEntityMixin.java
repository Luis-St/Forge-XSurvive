package net.luis.xsurvive.mixin.block.entity;

import net.luis.xsurvive.config.configs.blocks.MonsterSpawnerConfig;
import net.luis.xsurvive.config.util.XSConfigManager;
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
		MonsterSpawnerConfig.Settings config = XSConfigManager.MONSTER_SPAWNER_CONFIG.get().settings();
		this.spawner.minSpawnDelay = config.minSpawnDelay();
		this.spawner.maxSpawnDelay = config.maxSpawnDelay();
		this.spawner.spawnCount = config.spawnCount();
		this.spawner.requiredPlayerRange = config.requiredPlayerRange();
		this.spawner.maxNearbyEntities = config.maxNearbyEntities();
	}
	
	@Inject(method = "load", at = @At("HEAD"))
	public void load(@NotNull CompoundTag tag, CallbackInfo callback) {
		MonsterSpawnerConfig.Settings config = XSConfigManager.MONSTER_SPAWNER_CONFIG.get().settings();
		if (tag.contains("MinSpawnDelay")) {
			tag.remove("MinSpawnDelay");
			tag.putShort("MinSpawnDelay", (short) config.minSpawnDelay());
		}
		if (tag.contains("MaxSpawnDelay")) {
			tag.remove("MaxSpawnDelay");
			tag.putShort("MaxSpawnDelay", (short) config.maxSpawnDelay());
		}
		if (tag.contains("SpawnCount")) {
			tag.remove("SpawnCount");
			tag.putShort("SpawnCount", (short) config.spawnCount());
		}
		if (tag.contains("RequiredPlayerRange")) {
			tag.remove("RequiredPlayerRange");
			tag.putShort("RequiredPlayerRange", (short) config.requiredPlayerRange());
		}
		if (tag.contains("MaxNearbyEntities")) {
			tag.remove("MaxNearbyEntities");
			tag.putShort("MaxNearbyEntities", (short) config.maxNearbyEntities());
		}
	}
}
