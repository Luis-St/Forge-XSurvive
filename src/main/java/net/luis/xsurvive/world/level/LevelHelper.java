package net.luis.xsurvive.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 *
 * @author Luis-st
 *
 */

public class LevelHelper {
	
	public static DifficultyInstance getCurrentDifficultyAt(Level level, BlockPos pos) {
		LevelChunk chunk = level.getChunkSource().getChunkNow(pos.getX() >> 4, pos.getZ() >> 4);
		if (chunk == null || !chunk.getStatus().isOrAfter(ChunkStatus.FULL)) {
			return new DifficultyInstance(level.getDifficulty(), level.getDayTime(), 0, level.getMoonBrightness());
		}
		return level.getCurrentDifficultyAt(pos);
	}
	
}
