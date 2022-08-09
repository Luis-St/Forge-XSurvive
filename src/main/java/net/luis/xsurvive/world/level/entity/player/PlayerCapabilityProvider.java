package net.luis.xsurvive.world.level.entity.player;

import net.luis.xsurvive.world.capability.XSurviveCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
		return capability == XSurviveCapabilities.PLAYER ? this.optional.cast() : LazyOptional.empty();
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
