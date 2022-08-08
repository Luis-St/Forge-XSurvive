package net.luis.xsurvive.world.level.block;

import net.luis.xsurvive.world.level.block.entity.SmeltingFurnaceBlockEntity;
import net.luis.xsurvive.world.level.block.entity.XSurviveBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 
 * @author Luis-st
 *
 */

public class SmeltingFurnaceBlock extends AbstractFurnaceBlock {

	public SmeltingFurnaceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SmeltingFurnaceBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createFurnaceTicker(level, type, XSurviveBlockEntityTypes.SMELTING_FURNACE.get());
	}

	@Override
	protected void openContainer(Level level, BlockPos pos, Player player) {
		if (level.getBlockEntity(pos) instanceof SmeltingFurnaceBlockEntity smeltingBlockEntity) {
			player.openMenu(smeltingBlockEntity);
		}
	}
	
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rng) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY();
			double z = pos.getZ() + 0.5;
			if (rng.nextDouble() < 0.1) {
				level.playLocalSound(x, y, z, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
			Direction direction = state.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			double offset = rng.nextDouble() * 0.6D - 0.3D;
			double xOffset = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : offset;
			double yOffset = rng.nextDouble() * 9.0D / 16.0D;
			double zOffset = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : offset;
			level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0.0D, 0.0D, 0.0D);
		}
	}

}
