package net.luis.xsurvive.mixin.entity.villager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.npc.VillagerData;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(VillagerData.class)
public abstract class VillagerDataMixin {
	
	@Shadow
	private static int[] NEXT_LEVEL_XP_THRESHOLDS;
	
	@Shadow
	private static boolean canLevelUp(int level) {
		return false;
	}
	
	@Inject(method = "getMaxXpPerLevel", at = @At("HEAD"), cancellable = true)
	private static void getMaxXpPerLevel(int level, CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(canLevelUp(level) ? NEXT_LEVEL_XP_THRESHOLDS[level] * 5 : 0);
	}
	
}
