package net.luis.xsurvive.event.level;

import com.mojang.datafixers.util.Pair;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.WoodHarvester;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.event.level.LevelEvent.CreateSpawnPosition;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class LevelEventHandler {
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		BlockPos pos = new BlockPos(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
		BlockState state = event.getState();
		int xp = event.getExpToDrop();
		int experience = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.EXPERIENCE.get(), player);
		int multiDrop = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.MULTI_DROP.get(), player);
		int fortune = XSEnchantmentHelper.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player);
		int blasting = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.BLASTING.get(), player);
		int harvesting = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.HARVESTING.get(), player);
		if (xp > 0 && experience > 0) {
			event.setExpToDrop((xp * ((experience + 1) * ((experience * 2) + fortune))) * (multiDrop + 1));
		}
		if (blasting > 0) {
			player.level.explode(player, pos.getX(), pos.getY(), pos.getZ(), 2.0F * (blasting + 1), Explosion.BlockInteraction.BREAK);
		}
		if (harvesting > 0) {
			if (event.getState().is(BlockTags.LOGS)) {
				WoodHarvester harvester = new WoodHarvester((Level) event.getLevel(), pos, state, harvesting, player);
				harvester.harvest();
			}
		}
	}
	
	@SubscribeEvent
	public static void createFluidSource(CreateFluidSourceEvent event) {
		if (event.getLevel() instanceof Level level && level.dimension().equals(Level.NETHER)) {
			event.setResult(Result.ALLOW);
		}
	}
	
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