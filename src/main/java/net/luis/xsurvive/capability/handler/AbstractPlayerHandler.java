package net.luis.xsurvive.capability.handler;

import net.luis.xsurvive.world.entity.player.IPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.function.Supplier;

/**
 *
 * @author Luis-st
 *
 */

public class AbstractPlayerHandler implements IPlayer {
	
	private final Player player;
	private final ItemStackHandler enderChest = new ItemStackHandler(27);
	private final Supplier<CombinedInvWrapper> combinedInventory = () -> new CombinedInvWrapper(new InvWrapper(this.getPlayer().getEnderChestInventory()), this.enderChest);
	protected int frostTime;
	protected int startFrostTime;
	protected int endAspectCooldown;
	protected int startEndAspectCooldown;
	private int tick;
	
	public AbstractPlayerHandler(Player player) {
		this.player = player;
	}
	
	@Override
	public Player getPlayer() {
		return this.player;
	}
	
	@Override
	public Level getLevel() {
		return this.getPlayer().getLevel();
	}
	
	@Override
	public void tick() {
		this.tick++;
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
	public CombinedInvWrapper getCombinedInventory() {
		return this.combinedInventory.get();
	}
	
	private CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		tag.putInt("end_aspect_cooldown", this.endAspectCooldown);
		tag.putInt("start_end_aspect_cooldown", this.startEndAspectCooldown);
		return tag;
	}
	
	private void deserialize(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.frostTime = tag.getInt("frost_time");
		this.startFrostTime = tag.getInt("start_frost_time");
		this.endAspectCooldown = tag.getInt("end_aspect_cooldown");
		this.startEndAspectCooldown = tag.getInt("start_end_aspect_cooldown");
	}
	
	@Override
	public CompoundTag serializeDisk() {
		CompoundTag tag = this.serialize();
		tag.put("ender_chest", this.enderChest.serializeNBT());
		return tag;
	}
	
	@Override
	public void deserializeDisk(CompoundTag tag) {
		this.deserialize(tag);
		this.enderChest.deserializeNBT(tag.getCompound("ender_chest"));
	}
	
	@Override
	public CompoundTag serializeNetwork() {
		return this.serialize();
	}
	
	@Override
	public void deserializeNetwork(CompoundTag tag) {
		this.deserialize(tag);
	}
	
	@Override
	public CompoundTag serializePersistent() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.put("ender_chest", this.enderChest.serializeNBT());
		return tag;
	}
	
	@Override
	public void deserializePersistent(CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.enderChest.deserializeNBT(tag.getCompound("ender_chest"));
	}
	
}
