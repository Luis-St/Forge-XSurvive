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

package net.luis.xsurvive.world.level.block;

import com.mojang.serialization.MapCodec;
import net.luis.xsurvive.world.level.block.entity.SmeltingFurnaceBlockEntity;
import net.luis.xsurvive.world.level.block.entity.XSBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingFurnaceBlock extends AbstractFurnaceBlock {
	
	public static final MapCodec<SmeltingFurnaceBlock> CODEC = simpleCodec(SmeltingFurnaceBlock::new);
	
	public SmeltingFurnaceBlock(@NotNull Properties properties) {
		super(properties);
	}
	
	@Override
	protected @NotNull MapCodec<SmeltingFurnaceBlock> codec() {
		return CODEC;
	}
	
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new SmeltingFurnaceBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return createFurnaceTicker(level, type, XSBlockEntityTypes.SMELTING_FURNACE.get());
	}
	
	@Override
	protected void openContainer(@NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
		if (level.getBlockEntity(pos) instanceof SmeltingFurnaceBlockEntity smeltingBlockEntity) {
			player.openMenu(smeltingBlockEntity);
		}
	}
	
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource rng) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY();
			double z = pos.getZ() + 0.5;
			if (rng.nextDouble() < 0.1) {
				level.playLocalSound(x, y, z, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
			Direction direction = state.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			double offset = rng.nextDouble() * 0.6 - 0.3;
			double xOffset = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : offset;
			double yOffset = rng.nextDouble() * 9.0 / 16.0;
			double zOffset = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : offset;
			level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0, 0.0, 0.0);
		}
	}
}
