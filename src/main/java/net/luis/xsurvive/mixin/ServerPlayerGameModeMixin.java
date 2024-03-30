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

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map.Entry;
import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
	
	//region Mixin
	@Shadow protected ServerLevel level;
	@Shadow protected ServerPlayer player;
	//endregion
	
	@Inject(method = "destroyBlock", at = @At(value = "RETURN", ordinal = 5), locals = LocalCapture.CAPTURE_FAILHARD)
	public void destroyBlock(@NotNull BlockPos pos, CallbackInfoReturnable<Boolean> callback, BlockState state) {
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
					Optional<Item> optional = this.level.registryAccess().registryOrThrow(Registries.ITEM).getOptional(block.seed);
					if (optional.isPresent() && optional.get() instanceof BlockItem blockItem && blockItem.getBlock() instanceof StemBlock stemBlock) {
						replanted = this.level.setBlock(pos, stemBlock.defaultBlockState(), 3);
					}
				} else if (state.getBlock() instanceof CocoaBlock && state.getValue(CocoaBlock.AGE) >= 2) {
					replanted = this.level.setBlock(pos, state.setValue(CocoaBlock.AGE, 0), 3);
				} else if (state.getBlock() instanceof BambooSaplingBlock && this.level.getBlockState(pos.below()).is(BlockTags.BAMBOO_PLANTABLE_ON)) {
					replanted = this.level.setBlock(pos, Blocks.BAMBOO_SAPLING.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof BambooStalkBlock && !(belowState.getBlock() instanceof BambooStalkBlock) && belowState.is(BlockTags.BAMBOO_PLANTABLE_ON)) {
					if (this.level.getBlockState(pos.above()).getBlock() instanceof BambooStalkBlock) {
						this.level.destroyBlock(pos.above(), true, this.player);
					}
					replanted = this.level.setBlock(pos, Blocks.BAMBOO_SAPLING.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof CactusBlock block && !(belowState.getBlock() instanceof CactusBlock) && (belowState.is(Blocks.SAND) || belowState.is(Blocks.RED_SAND))) {
					if (this.level.getBlockState(pos.above()).getBlock() instanceof CactusBlock) {
						this.level.destroyBlock(pos.above(), true, this.player);
					}
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() instanceof SugarCaneBlock block && !(belowState.getBlock() instanceof SugarCaneBlock) && block.canSurvive(state, this.level, pos)) {
					if (this.level.getBlockState(pos.above()).getBlock() instanceof SugarCaneBlock) {
						this.level.destroyBlock(pos.above(), true, this.player);
					}
					replanted = this.level.setBlock(pos, block.defaultBlockState(), 3);
				} else if (state.getBlock() == Blocks.TORCHFLOWER) {
					replanted = this.level.setBlock(pos, Blocks.TORCHFLOWER_CROP.defaultBlockState(), 3);
				} else if (state.getBlock() == Blocks.PITCHER_CROP && state.getValue(PitcherCropBlock.AGE) >= 4) {
					replanted = this.level.setBlock(pos, Blocks.PITCHER_CROP.defaultBlockState(), 3);
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
