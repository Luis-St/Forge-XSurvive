package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractPlayerHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;

/**
 *
 * @author Luis-st
 *
 */

public class LocalPlayerHandler extends AbstractPlayerHandler {
	
	public LocalPlayerHandler(LocalPlayer player) {
		super(player);
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
}
