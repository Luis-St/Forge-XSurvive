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

package net.luis.xsurvive.data.provider.additions;

import net.luis.xsurvive.core.TrialSpawnerConfigKey;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author Luis-St
 *
 */

public class XSAdditionsTrialSpawnerConfigProvider {
	
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_BREEZE = TrialSpawnerConfigKey.of("trial_chamber/breeze");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_MELEE_HUSK = TrialSpawnerConfigKey.of("trial_chamber/melee/husk");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_MELEE_SPIDER = TrialSpawnerConfigKey.of("trial_chamber/melee/spider");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_MELEE_ZOMBIE = TrialSpawnerConfigKey.of("trial_chamber/melee/zombie");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_RANGED_POISON_SKELETON = TrialSpawnerConfigKey.of("trial_chamber/ranged/poison_skeleton");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_RANGED_SKELETON = TrialSpawnerConfigKey.of("trial_chamber/ranged/skeleton");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_RANGED_STRAY = TrialSpawnerConfigKey.of("trial_chamber/ranged/stray");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SLOW_RANGED_POISON_SKELETON = TrialSpawnerConfigKey.of("trial_chamber/slow_ranged/poison_skeleton");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SLOW_RANGED_SKELETON = TrialSpawnerConfigKey.of("trial_chamber/slow_ranged/skeleton");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SLOW_RANGED_STRAY = TrialSpawnerConfigKey.of("trial_chamber/slow_ranged/stray");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SMALL_MELEE_BABY_ZOMBIE = TrialSpawnerConfigKey.of("trial_chamber/small_melee/baby_zombie");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SMALL_MELEE_CAVE_SPIDER = TrialSpawnerConfigKey.of("trial_chamber/small_melee/cave_spider");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SMALL_MELEE_SILVERFISH = TrialSpawnerConfigKey.of("trial_chamber/small_melee/silverfish");
	private static final TrialSpawnerConfigKey TRIAL_CHAMBER_SMALL_MELEE_SLIME = TrialSpawnerConfigKey.of("trial_chamber/small_melee/slime");
	
	public static void create(@NotNull BootstrapContext<TrialSpawnerConfig> context) {
		register(context, TRIAL_CHAMBER_BREEZE, createBreezeChamberNormal(), createBreezeChamberOminous());
		register(context, TRIAL_CHAMBER_MELEE_HUSK, createHuskChamberNormal(), createHuskChamberOminous());
		register(context, TRIAL_CHAMBER_MELEE_SPIDER, createSpiderChamberNormal(), createSpiderChamberOminous());
		register(context, TRIAL_CHAMBER_MELEE_ZOMBIE, createZombieChamberNormal(), createZombieChamberOminous());
		register(context, TRIAL_CHAMBER_RANGED_POISON_SKELETON, createPoisonSkeletonChamberNormal(), createPoisonSkeletonChamberOminous());
		register(context, TRIAL_CHAMBER_RANGED_SKELETON, createSkeletonChamberNormal(), createSkeletonChamberOminous());
		register(context, TRIAL_CHAMBER_RANGED_STRAY, createStrayChamberNormal(), createStrayChamberOminous());
		register(context, TRIAL_CHAMBER_SLOW_RANGED_POISON_SKELETON, createSlowPoisonSkeletonChamberNormal(), createSlowPoisonSkeletonChamberOminous());
		register(context, TRIAL_CHAMBER_SLOW_RANGED_SKELETON, createSlowSkeletonChamberNormal(), createSlowSkeletonChamberOminous());
		register(context, TRIAL_CHAMBER_SLOW_RANGED_STRAY, createSlowStrayChamberNormal(), createSlowStrayChamberOminous());
		register(context, TRIAL_CHAMBER_SMALL_MELEE_BABY_ZOMBIE, createBabyZombieChamberNormal(), createBabyZombieChamberOminous());
		register(context, TRIAL_CHAMBER_SMALL_MELEE_CAVE_SPIDER, createCaveSpiderChamberNormal(), createCaveSpiderChamberOminous());
		register(context, TRIAL_CHAMBER_SMALL_MELEE_SILVERFISH, createSilverfishChamberNormal(), createSilverfishChamberOminous());
		register(context, TRIAL_CHAMBER_SMALL_MELEE_SLIME, createSlimeChamberNormal(), createSlimeChamberOminous());
	}
	
