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

package net.luis.xsurvive.capability.handler;

import net.luis.xsurvive.world.entity.player.IPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author Luis-St
 *
 */

public class AbstractPlayerHandler implements IPlayer {
	
	private final Player player;
	private final ItemStackHandler enderChest = new ItemStackHandler(27);
	private final Supplier<CombinedInvWrapper> combinedInventory = () -> new CombinedInvWrapper(new InvWrapper(this.getPlayer().getEnderChestInventory()), this.enderChest);
	private int tick;
	protected int frostTime;
	protected int startFrostTime;
	protected int endAspectCooldown;
	protected int startEndAspectCooldown;
	@Nullable protected BlockPos containerPos;
	
	protected AbstractPlayerHandler(@NotNull Player player) {
		this.player = player;
	}
	
	@Override
	public @NotNull Player getPlayer() {
		return this.player;
	}
	
	@Override
	public @NotNull Level getLevel() {
		return this.getPlayer().level();
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
			this.startEndAspectCooldown = 0;
		}
	}
	
	@Override
	public int getFrostTime() {
		return this.frostTime;
	}
	
	@Override
	public double getFrostPercent() {
		double showStartTime = (this.startFrostTime - (double) this.frostTime) / 20.0;
		double showEndTime = this.frostTime / 20.0;
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
		return Mth.clamp(this.endAspectCooldown / (double) this.startEndAspectCooldown, 0.0, 1.0);
	}
	
	@Override
	public @NotNull CombinedInvWrapper getCombinedInventory() {
		return this.combinedInventory.get();
	}
	
	public @NotNull Optional<@Nullable BlockPos> getContainerPos() {
		return Optional.ofNullable(this.containerPos);
	}
	
	//region NBT
	private @NotNull CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.putInt("frost_time", this.frostTime);
		tag.putInt("start_frost_time", this.startFrostTime);
		tag.putInt("end_aspect_cooldown", this.endAspectCooldown);
		tag.putInt("start_end_aspect_cooldown", this.startEndAspectCooldown);
		return tag;
	}
	
	private void deserialize(@NotNull CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.frostTime = tag.getInt("frost_time");
		this.startFrostTime = tag.getInt("start_frost_time");
		this.endAspectCooldown = tag.getInt("end_aspect_cooldown");
		this.startEndAspectCooldown = tag.getInt("start_end_aspect_cooldown");
	}
	
	@Override
	public @NotNull CompoundTag serializeDisk(HolderLookup.@NotNull Provider lookup) {
		CompoundTag tag = this.serialize();
		tag.put("ender_chest", this.enderChest.serializeNBT(lookup));
		return tag;
	}
	
	@Override
	public void deserializeDisk(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		this.deserialize(tag);
		this.enderChest.deserializeNBT(lookup, tag.getCompound("ender_chest"));
	}
	
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		CompoundTag tag = this.serialize();
		if (this.containerPos != null) {
			tag.put("container_pos", NbtUtils.writeBlockPos(this.containerPos));
		}
		return tag;
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		this.deserialize(tag);
		if (tag.contains("container_pos")) {
			this.containerPos = NbtUtils.readBlockPos(tag, "container_pos").orElseThrow();
		} else {
			this.containerPos = null;
		}
	}
	
	@Override
	public @NotNull CompoundTag serializePersistent(HolderLookup.@NotNull Provider lookup) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tick", this.tick);
		tag.put("ender_chest", this.enderChest.serializeNBT(lookup));
		return tag;
	}
	
	@Override
	public void deserializePersistent(HolderLookup.@NotNull Provider lookup, @NotNull CompoundTag tag) {
		this.tick = tag.getInt("tick");
		this.enderChest.deserializeNBT(lookup, tag.getCompound("ender_chest"));
	}
	//endregion
}
