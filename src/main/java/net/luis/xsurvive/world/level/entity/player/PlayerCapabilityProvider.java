package net.luis.xsurvive.world.level.entity.player;

import net.luis.xsurvive.capability.XSurviveCapabilities;
import net.luis.xsurvive.client.capability.LocalPlayerCapabilityHandler;
import net.luis.xsurvive.server.capability.ServerPlayerCapabilityHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * 
 * @author Luis-st
 *
 */

public class PlayerCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IPlayerCapability playerCapability;
	private final LazyOptional<IPlayerCapability> optional;
	
	public PlayerCapabilityProvider(IPlayerCapability playerCapability) {
		this.playerCapability = playerCapability;
		this.optional = LazyOptional.of(() -> this.playerCapability);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		return XSurviveCapabilities.PLAYER.orEmpty(capability, this.optional);
	}

	@Override
	public CompoundTag serializeNBT() {
		return this.playerCapability.serializeDisk();
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.playerCapability.deserializeDisk(tag);
	}
	
	public static IPlayerCapability get(Player player) {
		return player.getCapability(XSurviveCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
	}
	
	public static LocalPlayerCapabilityHandler getLocal(Player player) {
		IPlayerCapability capability = player.getCapability(XSurviveCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerCapabilityHandler handler) {
			return handler;
		} else if (capability instanceof ServerPlayerCapabilityHandler handler) {
			throw new RuntimeException("Fail to get LocalPlayerCapabilityHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static ServerPlayerCapabilityHandler getServer(Player player) {
		IPlayerCapability capability = player.getCapability(XSurviveCapabilities.PLAYER, null).orElseThrow(NullPointerException::new);
		if (capability instanceof LocalPlayerCapabilityHandler handler) {
			throw new RuntimeException("Fail to get ServerPlayerCapabilityHandler from client");
		} else if (capability instanceof ServerPlayerCapabilityHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
}
