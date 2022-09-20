package net.luis.xsurvive.world.level.biome;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 *
 * @author Luis-st
 *
 */

public record ModBasedBiomeModifier(String modId, HolderSet<Biome> biomes, List<ResourceLocation> featuresToAdd, List<ResourceLocation> featuresToRemove) implements BiomeModifier {
	
	public static final Codec<ModBasedBiomeModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.STRING.fieldOf("mod_id").forGetter((biomeModifier) -> {
			return biomeModifier.modId;
		}), Biome.LIST_CODEC.fieldOf("biomes").forGetter((biomeModifier) -> {
			return biomeModifier.biomes;
		}), ResourceLocation.CODEC.listOf().fieldOf("features_to_add").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToAdd;
		}), ResourceLocation.CODEC.listOf().fieldOf("features_to_remove").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToRemove;
		})).apply(instance, ModBasedBiomeModifier::new);
	});
	
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (ModList.get().isLoaded(this.modId) && this.biomes.contains(biome)) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			if (server != null) {
				RegistryAccess registryAccess = server.registryAccess();
				if (registryAccess != null) {
					if (phase == Phase.ADD) {
						this.addFeatures(builder, this.getHolderSet(registryAccess, this.featuresToAdd));
					} else if (phase == Phase.REMOVE) {
						this.removeFeatures(builder, this.getHolderSet(registryAccess, this.featuresToRemove));
					}
				}
			}
		}
	}
	
	private HolderSet<PlacedFeature> getHolderSet(RegistryAccess registryAccess, List<ResourceLocation> locations) {
		List<Holder<PlacedFeature>> features = Lists.newArrayList();
		Registry<PlacedFeature> registry = registryAccess.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
		for (ResourceLocation location : locations) {
			features.add(registry.getHolderOrThrow(registry.getResourceKey(registry.get(location)).orElseThrow()));
		}
		return HolderSet.direct(features);
	}
	
	private void addFeatures(Builder builder, HolderSet<PlacedFeature> features) {
		for (Holder<PlacedFeature> holder : features) {
			builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, holder);
		}
	}
	
	private void removeFeatures(Builder builder, HolderSet<PlacedFeature> features) {
		builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_ORES).removeIf(features::contains);
	}
	
	@Override
	public Codec<ModBasedBiomeModifier> codec() {
		return XSBiomeModifiers.MOD_BASED_BIOME_MODIFIER.get();
	}
	
}
