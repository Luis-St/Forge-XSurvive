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

package net.luis.xsurvive.mixin;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.LevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static net.luis.xsurvive.util.Util.*;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Raid.class)
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "unused", "BooleanMethodNameMustStartWithQuestion", "NonConstantFieldWithUpperCaseName" })
public abstract class RaidMixin {
	
	private static final Method RAIDER_TYPE_VALUES;
	private static final Field RAIDER_TYPE_ENTITY_TYPE;
	private static final Field RAIDER_TYPE_SPAWNS_PER_WAVE_BEFORE_BONUS;
	
	// @formatter:off
	private static final int[] VINDICATOR_SPAWNS = {
	//  0, 0, 2, 0, 1,  4,  2,  5 -> Max: 48
		0, 2, 4, 5, 5, 8, 10, 12
	};
	private static final int[] EVOKER_SPAWNS = {
	//  0, 0, 0, 0, 0, 1, 1, 2 -> Max: 12
		0, 0, 1, 1, 1, 2, 2, 3
	};
	private static final int[] PILLAGER_SPAWNS = {
	//  0, 4, 3,  3,  4,  4,  4,  2 -> Max: 56
		0, 5, 6, 6, 7, 9, 12, 14
	};
	private static final int[] WITCH_SPAWNS = {
	//  0, 0, 0, 0, 3, 0, 0, 1 -> Max: 16
		0, 0, 1, 1, 2, 2, 3, 4
	};
	private static final int[] RAVAGER_SPAWNS = {
	//  0, 0, 0, 1, 0, 1, 0, 2 -> Max: 12
		0, 0, 1, 1, 1, 2, 2, 3
	};
	// @formatter:on
	
	//region Mixin
	@Shadow private static Component RAID_NAME_COMPONENT;
	
	@Shadow private BlockPos center;
	@Shadow private ServerLevel level;
	@Shadow private float totalHealth;
	@Shadow private int groupsSpawned;
	@Shadow private ServerBossEvent raidEvent;
	@Shadow private RandomSource random;
	@Shadow private int numGroups;
	@Shadow private Optional<BlockPos> waveSpawnPos;
	
	@Shadow
	public abstract boolean isOver();
	
	@Shadow
	protected abstract boolean shouldSpawnBonusGroup();
	
	@Shadow
	public abstract void joinRaid(int wave, @NotNull Raider raider, @Nullable BlockPos pos, boolean hasLeader);
	
	@Shadow
	public abstract void updateBossbar();
	
	@Shadow
	public abstract int getTotalRaidersAlive();
	
	@Shadow
	protected abstract void setDirty();
	
	@Shadow
	public abstract boolean addWaveMob(int wave, @NotNull Raider raider, boolean updatedBossbar);
	
	@Shadow
	public abstract void setLeader(int wave, @NotNull Raider raider);
	
	@Shadow
	public abstract int getNumGroups(@NotNull Difficulty difficulty);
	//endregion
	
	private int totalRaiders;
	
