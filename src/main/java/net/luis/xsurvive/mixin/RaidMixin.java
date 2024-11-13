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

import net.luis.xsurvive.world.level.LevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static net.luis.xsurvive.util.Util.*;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(Raid.class)
//@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "unused", "BooleanMethodNameMustStartWithQuestion", "NonConstantFieldWithUpperCaseName" })
public abstract class RaidMixin { // ToDo: Fix issue with Raid.RaiderType AT
	
	/*// @formatter:off
	private static final int[] VINDICATOR_SPAWNS = {
		// 0, 0, 2, 0, 1,  4,  2,  5 -> Max: 48
		   0, 2, 4, 5, 5, 8, 10, 12
	};
	private static final int[] EVOKER_SPAWNS = {
		// 0, 0, 0, 0, 0, 1, 1, 2 -> Max: 12
		   0, 0, 0, 1, 1, 2, 2, 3
	};
	private static final int[] PILLAGER_SPAWNS = {
		// 0, 4, 3,  3,  4,  4,  4,  2 -> Max: 56
		   0, 5, 6, 6, 7, 9, 12, 14
	};
	private static final int[] WITCH_SPAWNS = {
		// 0, 0, 0, 0, 3, 0, 0, 1 -> Max: 16
		   0, 0, 1, 1, 2, 2, 3, 4
	};
	private static final int[] RAVAGER_SPAWNS = {
		// 0, 0, 0, 1, 0, 1, 0, 2 -> Max: 12
		   0, 0, 1, 1, 1, 2, 2, 3
	};
	// @formatter:on
	
	//region Mixin
	@Shadow private static Component RAID_NAME_COMPONENT;
	
	@Shadow private BlockPos center;
	@Shadow private ServerLevel level;
	@Shadow private float totalHealth;
	@Shadow private int groupsSpawned;
	@Shadow
	@Mutable
	private ServerBossEvent raidEvent;
	@Shadow private RandomSource random;
	@Shadow private int numGroups;
	@Shadow private Optional<BlockPos> waveSpawnPos;
	
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
	protected abstract int getDefaultNumSpawns(Raid.@NotNull RaiderType raiderType, int wave, boolean bonusWave);
	
	@Shadow
	protected abstract int getPotentialBonusSpawns(Raid.@NotNull RaiderType raiderType, @NotNull RandomSource rng, int wave, @NotNull DifficultyInstance instance, boolean bonusWave);
	
	@Shadow
	public abstract void setLeader(int wave, @NotNull Raider raider);
	
	@Shadow
	public abstract int getNumGroups(@NotNull Difficulty difficulty);
	//endregion
	
	private int totalRaiders;
	
	@Inject(method = "<init>*", at = @At("RETURN"))
	public void init(int id, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull CallbackInfo callback) {
		this.raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
	}
	
	@Inject(at = @At("HEAD"), method = "spawnGroup", cancellable = true)
	private void spawnGroup(@NotNull BlockPos pos, @NotNull CallbackInfo callback) {
		this.totalHealth = 0.0F;
		this.totalRaiders = 0;
		int waveGroupCount = 5;
		int wave = this.groupsSpawned + 1;
		boolean bonusWave = this.shouldSpawnBonusGroup();
		
		Map<Raid.RaiderType, List<Integer>> spawnGroups = new EnumMap<>(Raid.RaiderType.class);
		for (Raid.RaiderType raiderType : Raid.RaiderType.values()) {
			int defaultSpawns = this.getDefaultNumSpawns(raiderType, wave, bonusWave);
			int potentialBonusSpawns = this.getPotentialBonusSpawns(raiderType, this.random, wave, LevelHelper.getCurrentDifficultyAt(this.level, pos), bonusWave);
			this.totalRaiders += defaultSpawns + potentialBonusSpawns;
			List<Integer> split = split(defaultSpawns + potentialBonusSpawns, waveGroupCount);
			spawnGroups.put(raiderType, split);
		}
		boolean hasLeader = false;
		List<Vec3> spawnPositions = circlePoints(75.0, this.center.getCenter(), waveGroupCount).stream().map(vec -> offsetPointCircular(15.0, vec)).map(vec -> {
			int y = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int) vec.x(), (int) vec.z());
			return new Vec3(vec.x(), y + 1, vec.z());
		}).toList();
		for (int i = 0; i < waveGroupCount; i++) {
			for (Map.Entry<Raid.RaiderType, List<Integer>> entry : spawnGroups.entrySet()) {
				List<Integer> spawns = entry.getValue();
				if (spawns.size() <= i) {
					continue;
				}
				hasLeader = hasLeader || this.spawnRaiderType(wave, BlockPos.containing(spawnPositions.get(i)), spawns.get(i), entry.getKey(), hasLeader);
			}
		}
		this.waveSpawnPos = Optional.empty();
		++this.groupsSpawned;
		this.updateBossbar();
		this.setDirty();
		callback.cancel();
	}
	
	private boolean spawnRaiderType(int wave, @NotNull BlockPos pos, int spawnCount, Raid.@NotNull RaiderType raiderType, boolean hasLeader) {
		int riders = 0;
		for (int i = 0; i < spawnCount; i++) {
			Raider raider = raiderType.entityType.create(this.level);
			if (raider == null) {
				break;
			}
			if (!hasLeader && raider.canBeLeader()) {
				raider.setPatrolLeader(true);
				this.setLeader(wave, raider);
				hasLeader = true;
			}
			this.joinRaid(wave, raider, pos, false);
			if (raiderType.entityType == EntityType.RAVAGER) {
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
		return switch (this.level.getDifficulty()) {
			case EASY -> EntityType.PILLAGER.create(this.level);
			case NORMAL -> {
				if (wave > this.getDefaultNumGroups(Difficulty.NORMAL)) {
					yield this.random.nextInt(5) == 0 ? EntityType.VINDICATOR.create(this.level) : EntityType.PILLAGER.create(this.level);
				}
				yield EntityType.PILLAGER.create(this.level);
			}
			case HARD -> {
				if (wave > this.getDefaultNumGroups(Difficulty.HARD) && 2 > riders) {
					yield EntityType.EVOKER.create(this.level);
				}
				yield this.random.nextInt(3) == 0 ? EntityType.VINDICATOR.create(this.level) : EntityType.PILLAGER.create(this.level);
			}
			default -> null;
		};
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
	
	@Inject(method = "getDefaultNumSpawns", at = @At("HEAD"), cancellable = true)
	private void getDefaultNumSpawns(Raid.@NotNull RaiderType raiderType, int wave, boolean bonusWave, @NotNull CallbackInfoReturnable<Integer> callback) {
		int waveSpawns = this.getNumSpawns(raiderType, wave);
		for (int j = 0; j < (wave / 8); j++) {
			waveSpawns += this.getNumSpawns(raiderType, 7);
		}
		callback.setReturnValue(waveSpawns);
	}
	
	private int getNumSpawns(Raid.@NotNull RaiderType raiderType, int wave) {
		int index = wave > 7 ? (wave % 8) + 1 : wave;
		return switch (raiderType) {
			case VINDICATOR -> VINDICATOR_SPAWNS[index];
			case EVOKER -> EVOKER_SPAWNS[index];
			case PILLAGER -> PILLAGER_SPAWNS[index];
			case WITCH -> WITCH_SPAWNS[index];
			case RAVAGER -> RAVAGER_SPAWNS[index];
			default -> getSafeOrElseLast(raiderType.spawnsPerWaveBeforeBonus, index, 10) * 2;
		};
	}
	
	@Inject(method = "getPotentialBonusSpawns", at = @At("HEAD"), cancellable = true)
	private void getPotentialBonusSpawns(Raid.@NotNull RaiderType raiderType, @NotNull RandomSource rng, int wave, @NotNull DifficultyInstance instance, boolean bonusWave, @NotNull CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(switch (raiderType) {
			case VINDICATOR, PILLAGER -> rng.nextInt(Math.max(1, this.getNumSpawns(raiderType, wave) / 2));
			case EVOKER, RAVAGER, WITCH -> rng.nextInt((wave / 8) + 1) + rng.nextInt((wave / 8) + 1);
			default -> rng.nextInt((wave / 8) + 1);
		});
	}*/
}
