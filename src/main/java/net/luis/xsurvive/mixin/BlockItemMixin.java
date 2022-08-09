package net.luis.xsurvive.mixin;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;

import net.luis.xsurvive.world.inventory.tooltip.ShulkerBoxTooltip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {
	
	private BlockItemMixin(Properties properties) {
		super(properties);
	}
	
	@Shadow
	public abstract Block getBlock();
	
	@Inject(method = "canFitInsideContainerItems", at = @At("HEAD"), cancellable = true)
	public void canFitInsideContainerItems(CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(true);
	}
	
	@Override
	public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
		if (this.getBlock() instanceof ShulkerBoxBlock) {
			CompoundTag tag = BlockItem.getBlockEntityData(stack);
			if (tag != null && tag.contains("Items", 9)) {
				ListTag listTag = tag.getList("Items", 10);
				List<ItemStack> stacks = Lists.newArrayList();
				stacks.addAll(listTag.stream().map(CompoundTag.class::cast).map(ItemStack::of).toList());
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
