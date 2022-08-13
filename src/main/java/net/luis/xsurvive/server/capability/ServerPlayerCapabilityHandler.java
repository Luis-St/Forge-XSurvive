package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.network.XSurviveNetworkHandler;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.luis.xsurvive.world.effect.XSurviveMobEffects;
import net.luis.xsurvive.world.level.entity.player.IPlayerCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

/**
 * 
 * @author Luis-st
 *
 */

public class ServerPlayerCapabilityHandler implements IPlayerCapability {
	
	private final ServerPlayer player;
	private int tick;
	private int frostTime;
	private int startFrostTime;
	private int lastSync;
	private boolean changed = false;
	
	public ServerPlayerCapabilityHandler(ServerPlayer player) {
		this.player = player;
	}
	
	@Override
	public ServerPlayer getPlayer() {
		return this.player;
	}
	
	@Override
	public ServerLevel getLevel() {
		return this.getPlayer().getLevel();
	}
	
	@Override
	public void tick() {
		this.tick++;
		if (this.player.getRemainingFireTicks() > 0 || this.getLevel().dimensionType().ultraWarm()) {
			if (this.player.removeEffect(XSurviveMobEffects.FROST.get())) {
				this.frostTime = 0;
				this.setChanged();
			}
		}
		if (this.frostTime > 0) {
			this.frostTime--;
		} else {
			this.frostTime = 0;
		}
		if (this.changed) {
			this.broadcastChanges();
			this.lastSync = 0;
			this.changed = false;
		}
		this.lastSync++;
	}
	
	@Override
	public int getFrostTime() {
		return this.frostTime;
	}
	
	@Override
	public void setFrostTime(int frostTime) {
		this.frostTime = frostTime;
		this.startFrostTime = frostTime;
		this.setChanged();
	}
	
	@Override
	public float getFrostPercent() {
		double showStartTime = ((double) this.startFrostTime - (double) this.frostTime) / 20.0;
		double showEndTime = ((double) this.frostTime) / 20.0;
		if (showStartTime > 5.0 && showEndTime > 5.0) {
			return 1.0F;
		} else if (5.0 >= showStartTime) {
			return (float) showStartTime / 5.0F;
		} else if (5.0 >= showEndTime) {
			return (float) showEndTime / 5.0F;
		}
		return 0.0F;
	}
	
	@Override
	public void setChanged() {
		this.changed = true;
	}

	@Override
	public void broadcastChanges() {
		XSurviveNetworkHandler.sendToPlayer(this.player, new UpdatePlayerCapabilityPacket(this.serializeNetwork()));
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.frostTime = tag.getInt("frost_time");
		this.startFrostTime = tag.getInt("start_frost_time");
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		return tag;
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		
	}
	
	@Override
	public CompoundTag serializePersistent() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializePersistent(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
}
