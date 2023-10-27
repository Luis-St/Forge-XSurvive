package net.luis.xsurvive.mixin.item;

import com.google.common.collect.Lists;
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
