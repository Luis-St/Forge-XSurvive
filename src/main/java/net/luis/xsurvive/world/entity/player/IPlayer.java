package net.luis.xsurvive.world.entity.player;

import net.luis.xsurvive.capability.ICapability;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@AutoRegisterCapability
public interface IPlayer extends ICapability, INetworkCapability {
	
	@NotNull Player getPlayer();
	
	default @NotNull Level getLevel() {
		return this.getPlayer().level();
	}
	
	void tick();
	
	int getFrostTime();
	
	default void setFrostTime(int frostTime) {}
	
	double getFrostPercent();
	
	int getEndAspectCooldown();
	
	default void setEndAspectCooldown(int endAspectCooldown) {}
	
	double getEndAspectPercent();
	
	@NotNull CombinedInvWrapper getCombinedInventory();
	
	@NotNull Optional<@Nullable BlockPos> getContainerPos();
	
	@NotNull CompoundTag serializePersistent();
	
	void deserializePersistent(@NotNull CompoundTag tag);
}
