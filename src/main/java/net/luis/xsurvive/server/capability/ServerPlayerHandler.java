/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.server.capability;

import net.luis.xsurvive.capability.handler.AbstractPlayerHandler;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdatePlayerCapabilityPacket;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Luis-St
 *
 */

public class ServerPlayerHandler extends AbstractPlayerHandler {
	
	private int nextPhantomReset;
	private int lastSync;
	private boolean changed;
	private @Nullable BlockPos possibleContainerPos;
	
	public ServerPlayerHandler(@NotNull ServerPlayer player) {
		super(player);
	}
	
	@Override
	public void tick() {
		super.tick();
		if (this.getPlayer().getRemainingFireTicks() > 0 || this.getLevel().dimensionType().ultraWarm()) {
			if (this.getPlayer().removeEffect(XSMobEffects.FROST.getHolder().orElseThrow())) {
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
	
	public void setContainerPos(@Nullable BlockPos pos) {
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
	public @NotNull CompoundTag serializeDisk(HolderLookup.@NotNull Provider lookup) {
		CompoundTag tag = super.serializeDisk(lookup);
		tag.putInt("next_phantom_reset", this.nextPhantomReset);
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializeDisk(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		super.deserializeDisk(lookup, tag);
		this.nextPhantomReset = tag.getInt("next_phantom_reset");
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		
	}
	
	@Override
	public @NotNull CompoundTag serializePersistent(HolderLookup.@NotNull Provider lookup) {
		CompoundTag tag = super.serializePersistent(lookup);
		tag.putInt("last_sync", this.lastSync);
		tag.putBoolean("changed", this.changed);
		return tag;
	}
	
	@Override
	public void deserializePersistent(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		super.deserializePersistent(lookup, tag);
		this.lastSync = tag.getInt("last_sync");
		this.changed = tag.getBoolean("changed");
	}
	//endregion
}
