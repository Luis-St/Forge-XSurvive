package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.capability.XSCapabilities;
import net.luis.xsurvive.client.capability.ClientEntityHandler;
import net.luis.xsurvive.server.capability.ServerEntityHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class EntityProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IEntity entityCapability;
	private final LazyOptional<IEntity> optional;
	
	public EntityProvider(IEntity entityCapability) {
		this.entityCapability = entityCapability;
		this.optional = LazyOptional.of(() -> this.entityCapability);
	}
	
	public static @NotNull IEntity get(@NotNull Entity entity) {
		return entity.getCapability(XSCapabilities.ENTITY, null).orElseThrow(NullPointerException::new);
	}
	
	public static @NotNull LazyOptional<IEntity> getSafe(@NotNull Entity entity) {
		return entity.getCapability(XSCapabilities.ENTITY, null);
	}
	
	public static @NotNull ClientEntityHandler getClient(@NotNull Entity entity) {
		IEntity capability = entity.getCapability(XSCapabilities.ENTITY, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientEntityHandler handler) {
			return handler;
		} else if (capability instanceof ServerEntityHandler handler) {
			throw new RuntimeException("Fail to get ClientEntityHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static @NotNull ServerEntityHandler getServer(@NotNull Entity entity) {
		IEntity capability = entity.getCapability(XSCapabilities.ENTITY, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientEntityHandler handler) {
			throw new RuntimeException("Fail to get ServerEntityHandler from client");
		} else if (capability instanceof ServerEntityHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction side) {
		return XSCapabilities.ENTITY.orEmpty(capability, this.optional);
	}
	
	@Override
	public @NotNull CompoundTag serializeNBT() {
		return this.entityCapability.serializeDisk();
	}
	
	@Override
	public void deserializeNBT(@NotNull CompoundTag tag) {
		this.entityCapability.deserializeDisk(tag);
	}
}
