package net.luis.xsurvive.world.entity.player;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Luis-St
 */

public class PlayerHelper {
	
	public static <T> int getStat(@NotNull ServerPlayer player, @NotNull StatType<T> type, @NotNull T stat) {
		return player.getStats().getValue(type.get(stat));
	}
	
	public static int getStat(@NotNull ServerPlayer player, @NotNull ResourceLocation stat) {
		return player.getStats().getValue(Stats.CUSTOM.get(stat));
	}
	
	public static @NotNull List<ItemStack> getItems(@NotNull Player player, @NotNull Predicate<ItemStack> predicate) {
		IItemHandler handler = player.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(NullPointerException::new);
		List<ItemStack> items = Lists.newArrayList();
		for (int i = 0; i < handler.getSlots(); ++i) {
			if (predicate.test(handler.getStackInSlot(i))) {
				items.add(handler.getStackInSlot(i));
			}
		}
		return items;
	}
}
