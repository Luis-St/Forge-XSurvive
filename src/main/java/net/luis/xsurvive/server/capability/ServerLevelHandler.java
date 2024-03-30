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

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.capability.handler.AbstractLevelHandler;
import net.luis.xsurvive.network.XSNetworkHandler;
import net.luis.xsurvive.network.packet.UpdateLevelCapabilityPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.core.registries.BuiltInRegistries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("deprecation")
public class ServerLevelHandler extends AbstractLevelHandler {
	
	public ServerLevelHandler(Level level) {
		super(level);
	}
	
	public void addBeaconPosition(@NotNull BlockPos pos) {
		this.beaconPositions.add(pos);
		this.broadcastChanges();
	}
	
	public void removeBeaconPosition(BlockPos pos) {
		this.beaconPositions.remove(pos);
		this.broadcastChanges();
	}
	
	public void setBeaconEffects(@NotNull BlockPos pos, MobEffect primaryEffect, MobEffect secondaryEffect) {
		int primaryId = MOB_EFFECT.getId(primaryEffect);
		int secondaryId = MOB_EFFECT.getId(secondaryEffect);
		if (this.beaconEffects.containsKey(pos)) {
			Pair<Integer, Integer> pair = this.beaconEffects.get(pos);
			if (pair.getFirst() == primaryId && pair.getSecond() == secondaryId) {
				return;
			}
		}
		this.beaconEffects.put(pos, new Pair<>(primaryId, secondaryId));
		this.broadcastChanges();
	}
	
	public void removeBeaconEffects(BlockPos pos) {
		this.beaconEffects.remove(pos);
		this.broadcastChanges();
	}
	
	@Override
	public void broadcastChanges() {
		XSNetworkHandler.INSTANCE.sendToPlayersInLevel((ServerLevel) this.getLevel(), new UpdateLevelCapabilityPacket(this.serializeNetwork()));
	}
	
	//region NBT
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
	
	}
	//endregion
}
