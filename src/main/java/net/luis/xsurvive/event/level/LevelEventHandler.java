package net.luis.xsurvive.event.level;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.block.WoodHarvester;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.LevelEvent.CreateSpawnPosition;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class LevelEventHandler {
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.@NotNull BreakEvent event) {
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
			player.level().explode(player, pos.getX(), pos.getY(), pos.getZ(), 2.0F * (blasting + 1), Level.ExplosionInteraction.NONE);
		}
		if (harvesting > 0 && event.getState().is(BlockTags.LOGS) && event.getLevel() instanceof Level level) {
			new WoodHarvester(level, pos, state, harvesting, player).harvest();
		}
	}
	
	@SubscribeEvent
	public static void createFluidSource(@NotNull CreateFluidSourceEvent event) {
		if (event.getLevel().dimension().equals(Level.NETHER)) {
			event.setResult(Result.ALLOW);
		}
	}
	
	@SubscribeEvent
	public static void createSpawnPosition(@NotNull CreateSpawnPosition event) {
		if (event.getLevel() instanceof ServerLevel level) {
			Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator().findNearestMapStructure(level, getHolderSet(level.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE)), BlockPos.ZERO, 100, false);
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
	
	private static @NotNull HolderSet<Structure> getHolderSet(@NotNull Registry<Structure> registry) {
		return HolderSet.direct(registry.getHolderOrThrow(BuiltinStructures.VILLAGE_PLAINS));
	}
	
	private static @Nullable BlockPos getSpawnPos(@NotNull ServerLevel level, @NotNull ChunkPos pos) {
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
	public static void explosionDetonate(ExplosionEvent.@NotNull Detonate event) {
		Explosion explosion = event.getExplosion();
		if (event.getLevel() instanceof ServerLevel level) {
			if (level.dimension().equals(Level.NETHER) && explosion.getExploder() instanceof PrimedTnt) {
				Vec3 vec3 = explosion.getPosition();
				BlockPos pos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
				if (pos.getY() >= 124 && level.getBlockState(pos.below()).is(Blocks.BEDROCK)) {
					level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
			event.getAffectedBlocks().removeIf(pos -> level.getBlockState(pos).is(Blocks.SPAWNER));
		}
	}
}