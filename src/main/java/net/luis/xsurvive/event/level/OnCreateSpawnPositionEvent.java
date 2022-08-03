package net.luis.xsurvive.event.level;

import com.mojang.datafixers.util.Pair;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.event.level.LevelEvent.CreateSpawnPosition;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnCreateSpawnPositionEvent {
	
	@SubscribeEvent
	public static void createSpawnPosition(CreateSpawnPosition event) {
		if (event.getLevel() instanceof ServerLevel level) {
			Pair<BlockPos, Holder<Biome>> pair = level.findClosestBiome3d((holder) -> {
				return holder.is(Biomes.SPARSE_JUNGLE);
			}, BlockPos.ZERO, 6400, 32, 64);
			if (pair != null) {
				ChunkPos pos = level.getChunk(pair.getFirst()).getPos();
				BlockPos spawnPos = getSpawnPos(level, pos);
				if (spawnPos != null) {
					event.getSettings().setSpawn(spawnPos, 0.0F);
					XSurvive.LOGGER.info("Set world spawn to {}", spawnPos.toShortString());
					event.setCanceled(true);
				}
			}
		}
	}
	
	private static BlockPos getSpawnPos(ServerLevel level, ChunkPos pos) {
		BlockPos spawnPos = PlayerRespawnLogic.getSpawnPosInChunk(level, pos);
		for (int x = -7; x <= 7; x++) {
			for (int z = -7; z <= 7; z++) {
				if (spawnPos != null) {
					return spawnPos;
				}
				spawnPos = PlayerRespawnLogic.getSpawnPosInChunk(level, new ChunkPos(pos.x + x, pos.z + z));
			}
		}
		return null;
	}
	
}
