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

package net.luis.xsurvive.mixin.block;

import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.level.block.MysticFireBlock;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
	
	@Inject(method = "getState", at = @At("HEAD"), cancellable = true)
	private static void getState(BlockGetter blockGetter, BlockPos pos, CallbackInfoReturnable<BlockState> callback) {
		if (XSBlocks.MYSTIC_FIRE.get().canSurvive(blockGetter, pos)) {
			callback.setReturnValue(XSBlocks.MYSTIC_FIRE.get().getStateForPlacement(blockGetter, pos));
		}
	}
	
	@Inject(method = "entityInside", at = @At("TAIL"))
	public void entityInside(BlockState state, @NotNull Level level, BlockPos pos, Entity entity, CallbackInfo callback) {
		if (!level.isClientSide) {
			EntityProvider.getSafe(entity).ifPresent((handler) -> {
				if (state.getBlock() instanceof SoulFireBlock) {
					handler.setFireType(EntityFireType.SOUL_FIRE);
					handler.broadcastChanges();
				} else if (state.getBlock() instanceof MysticFireBlock) {
					handler.setFireType(EntityFireType.MYSTIC_FIRE);
					handler.broadcastChanges();
				} else {
					handler.setFireType(EntityFireType.FIRE);
					handler.broadcastChanges();
				}
			});
		}
	}
}
