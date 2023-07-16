package net.luis.xsurvive.mixin;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map.Entry;

/**
 *
 * @author Luis-st
 *
 */

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
	
	@Shadow protected ServerLevel level;
	@Shadow protected ServerPlayer player;
	
	@Inject(method = "destroyBlock", at = @At(value = "RETURN", ordinal = 5), locals = LocalCapture.CAPTURE_FAILHARD)
	public void destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> callback, BlockState state) {
		BlockState belowState = this.level.getBlockState(pos.below());
		Entry<EquipmentSlot, ItemStack> entry = XSEnchantmentHelper.getItemWithEnchantment(XSEnchantments.REPLANTING.get(), this.player);
		int replanting = entry.getValue().getEnchantmentLevel(XSEnchantments.REPLANTING.get());
		if (replanting > 0 && this.level.getBlockState(pos).isAir()) {
			if (state != null) {
				boolean replanted = false;
				if (state.getBlock() instanceof CropBlock block && block.isMaxAge(state)) {
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof NetherWartBlock block && state.getValue(NetherWartBlock.AGE) >= 3) {
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof SweetBerryBushBlock block && state.getValue(SweetBerryBushBlock.AGE) >= 3) {
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof StemBlock block && state.getValue(StemBlock.AGE) >= 7) {
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof AttachedStemBlock block) {
					if (block.seedSupplier.get() instanceof BlockItem blockItem && blockItem.getBlock() instanceof StemBlock stemBlock) {
						replanted = this.level.setBlock(pos, stemBlock.defaultBlockState(), 3);
					}
				} else if (state.getBlock() instanceof CocoaBlock block && state.getValue(CocoaBlock.AGE) >= 2) {
					replanted = this.level.setBlock(pos, state.setValue(CocoaBlock.AGE, 0), 3);
				} else if (state.getBlock() instanceof BambooSaplingBlock block && this.level.getBlockState(pos.below()).is(BlockTags.BAMBOO_PLANTABLE_ON)) {
					replanted = this.level.setBlock(pos, Blocks.BAMBOO_SAPLING.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof BambooStalkBlock block && !(belowState.getBlock() instanceof BambooStalkBlock) && belowState.is(BlockTags.BAMBOO_PLANTABLE_ON)) {
					if (this.level.getBlockState(pos.above()).getBlock() instanceof BambooStalkBlock) {
						this.level.destroyBlock(pos.above(), true, player);
					}
					replanted = this.level.setBlock(pos, Blocks.BAMBOO_SAPLING.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof CactusBlock block && !(belowState.getBlock() instanceof CactusBlock) && (belowState.is(Blocks.SAND) || belowState.is(Blocks.RED_SAND))) {
					if (this.level.getBlockState(pos.above()).getBlock() instanceof CactusBlock) {
						this.level.destroyBlock(pos.above(), true, player);
					}
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof SugarCaneBlock block && !(belowState.getBlock() instanceof SugarCaneBlock) && block.canSurvive(state, level, pos)) {
					if (this.level.getBlockState(pos.above()).getBlock() instanceof SugarCaneBlock) {
						this.level.destroyBlock(pos.above(), true, player);
					}
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				}
				if (replanted) {
					entry.getValue().hurtAndBreak(3, this.player, (player) -> {
						EquipmentSlot slot = entry.getKey();
						if (slot != null) {
							player.broadcastBreakEvent(slot);
						}
					});
				}
			} else {
				XSurvive.LOGGER.error("Fail to apply replanting logic, since a previous Mod modified the Block");
			}
		}
	}
}
