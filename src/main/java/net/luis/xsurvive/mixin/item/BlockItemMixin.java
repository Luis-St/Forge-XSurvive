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

package net.luis.xsurvive.mixin.item;

import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxTooltip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {
	
	//region Mixin
	private BlockItemMixin(Properties properties) {
		super(properties);
	}
	
	@Shadow
	public abstract Block getBlock();
	//endregion
	
	@Inject(method = "canFitInsideContainerItems", at = @At("HEAD"), cancellable = true)
	public void canFitInsideContainerItems(@NotNull CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(true);
	}
	
	@Override
	public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
		if (this.getBlock() instanceof ShulkerBoxBlock) {
			CompoundTag tag = BlockItem.getBlockEntityData(stack);
			if (tag != null && tag.contains("Items", 9)) {
				ListTag listTag = tag.getList("Items", 10);
				List<ItemStack> stacks = new ArrayList<>(listTag.stream().map(CompoundTag.class::cast).map(ItemStack::of).toList());
				return Optional.of(new ShulkerBoxTooltip(stacks));
			}
		}
		return super.getTooltipImage(stack);
	}
	
	@Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag, CallbackInfo callback) {
		if (this.getBlock() instanceof ShulkerBoxBlock) {
			callback.cancel();
		}
	}
}
