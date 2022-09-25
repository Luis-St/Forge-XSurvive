package net.luis.xsurvive.event.level;

import com.mojang.datafixers.util.Pair;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.WoodHarvester;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.LevelEvent.CreateSpawnPosition;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
			Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator().findNearestMapStructure(level, getHolderSet(level.getLevel().registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY)), BlockPos.ZERO, 100, false);
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
	
	private static HolderSet<Structure> getHolderSet(Registry<Structure> registry) {
		return Structures.VILLAGE_PLAINS.unwrap().left().map(registry::getHolderOrThrow).map(HolderSet::direct).orElseThrow();
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
	
	@SubscribeEvent
	public static void explosionDetonate(ExplosionEvent.Detonate event) {
		Explosion explosion = event.getExplosion();
		if (event.getLevel() instanceof ServerLevel level) {
			if (level.dimension().equals(Level.NETHER) && explosion.getExploder() instanceof PrimedTnt) {
				BlockPos pos = new BlockPos(explosion.getPosition());
				if (pos.getY() >= 124 && level.getBlockState(pos.below()).is(Blocks.BEDROCK)) {
					level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		}
	}
	
}