package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractEntityHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

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
	public @NotNull CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
}
