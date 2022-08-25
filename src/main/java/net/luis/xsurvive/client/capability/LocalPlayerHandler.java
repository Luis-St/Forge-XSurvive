package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.world.level.entity.player.IPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

/**
 * 
 * @author Luis-st
 *
 */

public class LocalPlayerHandler implements IPlayer {
	
	private final LocalPlayer player;
	private int tick;
	private int frostTime;
	private int startFrostTime;
	private int endAspectCooldown;
	private int startEndAspectCooldown;
	
	public LocalPlayerHandler(LocalPlayer player) {
		this.player = player;
	}
	
	@Override
	public LocalPlayer getPlayer() {
		return this.player;
	}
	
	@Override
	public ClientLevel getLevel() {
		return (ClientLevel) this.player.getLevel();
	}
	
	@Override
	public void tick() {
		this.tick++;
		if (this.frostTime > 0) {
			this.frostTime--;
		}
		if (this.endAspectCooldown > 0) {
			this.endAspectCooldown--;
		}
	}
	
	@Override
	public int getFrostTime() {
		return this.frostTime;
	}
	
	@Override
	public double getFrostPercent() {
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
	public int getEndAspectCooldown() {
		return this.endAspectCooldown;
	}
	
	@Override
	public double getEndAspectPercent() {
		return Mth.clamp((double) this.endAspectCooldown / (double) this.startEndAspectCooldown, 0.0, 1.0);
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		tag.putInt("end_aspect_cooldown", this.endAspectCooldown);
		tag.putInt("start_end_aspect_cooldown", this.startEndAspectCooldown);
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.frostTime = tag.getInt("frost_time");
		this.startFrostTime = tag.getInt("start_frost_time");
		this.endAspectCooldown = tag.getInt("end_aspect_cooldown");
		this.startEndAspectCooldown = tag.getInt("start_end_aspect_cooldown");
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.frostTime = tag.getInt("frost_time");
		this.startFrostTime = tag.getInt("start_frost_time");
		this.endAspectCooldown = tag.getInt("end_aspect_cooldown");
		this.startEndAspectCooldown = tag.getInt("start_end_aspect_cooldown");
	}
	
	@Override
	public CompoundTag serializePersistent() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		return tag;
	}
	
	@Override
	public void deserializePersistent(CompoundTag tag) {
		this.tick = tag.getInt("tick");
	}

}
