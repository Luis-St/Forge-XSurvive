package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.capability.handler.AbstractPlayerHandler;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class ServerPlayerHandler extends AbstractPlayerHandler {
	
	private int nextPhantomReset = 0;
	private int lastSync;
	private boolean changed = false;
	private BlockPos possibleContainerPos;
	
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
		if (this.nextPhantomReset > 0) {
			this.nextPhantomReset--;
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
	
	public void setNextPhantomReset(int days) {
		this.nextPhantomReset = 24000 * days;
	}
	
	public boolean canResetPhantomSpawn() {
		return 0 >= this.nextPhantomReset;
	}
	
	public void setContainerPos(BlockPos pos) {
		this.possibleContainerPos = pos == null ? null : pos.immutable();
	}
	
	public void confirmContainerPos() {
		if (this.possibleContainerPos != null) {
			this.containerPos = this.possibleContainerPos;
			this.possibleContainerPos = null;
			this.setChanged();
		}
	}
	
	public void resetContainerPos() {
		if (this.containerPos != null) {
			this.containerPos = null;
			this.setChanged();
		}
		this.possibleContainerPos = null;
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
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeDisk() {
		CompoundTag tag = super.serializeDisk();
		tag.putInt("next_phantom_reset", this.nextPhantomReset);
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(@NotNull CompoundTag tag) {
		super.deserializeDisk(tag);
		this.nextPhantomReset = tag.getInt("next_phantom_reset");
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		
	}
	
	@Override
	public @NotNull CompoundTag serializePersistent() {
		CompoundTag tag = super.serializePersistent();
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializePersistent(@NotNull CompoundTag tag) {
		super.deserializePersistent(tag);
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	//endregion
}