	private static @NotNull TrialSpawnerConfig createBreezeChamberNormal() {
		return TrialSpawnerConfig.builder()
			.simultaneousMobs(2.0F)
			.simultaneousMobsAddedPerPlayer(1.0F)
			.totalMobs(8.0F)
			.totalMobsAddedPerPlayer(1.5F)
			.ticksBetweenSpawn(20 * 10)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.BREEZE)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createBreezeChamberOminous() {
		return TrialSpawnerConfig.builder()
			.simultaneousMobs(3.0F)
			.simultaneousMobsAddedPerPlayer(2.0F)
			.totalMobs(16.0F)
			.totalMobsAddedPerPlayer(2.5F)
			.ticksBetweenSpawn(20 * 10)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.BREEZE)))
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createHuskChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.HUSK))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createHuskChamberOminous() {
		return baseChamberOminous()
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.HUSK, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_MELEE)))
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSpiderChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.SPIDER))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createSpiderChamberOminous() {
		return baseChamberOminous()
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.SPIDER)))
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createZombieChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.ZOMBIE))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createZombieChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.ZOMBIE, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_MELEE)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createPoisonSkeletonChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.BOGGED))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createPoisonSkeletonChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.BOGGED, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_RANGED)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSkeletonChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.SKELETON))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createSkeletonChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.SKELETON, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_RANGED)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createStrayChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.STRAY))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createStrayChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.STRAY, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_RANGED)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlowPoisonSkeletonChamberNormal() {
		return slowChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.BOGGED))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlowPoisonSkeletonChamberOminous() {
		return slowChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.BOGGED, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_RANGED)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlowSkeletonChamberNormal() {
		return slowChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.SKELETON))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlowSkeletonChamberOminous() {
		return slowChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.SKELETON, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_RANGED)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlowStrayChamberNormal() {
		return slowChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.STRAY))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlowStrayChamberOminous() {
		return slowChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnDataWithEquipment(EntityType.STRAY, BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_RANGED)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createBabyZombieChamberNormal() {
		return TrialSpawnerConfig.builder()
			.simultaneousMobs(8.0F)
			.simultaneousMobsAddedPerPlayer(2.0F)
			.totalMobs(35.0F)
			.totalMobsAddedPerPlayer(7.5F)
			.ticksBetweenSpawn(20)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(customSpawnDataWithEquipment(EntityType.ZOMBIE, tag -> tag.putBoolean("IsBaby", true), null)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createBabyZombieChamberOminous() {
		return TrialSpawnerConfig.builder()
			.simultaneousMobs(14.0F)
			.simultaneousMobsAddedPerPlayer(5.0F)
			.totalMobs(50.0F)
			.totalMobsAddedPerPlayer(12.0F)
			.ticksBetweenSpawn(20)
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(
				SimpleWeightedRandomList.single(
					customSpawnDataWithEquipment(EntityType.ZOMBIE, tag -> tag.putBoolean("IsBaby", true), BuiltInLootTables.EQUIPMENT_TRIAL_CHAMBER_MELEE)
				)
			)
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createCaveSpiderChamberNormal() {
		return baseChamberNormal().spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.CAVE_SPIDER))).build();
	}
	
	private static @NotNull TrialSpawnerConfig createCaveSpiderChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.CAVE_SPIDER)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSilverfishChamberNormal() {
		return baseChamberNormal()
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.SILVERFISH)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSilverfishChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(SimpleWeightedRandomList.single(spawnData(EntityType.SILVERFISH)))
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlimeChamberNormal() {
		return baseChamberNormal()
			.spawnPotentialsDefinition(
				SimpleWeightedRandomList.<SpawnData>builder()
					.add(customSpawnData(EntityType.SLIME, tag -> tag.putByte("Size", (byte) 1)), 3)
					.add(customSpawnData(EntityType.SLIME, tag -> tag.putByte("Size", (byte) 2)), 1)
					.build()
			)
			.build();
	}
	
	private static @NotNull TrialSpawnerConfig createSlimeChamberOminous() {
		return baseChamberOminous()
			.lootTablesToEject(
				SimpleWeightedRandomList.<ResourceKey<LootTable>>builder()
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
					.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
					.build()
			)
			.spawnPotentialsDefinition(
				SimpleWeightedRandomList.<SpawnData>builder()
					.add(customSpawnData(EntityType.SLIME, tag -> tag.putByte("Size", (byte) 1)), 3)
					.add(customSpawnData(EntityType.SLIME, tag -> tag.putByte("Size", (byte) 2)), 1)
					.build()
			)
			.build();
	}
	
	//region Base config builders
	private static TrialSpawnerConfig.@NotNull Builder baseChamberNormal() {
		return TrialSpawnerConfig.builder()
			.simultaneousMobs(5.0F)
			.simultaneousMobsAddedPerPlayer(2.0F)
			.totalMobs(40.0F)
			.totalMobsAddedPerPlayer(10.0F)
			.ticksBetweenSpawn(20);
	}
	
	private static TrialSpawnerConfig.@NotNull Builder baseChamberOminous() {
		return TrialSpawnerConfig.builder()
			.simultaneousMobs(10.0F)
			.simultaneousMobsAddedPerPlayer(3.0F)
			.totalMobs(70.0F)
			.totalMobsAddedPerPlayer(15.0F)
			.ticksBetweenSpawn(20);
	}
	
	private static TrialSpawnerConfig.@NotNull Builder slowChamberNormal() {
		return baseChamberNormal()
			.totalMobs(25.0F)
			.totalMobsAddedPerPlayer(10.0F)
			.ticksBetweenSpawn(20 * 8);
	}
	
	private static TrialSpawnerConfig.@NotNull Builder slowChamberOminous() {
		return baseChamberOminous()
			.totalMobs(50.0F)
			.totalMobsAddedPerPlayer(15.0F)
			.ticksBetweenSpawn(20 * 8);
	}
	//endregion
	
	//region Spawn data helpers
	private static <T extends Entity> @NotNull SpawnData spawnData(@NotNull EntityType<T> entityType) {
		return customSpawnDataWithEquipment(entityType, tag -> {}, null);
	}
	
	@SuppressWarnings("SameParameterValue")
	private static <T extends Entity> @NotNull SpawnData customSpawnData(@NotNull EntityType<T> entityType, Consumer<CompoundTag> action) {
		return customSpawnDataWithEquipment(entityType, action, null);
	}
	
	private static <T extends Entity> @NotNull SpawnData spawnDataWithEquipment(@NotNull EntityType<T> entityType, @Nullable ResourceKey<LootTable> lootTable) {
		return customSpawnDataWithEquipment(entityType, tag -> {}, lootTable);
	}
	
	private static <T extends Entity> @NotNull SpawnData customSpawnDataWithEquipment(@NotNull EntityType<T> entityType, @NotNull Consumer<CompoundTag> action, @Nullable ResourceKey<LootTable> lootTable) {
		CompoundTag tag = new CompoundTag();
		tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
		action.accept(tag);
		return new SpawnData(tag, Optional.empty(), Optional.ofNullable(lootTable).map(table -> new EquipmentTable(table, 0.0F)));
	}
	//endregion
	
	private static void register(@NotNull BootstrapContext<TrialSpawnerConfig> context, @NotNull TrialSpawnerConfigKey key, @NotNull TrialSpawnerConfig normalConfig, @NotNull TrialSpawnerConfig ominousConfig) {
		context.register(key.normal(), normalConfig);
		context.register(key.ominous(), ominousConfig);
	}
}
