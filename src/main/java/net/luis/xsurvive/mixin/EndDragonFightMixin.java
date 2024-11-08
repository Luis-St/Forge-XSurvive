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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
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

@Mixin(EndDragonFight.class)
public abstract class EndDragonFightMixin {
	
	//region Mixin
	@Shadow private ServerLevel level;
	//endregion
	
	@Inject(method = "spawnExitPortal", at = @At("HEAD"), cancellable = true)
	private void spawnExitPortal(boolean active, @NotNull CallbackInfo callback) {
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
