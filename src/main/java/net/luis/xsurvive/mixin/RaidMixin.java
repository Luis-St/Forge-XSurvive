package net.luis.xsurvive.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Raid.class)
public abstract class RaidMixin {
	
	private static final int[] VINDICATOR_SPAWNS = new int[] {
			1, 3, 8, 12, 15, 20, 24, 29
	};
	private static final int[] PILLAGER_SPAWNS = new int[] {
			3, 5, 11, 15, 19, 24, 30, 34
	};
	private static final int[] WITCH_SPAWNS = new int[] {
			1, 1, 2, 3, 4, 5, 6, 7
	};
	
	@Shadow
	private ServerLevel level;
	@Shadow
	private int numGroups;
	
	@Inject(method = "getDefaultNumSpawns", at = @At("HEAD"), cancellable = true)
	private void getDefaultNumSpawns(Raid.RaiderType raiderType, int wave, boolean bonusWave, CallbackInfoReturnable<Integer> callback) {
		RandomSource rng = RandomSource.create();
		int spawns = bonusWave ? raiderType.spawnsPerWaveBeforeBonus[this.numGroups] : raiderType.spawnsPerWaveBeforeBonus[wave];
		switch (raiderType) {
			case VINDICATOR -> callback.setReturnValue(VINDICATOR_SPAWNS[bonusWave ? this.numGroups : wave] + rng.nextInt(Math.max(1, wave)));
			case EVOKER, RAVAGER -> callback.setReturnValue(wave + rng.nextInt(Math.max(1, wave)));
			case PILLAGER -> callback.setReturnValue(PILLAGER_SPAWNS[bonusWave ? this.numGroups : wave] + rng.nextInt(Math.max(1, wave)));
			case WITCH -> callback.setReturnValue(WITCH_SPAWNS[bonusWave ? this.numGroups : wave]);
			default -> callback.setReturnValue(spawns + rng.nextInt(Math.max(1, spawns / 2)));
		}
	}
	
	@Inject(method = "getPotentialBonusSpawns", at = @At("HEAD"), cancellable = true)
	private void getPotentialBonusSpawns(Raid.RaiderType raiderType, RandomSource rng, int wave, DifficultyInstance instance, boolean bonusWave, CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(0);
		switch (raiderType) {
			case VINDICATOR, EVOKER -> callback.setReturnValue(0);
			case PILLAGER, WITCH -> callback.setReturnValue(1);
			case RAVAGER -> callback.setReturnValue(rng.nextInt(3) == 0 && bonusWave ? 1 : 0);
			default -> callback.setReturnValue(rng.nextInt(wave));
		}
	}
	
}
