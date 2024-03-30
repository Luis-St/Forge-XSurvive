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

package net.luis.xsurvive.world.entity.ai.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class CustomEnderManAi extends AbstractCustomAi<EnderMan> {
	
	public CustomEnderManAi(EnderMan entity, ServerLevel level) {
		super(entity, level);
	}
	
	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null && this.entity.getTarget() instanceof Player;
	}
	
	@Override
	public void tick() {
		if (this.entity.tickCount % 50 == 0 && !this.entity.isInWater() && ForgeEventFactory.getMobGriefingEvent(this.level, this.entity)) {
			this.freezeNearbyWater();
		}
	}
	
	private void freezeNearbyWater() {
		BlockPos.betweenClosedStream(this.entity.getBoundingBox().inflate(5.0, 1.0, 5.0).setMaxY(this.entity.getY() + 1.0)).map(this::immutable).filter((pos) -> {
			return this.level.getBlockState(pos).is(Blocks.WATER) && 0.75 > this.entity.getRandom().nextDouble();
		}).toList().forEach((pos) -> {
			this.level.setBlock(pos, Blocks.FROSTED_ICE.defaultBlockState(), Block.UPDATE_ALL);
		});
	}
	
	private @NotNull BlockPos immutable(@NotNull BlockPos pos) {
		if (pos instanceof MutableBlockPos mutablePos) {
			return mutablePos.immutable();
		}
		return pos;
	}
}
