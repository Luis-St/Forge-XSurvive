package net.luis.xsurvive.world.entity.npc;

import net.luis.xsurvive.capability.XSCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class VillagerProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IVillager villagerCapability;
	private final LazyOptional<IVillager> optional;
	
	public VillagerProvider(IVillager playerCapability) {
		this.villagerCapability = playerCapability;
		this.optional = LazyOptional.of(() -> this.villagerCapability);
	}
	
	public static @NotNull IVillager get(@NotNull Villager villager) {
		return villager.getCapability(XSCapabilities.VILLAGER, null).orElseThrow(NullPointerException::new);
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side) {
		return XSCapabilities.VILLAGER.orEmpty(capability, this.optional);
	}
	
	@Override
	public @NotNull CompoundTag serializeNBT() {
		return this.villagerCapability.serializeDisk();
	}
	
	@Override
	public void deserializeNBT(@NotNull CompoundTag tag) {
		this.villagerCapability.deserializeDisk(tag);
	}
}
