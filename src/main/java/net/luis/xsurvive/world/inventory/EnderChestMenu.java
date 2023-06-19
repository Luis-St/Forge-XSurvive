package net.luis.xsurvive.world.inventory;

import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class EnderChestMenu extends AbstractContainerMenu {
	
	public EnderChestMenu(int id, Inventory inventory, FriendlyByteBuf data) {
		this(id, inventory);
	}
	
	public EnderChestMenu(int id, Inventory inventory) {
		super(XSMenuTypes.ENDER_CHEST.get(), id);
		Player player = inventory.player;
		IItemHandlerModifiable handler = PlayerProvider.get(player).getCombinedInventory();
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new SlotItemHandler(handler, j + i * 9, 8 + j * 18, (i * 18) + 18));
			}
		}
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 103 + i * 18 + 36));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(inventory, i, 8 + i * 18, 161 + 36));
		}
	}
	
	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack = slot.getItem();
			stack = itemStack.copy();
			if (index < 54) {
				if (!this.moveItemStackTo(itemStack, 54, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemStack, 0, 54, false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return stack;
	}
	
	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}
}
