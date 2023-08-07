package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractEntityHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class ClientEntityHandler extends AbstractEntityHandler {
	
	public ClientEntityHandler(Entity entity) {
		super(entity);
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
	//endregion
}
