package net.luis.xsurvive.common.capability;

import net.luis.xsurvive.init.XSurviveCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
	
	protected final IPlayerCapability playerCapability;
	protected final LazyOptional<IPlayerCapability> optional;
	
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
		return this.playerCapability.serialize();
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.playerCapability.deserialize(tag);
	}

}
