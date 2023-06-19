package net.luis.xsurvive.world.entity.player;

import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.capability.LocalPlayerHandler;
import net.luis.xsurvive.server.capability.ServerPlayerHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class PlayerProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IPlayer playerCapability;
	private final LazyOptional<IPlayer> optional;
	
	public PlayerProvider(IPlayer playerCapability) {
		this.playerCapability = playerCapability;
		this.optional = LazyOptional.of(() -> this.playerCapability);
	}
	
	public static IPlayer get(Player player) {
		return player.getCapability(XSCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
	}
	
	public static LocalPlayerHandler getLocal(Player player) {
		IPlayer capability = player.getCapability(XSCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerHandler handler) {
			return handler;
		} else if (capability instanceof ServerPlayerHandler handler) {
			throw new RuntimeException("Fail to get LocalPlayerHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static ServerPlayerHandler getServer(Player player) {
		IPlayer capability = player.getCapability(XSCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerHandler handler) {
			throw new RuntimeException("Fail to get ServerPlayerHandler from client");
		} else if (capability instanceof ServerPlayerHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side) {
		return XSCapabilities.PLAYER.orEmpty(capability, this.optional);
	}
	
	@Override
	public CompoundTag serializeNBT() {
		return this.playerCapability.serializeDisk();
	}
	
	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.playerCapability.deserializeDisk(tag);
	}
}
