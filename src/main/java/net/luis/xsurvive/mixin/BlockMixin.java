package net.luis.xsurvive.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.extensions.IForgeBlock;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements IForgeBlock {

	private BlockMixin(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean canHarvest, FluidState fluid) {
		BlockState belowState = level.getBlockState(pos.below());
		int replanting = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.REPLANTING.get(), player);
		if (replanting > 0) {
			if (state.getBlock() instanceof CropBlock block && block.isMaxAge(state)) {
				return level.setBlock(pos, block.defaultBlockState(), 3);
			} else if (state.getBlock() instanceof NetherWartBlock block && state.getValue(NetherWartBlock.AGE) >= 3) {
				return level.setBlock(pos, block.defaultBlockState(), 3);
			} else if (state.getBlock() instanceof StemBlock block && state.getValue(StemBlock.AGE) >= 7) {
				return level.setBlock(pos, block.defaultBlockState(), 3);
			} else if (state.getBlock() instanceof AttachedStemBlock block) {
				if (block.seedSupplier.get() instanceof BlockItem blockItem && blockItem.getBlock() instanceof StemBlock stemBlock) {
					return level.setBlock(pos, stemBlock.defaultBlockState().setValue(StemBlock.AGE, 7), 3); 
				}
				return level.setBlock(pos, block.defaultBlockState().setValue(AttachedStemBlock.FACING, state.getValue(AttachedStemBlock.FACING)), 3); 
			} else if (state.getBlock() instanceof SweetBerryBushBlock block && state.getValue(SweetBerryBushBlock.AGE) >= 3) {
				return level.setBlock(pos, block.defaultBlockState(), 3);
			} else if (state.getBlock() instanceof BambooSaplingBlock block && level.getBlockState(pos.below()).is(BlockTags.BAMBOO_PLANTABLE_ON)) {
				return level.setBlock(pos, Blocks.BAMBOO_SAPLING.defaultBlockState(), 3);
			} else if (state.getBlock() instanceof BambooBlock block && !(belowState.getBlock() instanceof BambooBlock) && belowState.is(BlockTags.BAMBOO_PLANTABLE_ON)) {
				if (level.getBlockState(pos.above()).getBlock() instanceof BambooBlock) {
					level.destroyBlock(pos.above(), true, player);
				}
				return level.setBlock(pos, Blocks.BAMBOO_SAPLING.defaultBlockState(), 512);
			} else if (state.getBlock() instanceof CactusBlock block && !(belowState.getBlock() instanceof CactusBlock) && (belowState.is(Blocks.SAND) || belowState.is(Blocks.RED_SAND))) {
				if (level.getBlockState(pos.above()).getBlock() instanceof CactusBlock) {
					level.destroyBlock(pos.above(), true, player);
				}
				return level.setBlock(pos, block.defaultBlockState(), 512);
			} else if (state.getBlock() instanceof CocoaBlock block && state.getValue(CocoaBlock.AGE) >= 2) {
				return level.setBlock(pos, state.setValue(CocoaBlock.AGE, 0), 3);
			} else if (state.getBlock() instanceof SugarCaneBlock block && !(belowState.getBlock() instanceof SugarCaneBlock) && block.canSurvive(state, level, pos)) {
				if (level.getBlockState(pos.above()).getBlock() instanceof SugarCaneBlock) {
					level.destroyBlock(pos.above(), true, player);
				}
				return level.setBlock(pos, block.defaultBlockState(), 512);
			}
		}
		return IForgeBlock.super.onDestroyedByPlayer(state, level, pos, player, canHarvest, fluid);
	}
	
}
