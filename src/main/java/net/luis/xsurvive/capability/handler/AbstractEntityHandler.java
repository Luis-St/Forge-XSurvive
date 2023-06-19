package net.luis.xsurvive.capability.handler;

import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

/**
 *
 * @author Luis-st
 *
 */

public class AbstractEntityHandler implements IEntity {
	
	private final Entity entity;
	private int tick;
	protected EntityFireType fireType = EntityFireType.NONE;
	
	public AbstractEntityHandler(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public Entity getEntity() {
		return this.entity;
	}
	
	@Override
	public void tick() {
		this.tick++;
	}
	
	@Override
	public EntityFireType getFireType() {
		return this.fireType;
	}
	
	private CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("fire_type", this.fireType.ordinal());
		return tag;
	}
	
	private void deserialize(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.fireType = EntityFireType.byOrdinal(tag.getInt("fire_type"), EntityFireType.NONE);
	}
	
	@Override
	public CompoundTag serializeDisk() {
		return this.serialize();
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.deserialize(tag);
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		return this.serialize();
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		this.deserialize(tag);
	}
}
