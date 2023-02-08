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
 * @author Luis-st
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
		return manager.structureHasPieceAt(pos, manager.getStructureWithPieceAt(pos, BuiltinStructures.OCEAN_MONUMENT));
	}
	
	@Override
	public void tick() {
		if (ForgeEventFactory.getMobGriefingEvent(this.level, this.entity)) {
			AABB boundingBox = this.entity.getBoundingBox().inflate(1.5);
			for (int x = Mth.floor(boundingBox.minX); x <= Mth.floor(boundingBox.maxX); ++x) {
				for (int y = Mth.floor(boundingBox.minY); y <= Mth.floor(boundingBox.maxY); ++y) {
					for (int z = Mth.floor(boundingBox.minZ); z <= Mth.floor(boundingBox.maxZ); ++z) {
						BlockPos pos = new BlockPos(x, y, z);
						BlockState state = level.getBlockState(pos);
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
