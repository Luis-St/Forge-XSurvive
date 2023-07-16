package net.luis.xsurvive.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(EndDragonFight.class)
public abstract class EndDragonFightMixin {
	
	@Shadow private ServerLevel level;
	
	@Inject(method = "spawnExitPortal", at = @At("HEAD"), cancellable = true)
	private void spawnExitPortal(boolean active, CallbackInfo callback) {
		EndPodiumFeature endpodiumfeature = new EndPodiumFeature(active);
		endpodiumfeature.place(FeatureConfiguration.NONE, this.level, this.level.getChunkSource().getGenerator(), RandomSource.create(), new BlockPos(0, 75, 0));
		for (int i = -1; i <= 1; i++) {
			this.level.setBlockAndUpdate(new BlockPos(-3, 74, i), Blocks.AIR.defaultBlockState());
			this.level.setBlockAndUpdate(new BlockPos(3, 74, i), Blocks.AIR.defaultBlockState());
			this.level.setBlockAndUpdate(new BlockPos(i, 74, -3), Blocks.AIR.defaultBlockState());
			this.level.setBlockAndUpdate(new BlockPos(i, 74, 3), Blocks.AIR.defaultBlockState());
		}
		this.level.setBlockAndUpdate(new BlockPos(-2, 74, -2), Blocks.AIR.defaultBlockState());
		this.level.setBlockAndUpdate(new BlockPos(-2, 74, 2), Blocks.AIR.defaultBlockState());
		this.level.setBlockAndUpdate(new BlockPos(2, 74, -2), Blocks.AIR.defaultBlockState());
		this.level.setBlockAndUpdate(new BlockPos(2, 74, 2), Blocks.AIR.defaultBlockState());
		callback.cancel();
	}
}
