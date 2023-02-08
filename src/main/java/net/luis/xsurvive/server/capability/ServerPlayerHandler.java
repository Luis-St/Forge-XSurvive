package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.capability.handler.AbstractPlayerHandler;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

/**
 *
 * @author Luis-st
 *
 */

public class ServerPlayerHandler extends AbstractPlayerHandler {
	
	private int lastSync;
	private boolean changed = false;
	
	public ServerPlayerHandler(ServerPlayer player) {
		super(player);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (this.getPlayer().getRemainingFireTicks() > 0 || this.getLevel().dimensionType().ultraWarm()) {
			if (this.getPlayer().removeEffect(XSMobEffects.FROST.get())) {
				this.frostTime = 0;
				this.setChanged();
			}
		}
		if (this.changed) {
			this.broadcastChanges();
			this.lastSync = 0;
			this.changed = false;
		}
		this.lastSync++;
	}
	
	@Override
	public void setFrostTime(int frostTime) {
		this.frostTime = frostTime;
		this.startFrostTime = frostTime;
		this.setChanged();
	}
	
	@Override
	public void setEndAspectCooldown(int endAspectCooldown) {
		if (endAspectCooldown >= 0) {
			this.endAspectCooldown = endAspectCooldown;
			this.startEndAspectCooldown = endAspectCooldown;
		} else {
			this.endAspectCooldown = 0;
			this.startEndAspectCooldown = 0;
		}
		this.setChanged();
	}
	
	@Override
	public void setChanged() {
		this.changed = true;
	}
	
	@Override
	public void broadcastChanges() {
		XSNetworkHandler.INSTANCE.sendToPlayer(this.getPlayer(), new UpdatePlayerCapabilityPacket(this.serializeNetwork()));
		this.changed = false;
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = super.serializeDisk();
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		super.deserializeDisk(tag);
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		
	}
	
	@Override
	public CompoundTag serializePersistent() {
		CompoundTag tag = super.serializePersistent();
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializePersistent(CompoundTag tag) {
		super.deserializePersistent(tag);
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
}
