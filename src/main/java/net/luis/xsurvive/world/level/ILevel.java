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

package net.luis.xsurvive.world.level;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.capability.INetworkCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

@AutoRegisterCapability
public interface ILevel extends INetworkCapability {
	
	@NotNull Level getLevel();
	
	default @NotNull List<BlockPos> getBeaconPositions(@NotNull BlockPos pos, int range) {
		return this.getBeaconPositions(new AABB(pos).inflate(range).setMinY(this.getLevel().getMinY()).setMaxY(this.getLevel().getMaxY()));
	}
	
	@NotNull List<BlockPos> getBeaconPositions(@NotNull AABB area);
	
	@NotNull Pair<@Nullable Holder<MobEffect>, @Nullable Holder<MobEffect>> getBeaconEffects(@NotNull BlockPos pos);
	
	default @NotNull Optional<Holder<MobEffect>> getPrimaryBeaconEffect(@NotNull BlockPos pos) {
		return Optional.ofNullable(this.getBeaconEffects(pos).getFirst());
	}
	
}
