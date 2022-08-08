package net.luis.xsurvive.world.level.block.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.luis.xsurvive.world.item.crafting.XSurviveRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 
 * @author Luis-st
 *
 */

public class SmeltingFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

	public SmeltingFurnaceBlockEntity(BlockPos pos, BlockState state) {
		super(XSurviveBlockEntityTypes.SMELTING_FURNACE.get(), pos, state, XSurviveRecipeTypes.SMELTING.get());
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable(XSurvive.MOD_ID + ".container.smelting_furnace");
	}
	
	@Override
	protected int getBurnDuration(ItemStack stack) {
		return super.getBurnDuration(stack) / 2;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new SmeltingFurnaceMenu(id, inventory, this, this.dataAccess);
	}

}
