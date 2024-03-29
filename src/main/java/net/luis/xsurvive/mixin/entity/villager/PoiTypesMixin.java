package net.luis.xsurvive.mixin.entity.villager;

import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(PoiTypes.class)
public abstract class PoiTypesMixin {
	
	@Inject(method = "getBlockStates", at = @At("HEAD"), cancellable = true)
	private static void getBlockStates(Block block, CallbackInfoReturnable<Set<BlockState>> callback) {
		if (block == Blocks.BEEHIVE) {
			callback.setReturnValue(Set.of());
		}
	}
}
