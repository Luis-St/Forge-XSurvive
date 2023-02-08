package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractEntityHandler;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

/**
 *
 * @author Luis-st
 *
 */

public class ClientEntityHandler extends AbstractEntityHandler {
	
	public ClientEntityHandler(Entity entity) {
		super(entity);
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
	
}
