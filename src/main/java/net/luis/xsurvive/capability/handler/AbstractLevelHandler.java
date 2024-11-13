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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.world.level.ILevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static net.minecraft.core.registries.BuiltInRegistries.*;

/**
 *
 * @author Luis-St
 *
 */

public abstract class AbstractLevelHandler implements ILevel {
	
	private final Level level;
	protected final List<BlockPos> beaconPositions = Lists.newArrayList();
	protected final Map<BlockPos, Pair<Integer, Integer>> beaconEffects = Maps.newHashMap();
	
	protected AbstractLevelHandler(@NotNull Level level) {
		this.level = level;
	}
	
	@Override
	public @NotNull Level getLevel() {
		return this.level;
	}
	
	@Override
	public @NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area) {
		List<BlockPos> positions = Lists.newArrayList();
		for (BlockPos position : this.beaconPositions) {
			if (area.contains(position.getX(), position.getY(), position.getZ())) {
				positions.add(position);
			}
		}
		return positions;
	}
	
	@Override
	public @NotNull Pair<@Nullable Holder<MobEffect>, @Nullable Holder<MobEffect>> getBeaconEffects(@NotNull BlockPos pos) {
		if (!this.beaconEffects.containsKey(pos)) {
			return new Pair<>(null, null);
		}
		Pair<Integer, Integer> effects = this.beaconEffects.get(pos);
		return new Pair<>(MOB_EFFECT.getHolder(effects.getFirst()).orElseThrow(), MOB_EFFECT.getHolder(effects.getSecond()).orElseThrow());
	}
	
	//region NBT
	@Override
	public @NotNull CompoundTag serializeNetwork() {
		CompoundTag tag = new CompoundTag();
		ListTag beaconPositions = new ListTag();
		for (BlockPos beaconPosition : this.beaconPositions) {
			beaconPositions.add(NbtUtils.writeBlockPos(beaconPosition));
		}
		tag.put("beacon_positions", beaconPositions);
		ListTag beaconEffects = new ListTag();
		for (Map.Entry<BlockPos, Pair<Integer, Integer>> beaconEffect : this.beaconEffects.entrySet()) {
			CompoundTag beaconEffectTag = new CompoundTag();
			beaconEffectTag.put("pos", NbtUtils.writeBlockPos(beaconEffect.getKey()));
			beaconEffectTag.putInt("primary_effect", beaconEffect.getValue().getFirst());
			beaconEffectTag.putInt("secondary_effect", beaconEffect.getValue().getSecond());
			beaconEffects.add(beaconEffectTag);
		}
		tag.put("beacon_effects", beaconEffects);
		return tag;
	}
	
	@Override
	public void deserializeNetwork(@NotNull CompoundTag tag) {
		this.beaconPositions.clear();
		ListTag beaconPositions = tag.getList("beacon_positions", Tag.TAG_COMPOUND);
		for (Tag beaconPosition : beaconPositions) {
			int[] array = ((IntArrayTag) beaconPosition).getAsIntArray();
			this.beaconPositions.add(new BlockPos(array[0], array[1], array[2]));
		}
		this.beaconEffects.clear();
		ListTag beaconEffects = tag.getList("beacon_effects", Tag.TAG_COMPOUND);
		for (Tag beaconEffect : beaconEffects) {
			CompoundTag beaconEffectTag = (CompoundTag) beaconEffect;
			this.beaconEffects.put(NbtUtils.readBlockPos(beaconEffectTag, "pos").orElseThrow(), new Pair<>(beaconEffectTag.getInt("primary_effect"), beaconEffectTag.getInt("secondary_effect")));
		}
	}
	//endregion
}
