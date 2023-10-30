package net.luis.xsurvive.mixin.entity.villager;

import net.minecraft.world.entity.npc.VillagerData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(VillagerData.class)
@SuppressWarnings({"StaticVariableUsedBeforeInitialization", "NonConstantFieldWithUpperCaseName"})
public abstract class VillagerDataMixin {
	
	//region Mixin
	@Shadow private static int[] NEXT_LEVEL_XP_THRESHOLDS;
	
	@Shadow
	private static boolean canLevelUp(int level) {
		return false;
	}
	//endregion
	
	@Inject(method = "getMaxXpPerLevel", at = @At("HEAD"), cancellable = true)
	private static void getMaxXpPerLevel(int level, @NotNull CallbackInfoReturnable<Integer> callback) {
		callback.setReturnValue(canLevelUp(level) ? NEXT_LEVEL_XP_THRESHOLDS[level] * 3 : 0);
	}
}
