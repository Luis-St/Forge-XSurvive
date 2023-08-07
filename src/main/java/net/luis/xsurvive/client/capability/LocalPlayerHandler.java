package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractPlayerHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class LocalPlayerHandler extends AbstractPlayerHandler {
	
	public LocalPlayerHandler(LocalPlayer player) {
		super(player);
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
	//endregion
}
