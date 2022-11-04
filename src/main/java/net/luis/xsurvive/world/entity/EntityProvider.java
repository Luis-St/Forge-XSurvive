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

/**
 *
 * @author Luis-st
 *
 */

public class EntityProvider implements ICapabilitySerializable<CompoundTag> {
	
	private final IEntity entityCapability;
	private final LazyOptional<IEntity> optional;
	
	public EntityProvider(IEntity playerCapability) {
		this.entityCapability = playerCapability;
		this.optional = LazyOptional.of(() -> this.entityCapability);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		return XSCapabilities.ENTITY.orEmpty(capability, this.optional);
	}

	@Override
	public CompoundTag serializeNBT() {
		return this.entityCapability.serializeDisk();
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.entityCapability.deserializeDisk(tag);
	}
	
	public static IEntity get(Entity entity) {
		return entity.getCapability(XSCapabilities.ENTITY, null).orElseThrow(NullPointerException::new);
	}
	
	public static ClientEntityHandler getClient(Entity entity) {
		IEntity capability = entity.getCapability(XSCapabilities.ENTITY, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientEntityHandler handler) {
			return handler;
		} else if (capability instanceof ServerEntityHandler handler) {
			throw new RuntimeException("Fail to get ClientEntityHandler from server");
		}
		throw new IllegalStateException("Unknown network side");
	}
	
	public static ServerEntityHandler getServer(Entity entity) {
		IEntity capability = entity.getCapability(XSCapabilities.ENTITY, null).orElseThrow(NullPointerException::new);
		if (capability instanceof ClientEntityHandler handler) {
			throw new RuntimeException("Fail to get ServerEntityHandler from client");
		} else if (capability instanceof ServerEntityHandler handler) {
			return handler;
		}
		throw new IllegalStateException("Unknown network side");
	}
	
}
