package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractLevelHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class ClientLevelHandler extends AbstractLevelHandler {
	
	public ClientLevelHandler(Level level) {
		super(level);
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
	//endregion
}
