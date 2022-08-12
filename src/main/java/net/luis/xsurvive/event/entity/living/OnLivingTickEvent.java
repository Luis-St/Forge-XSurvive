package net.luis.xsurvive.event.entity.living;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSurviveBlockTags;
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
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Luis-st
 *
 */

@Mod.EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnLivingTickEvent {
	
	@SubscribeEvent
	public static void livingTick(LivingEvent.LivingTickEvent event) {
		if (event.getEntity() instanceof ElderGuardian elderGuardian) {
			if (elderGuardian.level instanceof ServerLevel level) {
				AABB boundingBox = elderGuardian.getBoundingBox().inflate(1.5);
				StructureManager manager = level.structureManager();
				if (manager.structureHasPieceAt(elderGuardian.blockPosition(), manager.getStructureWithPieceAt(elderGuardian.blockPosition(), BuiltinStructures.OCEAN_MONUMENT))) {
					for (int x = Mth.floor(boundingBox.minX); x <= Mth.floor(boundingBox.maxX); ++x) {
						for (int y = Mth.floor(boundingBox.minY); y <= Mth.floor(boundingBox.maxY); ++y) {
							for (int z = Mth.floor(boundingBox.minZ); z <= Mth.floor(boundingBox.maxZ); ++z) {
								BlockPos pos = new BlockPos(x, y, z);
								BlockState state = level.getBlockState(pos);
								if (!state.isAir() && !state.is(Blocks.WATER) && !state.is(XSurviveBlockTags.OCEAN_MONUMENT_BLOCKS)) {
									level.destroyBlock(pos, true, elderGuardian);
									level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
								}
							}
						}
					}
				}
			}
		}
	}
	
}