	//region Reflection helper
	private static Object @NotNull [] getRaiderTypeValues() {
		try {
			return (Object[]) RAIDER_TYPE_VALUES.invoke(null);
		} catch (Exception e) {
			throw new RuntimeException("Failed to invoke method", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static EntityType<? extends Raider> getRaiderTypeEntityType(@NotNull Object raiderType) {
		try {
			return (EntityType<? extends Raider>) RAIDER_TYPE_ENTITY_TYPE.get(raiderType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get field", e);
		}
	}
	
	private static int @NotNull [] getRaiderTypeSpawnsPerWaveBeforeBonus(@NotNull Object raiderType) {
		try {
			return (int[]) RAIDER_TYPE_SPAWNS_PER_WAVE_BEFORE_BONUS.get(raiderType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get field", e);
		}
	}
	//endregion
	
	@Inject(method = "<init>(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", at = @At("RETURN"))
	public void init(int id, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull CallbackInfo callback) {
		this.raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
	}
	
	@Inject(method = "<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
	public void init(@NotNull ServerLevel level, @NotNull CompoundTag tag, @NotNull CallbackInfo callback) {
		this.raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
		if (tag.contains("TotalRaiders")) {
			this.totalRaiders = tag.getInt("TotalRaiders");
		}
		if (!this.isOver()) {
			AABB area = new AABB(this.center).inflate(64.0).setMinY(level.getMinY()).setMaxY(level.getMaxY());
			for (Raider raider : level.getEntitiesOfClass(Raider.class, area, EntitySelector.LIVING_ENTITY_STILL_ALIVE)) {
				this.joinRaid(this.groupsSpawned, raider, null, false);
			}
			this.updateBossbar();
		}
	}
	
	@Inject(method = "save", at = @At("RETURN"))
	public void save(@NotNull CompoundTag tag, CallbackInfoReturnable<CompoundTag> callback) {
		tag.putInt("TotalRaiders", this.totalRaiders);
	}
	
	@Inject(at = @At("HEAD"), method = "spawnGroup", cancellable = true)
	private void spawnGroup(@NotNull BlockPos pos, @NotNull CallbackInfo callback) {
		this.totalHealth = 0.0F;
		this.totalRaiders = 0;
		int waveGroupCount = 5;
		int wave = this.groupsSpawned + 1;
		boolean bonusWave = this.shouldSpawnBonusGroup();
		
		Map<Object /*Raid.RaiderType*/, List<Integer>> spawnGroups = new HashMap<>();
		for (Object raiderType : getRaiderTypeValues()) {
			int defaultSpawns = this.getDefaultNumSpawns(raiderType, wave, bonusWave);
			int potentialBonusSpawns = this.getPotentialBonusSpawns(raiderType, this.random, wave, LevelHelper.getCurrentDifficultyAt(this.level, pos), bonusWave);
			this.totalRaiders += defaultSpawns + potentialBonusSpawns;
			if (defaultSpawns + potentialBonusSpawns > 0) {
				spawnGroups.put(raiderType, split(defaultSpawns + potentialBonusSpawns, waveGroupCount));
			}
		}
		boolean hasLeader = false;
		List<Vec3> spawnPositions = circlePoints(75.0, this.center.getCenter(), waveGroupCount).stream().map(vec -> offsetPointCircular(15.0, vec)).map(vec -> {
			int y = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) vec.x(), (int) vec.z());
			return new Vec3(vec.x(), y + 1, vec.z());
		}).toList();
		
		for (Map.Entry<Object /*Raid.RaiderType*/, List<Integer>> entry : spawnGroups.entrySet()) {
			List<Integer> spawns = entry.getValue();
			if (spawns.isEmpty()) {
				continue;
			}
			for (int i = 0; i < waveGroupCount; i++) {
				if (spawns.get(i) == 0) {
					continue;
				}
				boolean result = this.spawnRaiderType(wave, BlockPos.containing(spawnPositions.get(i)), spawns.get(i), entry.getKey(), hasLeader);
				hasLeader = hasLeader || result;
			}
		}
		this.waveSpawnPos = Optional.empty();
		++this.groupsSpawned;
		this.updateBossbar();
		this.setDirty();
		callback.cancel();
	}
	
	private int getDefaultNumSpawns(@NotNull Object /*Raid.RaiderType*/ raiderType, int wave, boolean bonusWave) {
		int waveSpawns = this.getNumSpawns(raiderType, wave);
		for (int j = 0; j < (wave / 8); j++) {
			waveSpawns += this.getNumSpawns(raiderType, 7);
		}
		return waveSpawns;
	}
	
	private int getPotentialBonusSpawns(@NotNull Object /*Raid.RaiderType*/ raiderType, @NotNull RandomSource rng, int wave, @NotNull DifficultyInstance instance, boolean bonusWave) {
		return switch (raiderType.toString()) {
			case "VINDICATOR", "PILLAGER" -> rng.nextInt(Math.max(1, this.getNumSpawns(raiderType, wave) / 2));
			case "EVOKER", "RAVAGER", "WITCH" -> rng.nextInt((wave / 8) + 1) + rng.nextInt((wave / 8) + 1);
			default -> rng.nextInt((wave / 8) + 1);
		};
	}
	
	private int getNumSpawns(@NotNull Object /*Raid.RaiderType*/ raiderType, int wave) {
		int index = wave > 7 ? (wave % 8) + 1 : wave;
		return switch (raiderType.toString()) {
			case "VINDICATOR" -> VINDICATOR_SPAWNS[index];
			case "EVOKER" -> EVOKER_SPAWNS[index];
			case "PILLAGER" -> PILLAGER_SPAWNS[index];
			case "WITCH" -> WITCH_SPAWNS[index];
			case "RAVAGER" -> RAVAGER_SPAWNS[index];
			default -> getSafeOrElseLast(getRaiderTypeSpawnsPerWaveBeforeBonus(raiderType), index, 10) * 2;
		};
	}
	
	private boolean spawnRaiderType(int wave, @NotNull BlockPos pos, int spawnCount, @NotNull Object /*Raid.RaiderType*/ raiderType, boolean hasLeader) {
		int riders = 0;
		for (int i = 0; i < spawnCount; i++) {
			EntityType<? extends Raider> entityType = getRaiderTypeEntityType(raiderType);
			Raider raider = entityType.create(this.level, EntitySpawnReason.EVENT);
			if (raider == null) {
				XSurvive.LOGGER.error("Failed to create raider of type {}", raiderType);
				break;
			}
			if (!hasLeader && raider.canBeLeader()) {
				raider.setPatrolLeader(true);
				this.setLeader(wave, raider);
				hasLeader = true;
			}
			this.joinRaid(wave, raider, pos, false);
			if (getRaiderTypeEntityType(raiderType) == EntityType.RAVAGER) {
				Raider ravagerRider = this.getRavagerRider(wave, riders);
				if (ravagerRider != null) {
					this.joinRaid(wave, ravagerRider, pos, false);
					ravagerRider.moveTo(pos, 0.0F, 0.0F);
					ravagerRider.startRiding(raider);
					++riders;
				}
			}
		}
		return hasLeader;
	}
	
	private @Nullable Raider getRavagerRider(int wave, int riders) {
		EntityType<? extends Raider> entityType = switch (this.level.getDifficulty()) {
			case EASY -> EntityType.PILLAGER;
			case NORMAL -> {
				if (wave > this.getDefaultNumGroups(Difficulty.NORMAL)) {
					yield this.random.nextInt(5) == 0 ? EntityType.VINDICATOR : EntityType.PILLAGER;
				}
				yield EntityType.PILLAGER;
			}
			case HARD -> {
				if (wave > this.getDefaultNumGroups(Difficulty.HARD) && 2 > riders) {
					yield EntityType.EVOKER;
				}
				yield this.random.nextInt(3) == 0 ? EntityType.VINDICATOR : EntityType.PILLAGER;
			}
			default -> null;
		};
		return entityType != null ? entityType.create(this.level, EntitySpawnReason.EVENT) : null;
	}
	
	@Inject(method = "updateBossbar", at = @At("HEAD"), cancellable = true)
	public void updateBossbar(@NotNull CallbackInfo callback) {
		this.raidEvent.setProgress(Mth.clamp(this.getTotalRaidersAlive() / (float) this.totalRaiders, 0.0F, 1.0F));
		callback.cancel();
	}
	
	@Inject(method = "getNumGroups", at = @At("HEAD"), cancellable = true)
	public void getNumGroups(@NotNull Difficulty difficulty, @NotNull CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(Math.max(6, this.getDefaultNumGroups(difficulty) * difficulty.getId()));
	}
	
	private int getDefaultNumGroups(@NotNull Difficulty difficulty) {
		return difficulty.getId() * 2 + 1;
	}
	
	static {
		try {
			Class<?> clazz = Class.forName("net.minecraft.world.entity.raid.Raid$RaiderType");
			RAIDER_TYPE_VALUES = ObfuscationReflectionHelper.findMethod(clazz, "values");
			RAIDER_TYPE_ENTITY_TYPE = ObfuscationReflectionHelper.findField(clazz, "f_37814_");
			RAIDER_TYPE_SPAWNS_PER_WAVE_BEFORE_BONUS = ObfuscationReflectionHelper.findField(clazz, "f_37815_");
		} catch (Exception e) {
			throw new RuntimeException("Failed to find method", e);
		}
	}
}
