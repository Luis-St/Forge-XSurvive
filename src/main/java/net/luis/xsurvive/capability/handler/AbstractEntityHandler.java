package net.luis.xsurvive.capability.handler;

import net.luis.xsurvive.world.entity.EntityFireType;
import net.luis.xsurvive.world.entity.IEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class AbstractEntityHandler implements IEntity {
	
	private final Entity entity;
	private int tick;
	protected EntityFireType fireType = EntityFireType.NONE;
	
	protected AbstractEntityHandler(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public @NotNull Entity getEntity() {
		return this.entity;
	}
	
	@Override
	public void tick() {
		this.tick++;
	}
	
	@Override
	public @NotNull EntityFireType getFireType() {
		return this.fireType;
	}
	
	//region NBT
	private @NotNull CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("fire_type", this.fireType.ordinal());
		return tag;
	}
	
	private void deserialize(@NotNull CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.fireType = EntityFireType.byOrdinal(tag.getInt("fire_type"), EntityFireType.NONE);
	}
	
	@Override
	public @NotNull CompoundTag serializeDisk() {
		return this.serialize();
	}
	
	@Override
	public void deserializeDisk(@NotNull CompoundTag tag) {
		this.deserialize(tag);
	}
	
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		return this.serialize();
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		this.deserialize(tag);
	}
	//endregion
}
