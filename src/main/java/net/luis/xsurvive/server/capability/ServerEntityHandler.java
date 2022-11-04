package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateEntityCapabilityPacket;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

/**
 *
 * @author Luis-st
 *
 */

public class ServerEntityHandler implements IEntity {
	
	private final Entity entity;
	private int tick;
	private EntityFireType fireType = EntityFireType.NONE;
	private int lastSync;
	private boolean changed = false;
	
	public ServerEntityHandler(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public Entity getEntity() {
		return this.entity;
	}
	
	@Override
	public ServerLevel getLevel() {
		return (ServerLevel) this.getEntity().getLevel();
	}
	
	@Override
	public void tick() {
		this.tick++;
		if (this.entity.isOnFire() && this.fireType == EntityFireType.NONE) {
			this.fireType = EntityFireType.FIRE;
			this.setChanged();
		} else if (!this.entity.isOnFire() && this.fireType != EntityFireType.NONE) {
			this.fireType = EntityFireType.NONE;
			this.setChanged();
		}
		if (this.changed) {
			this.broadcastChanges();
			this.lastSync = 0;
			this.changed = false;
		}
		this.lastSync++;
	}
	
	@Override
	public EntityFireType getFireType() {
		return this.fireType;
	}
	
	@Override
	public void setFireType(EntityFireType fireType) {
		this.fireType = fireType == null ? EntityFireType.NONE : fireType;
		this.setChanged();
	}
	
	@Override
	public void setChanged() {
		this.changed = true;
	}
	
	@Override
	public void broadcastChanges() {
		XSNetworkHandler.INSTANCE.sendToPlayersInLevel(this.getLevel(), new UpdateEntityCapabilityPacket(this.entity.getId(), this.serializeNetwork()));
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("fire_type", this.fireType.ordinal());
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.fireType = EntityFireType.byOrdinal(tag.getInt("fire_type"), EntityFireType.NONE);
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("fire_type", this.fireType.ordinal());
		return tag;
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		
	}
	
}
