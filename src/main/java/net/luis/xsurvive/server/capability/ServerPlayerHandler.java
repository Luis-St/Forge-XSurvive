package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.level.entity.player.IPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;

/**
 * 
 * @author Luis-st
 *
 */

public class ServerPlayerHandler implements IPlayer {
	
	private final ServerPlayer player;
	private int tick;
	private int frostTime;
	private int startFrostTime;
	private int endAspectCooldown;
	private int startEndAspectCooldown;
	private int lastSync;
	private boolean changed = false;
	
	public ServerPlayerHandler(ServerPlayer player) {
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
			if (this.player.removeEffect(XSMobEffects.FROST.get())) {
				this.frostTime = 0;
				this.setChanged();
			}
		}
		if (this.frostTime > 0) {
			this.frostTime--;
		} else {
			this.frostTime = 0;
		}
		if (this.endAspectCooldown > 0) {
			this.endAspectCooldown--;
		} else {
			this.endAspectCooldown = 0;
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
	public double getFrostPercent() {
		double showStartTime = ((double) this.startFrostTime - (double) this.frostTime) / 20.0;
		double showEndTime = ((double) this.frostTime) / 20.0;
		if (showStartTime > 5.0 && showEndTime > 5.0) {
			return 1.0;
		} else if (5.0 >= showStartTime) {
			return (float) showStartTime / 5.0;
		} else if (5.0 >= showEndTime) {
			return (float) showEndTime / 5.0;
		}
		return 0.0;
	}
	
	@Override
	public int getEndAspectCooldown() {
		return this.endAspectCooldown;
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
	public double getEndAspectPercent() {
		return Mth.clamp((double) this.endAspectCooldown / (double) this.startEndAspectCooldown, 0.0, 1.0);
	}
	
	@Override
	public void setChanged() {
		this.changed = true;
	}

	@Override
	public void broadcastChanges() {
		XSNetworkHandler.sendToPlayer(this.player, new UpdatePlayerCapabilityPacket(this.serializeNetwork()));
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		tag.putInt("end_aspect_cooldown", this.endAspectCooldown);
		tag.putInt("start_end_aspect_cooldown", this.startEndAspectCooldown);
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.frostTime = tag.getInt("frost_time");
		this.startFrostTime = tag.getInt("start_frost_time");
		this.endAspectCooldown = tag.getInt("end_aspect_cooldown");
		this.startEndAspectCooldown = tag.getInt("start_end_aspect_cooldown");
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		tag.putInt("end_aspect_cooldown", this.endAspectCooldown);
		tag.putInt("start_end_aspect_cooldown", this.startEndAspectCooldown);
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
