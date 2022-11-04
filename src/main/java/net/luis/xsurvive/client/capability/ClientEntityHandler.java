package net.luis.xsurvive.client.capability;

import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

/**
 *
 * @author Luis-st
 *
 */

public class ClientEntityHandler implements IEntity {
	
	private final Entity entity;
	private int tick;
	private EntityFireType fireType = EntityFireType.NONE;
	
	public ClientEntityHandler(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Entity getEntity() {
		return this.entity;
	}
	
	@Override
	public ClientLevel getLevel() {
		return (ClientLevel) this.getEntity().getLevel();
	}
	
	@Override
	public void tick() {
		this.tick++;
	}
	
	@Override
	public EntityFireType getFireType() {
		return this.fireType;
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("fire_type", this.fireType.ordinal());
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.fireType = EntityFireType.byOrdinal(tag.getInt("fire_type"), EntityFireType.NONE);
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		return new CompoundTag();
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.fireType = EntityFireType.byOrdinal(tag.getInt("fire_type"), EntityFireType.NONE);
	}
	
}
