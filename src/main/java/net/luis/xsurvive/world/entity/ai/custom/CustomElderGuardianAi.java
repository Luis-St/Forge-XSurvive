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

import net.luis.xsurvive.tag.XSBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;

/**
 *
 * @author Luis-St
 *
 */

public class CustomElderGuardianAi extends AbstractCustomAi<ElderGuardian> {
	
	public CustomElderGuardianAi(ElderGuardian entity, ServerLevel level) {
		super(entity, level);
	}
	
	@Override
	public boolean canUse() {
		StructureManager manager = this.level.structureManager();
		BlockPos pos = this.entity.blockPosition();
		return manager.structureHasPieceAt(pos, manager.getStructureWithPieceAt(pos, holder -> holder.is(BuiltinStructures.OCEAN_MONUMENT))) && this.level.getSeaLevel() > pos.getY();
	}
	
	@Override
	public void tick() {
		if (ForgeEventFactory.getMobGriefingEvent(this.level, this.entity)) {
			AABB boundingBox = this.entity.getBoundingBox().inflate(1.5);
			for (int x = Mth.floor(boundingBox.minX); x <= Mth.floor(boundingBox.maxX); ++x) {
				for (int y = Mth.floor(boundingBox.minY); y <= Mth.floor(boundingBox.maxY); ++y) {
					for (int z = Mth.floor(boundingBox.minZ); z <= Mth.floor(boundingBox.maxZ); ++z) {
						BlockPos pos = new BlockPos(x, y, z);
						BlockState state = this.level.getBlockState(pos);
						if (!state.isAir() && !state.is(Blocks.WATER) && !state.is(XSBlockTags.OCEAN_MONUMENT_BLOCKS)) {
							this.level.destroyBlock(pos, true, this.entity);
							this.level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
						}
					}
				}
			}
		}
	}
}
