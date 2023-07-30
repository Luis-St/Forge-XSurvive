package net.luis.xsurvive.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraftforge.event.entity.player.PlayerSpawnPhantomsEvent;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	
	@Shadow private int nextTick;
	
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies, CallbackInfoReturnable<Integer> callback) {
		if (!spawnEnemies) {
			callback.setReturnValue(0);
		} else if (!level.getGameRules().getBoolean(GameRules.RULE_DOINSOMNIA)) {
			callback.setReturnValue(0);
		} else {
			RandomSource rng = level.random;
			--this.nextTick;
			if (this.nextTick > 0) {
				callback.setReturnValue(0);
			} else {
				this.nextTick += (60 + rng.nextInt(60)) * 20;
				int spawnedPhantoms = 0;
				for (ServerPlayer player : level.players()) {
					if (!player.isSpectator() && !player.isCreative()) {
						BlockPos playerPos = player.blockPosition();
						DifficultyInstance instance = level.getCurrentDifficultyAt(playerPos);
						PlayerSpawnPhantomsEvent event = new PlayerSpawnPhantomsEvent(player, 2 + rng.nextInt(instance.getDifficulty().getId() + 2));
						if (event.getResult() == Event.Result.DENY) {
							continue;
						} else if (event.getResult() == Event.Result.ALLOW || !level.dimensionType().hasSkyLight() || playerPos.getY() >= level.getSeaLevel() && level.canSeeSky(playerPos)) {
							if (instance.isHarderThan(rng.nextFloat() * 3.0F)) {
								ServerStatsCounter statsCounter = player.getStats();
								int j = Mth.clamp(statsCounter.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
								if (rng.nextInt(j) >= 72000) {
									BlockPos spawnPos = playerPos.above(20 + rng.nextInt(15)).east(-10 + rng.nextInt(21)).south(-10 + rng.nextInt(21));
									if (NaturalSpawner.isValidEmptySpawnBlock(level, spawnPos, level.getBlockState(spawnPos), level.getFluidState(spawnPos), EntityType.PHANTOM)) {
										SpawnGroupData spawnGroup = null;
										int spawnCount = event.getPhantomsToSpawn();
										for (int count = 0; count < spawnCount; ++count) {
											Phantom phantom = Objects.requireNonNull(EntityType.PHANTOM.create(level));
											phantom.moveTo(spawnPos, 0.0F, 0.0F);
											spawnGroup = phantom.finalizeSpawn(level, instance, MobSpawnType.NATURAL, spawnGroup, null);
											level.addFreshEntityWithPassengers(phantom);
										}
										spawnedPhantoms += spawnCount;
									}
								}
							}
						}
					}
				}
				callback.setReturnValue(spawnedPhantoms);
			}
		}
	}
}
