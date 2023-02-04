package net.luis.xsurvive.mixin;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin extends ChunkGenerator {
	
	private NoiseBasedChunkGeneratorMixin(BiomeSource source) {
		super(source);
	}
	
	@Inject(method = "fillFromNoise", at = @At("HEAD"), cancellable = true)
	public void fillFromNoise(Executor executor, Blender blender, RandomState rng, StructureManager structureManager, ChunkAccess chunk, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> callback) {
		if (this.isEndChunk(chunk) && 32768 >= BlockPos.ZERO.distSqr(chunk.getPos().getMiddleBlockPosition(0))) {
			callback.setReturnValue(CompletableFuture.completedFuture(chunk));
		}
	}
	
	private boolean isEndChunk(ChunkAccess chunk) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				if (chunk.getNoiseBiome(x, 100, z).is(Biomes.THE_END)) {
					return true;
				}
			}
		}
		return false;
	}

}
