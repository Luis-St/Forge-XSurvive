package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.capability.handler.AbstractPlayerHandler;
import net.luis.xsurvive.world.entity.player.IPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

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
