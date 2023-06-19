package net.luis.xsurvive.world.level.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Luis-st
 *
 */

public class MysticFireBlock extends BaseFireBlock {
	
	private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((direction) -> {
		return direction.getKey() != Direction.DOWN;
	}).collect(Util.toMap());
	private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
	private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	public static final BooleanProperty UP = PipeBlock.UP;
	private final Map<BlockState, VoxelShape> shapesCache;
	
	public MysticFireBlock(Properties properties) {
		super(properties, 3.0F);
		this.registerDefaultState(
				this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE));
		this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), MysticFireBlock::calculateShape)));
	}
	
	private static VoxelShape calculateShape(BlockState state) {
		VoxelShape voxelShape = Shapes.empty();
		if (state.getValue(UP)) {
			voxelShape = UP_AABB;
		}
		if (state.getValue(NORTH)) {
			voxelShape = Shapes.or(voxelShape, NORTH_AABB);
		}
		if (state.getValue(SOUTH)) {
			voxelShape = Shapes.or(voxelShape, SOUTH_AABB);
		}
		if (state.getValue(EAST)) {
			voxelShape = Shapes.or(voxelShape, EAST_AABB);
		}
		if (state.getValue(WEST)) {
			voxelShape = Shapes.or(voxelShape, WEST_AABB);
		}
		return voxelShape.isEmpty() ? DOWN_AABB : voxelShape;
	}
	
	@Override
	protected boolean canBurn(@NotNull BlockState state) {
		return true;
	}
	
	@Override
	public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		return this.canSurvive(state, level, pos) ? getState(level, pos) : Blocks.AIR.defaultBlockState();
	}
	
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return this.shapesCache.get(state);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
	}
	
	public BlockState getStateForPlacement(BlockGetter blockGetter, BlockPos pos) {
		if (!this.canCatchFire(blockGetter, pos, Direction.DOWN)) {
			BlockState state = this.defaultBlockState();
			for (Direction direction : Direction.values()) {
				BooleanProperty property = PROPERTY_BY_DIRECTION.get(direction);
				if (property != null) {
					state = state.setValue(property, this.canCatchFire(blockGetter, pos, direction));
				}
			}
			return state;
		} else {
			return this.defaultBlockState();
		}
	}
	
	public boolean canCatchFire(BlockGetter blockGetter, BlockPos pos, Direction direction) {
		BlockState state = blockGetter.getBlockState(pos.relative(direction));
		return state.is(Tags.Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN);
	}
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader levelReader, @NotNull BlockPos pos) {
		return this.canSurvive(levelReader, pos);
	}
	
	public boolean canSurvive(BlockGetter blockGetter, BlockPos pos) {
		return this.isValidFireLocation(blockGetter, pos);
	}
	
	private boolean isValidFireLocation(BlockGetter blockGetter, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			BlockState state = blockGetter.getBlockState(pos.relative(direction));
			if (state.is(Tags.Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definitionBuilder) {
		definitionBuilder.add(NORTH, EAST, SOUTH, WEST, UP);
	}
}
