package net.luis.xsurvive.world.level.biome;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public record ReplaceBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> featuresToAdd, /*HolderSet<PlacedFeature> featuresToRemove*/List<ResourceLocation> featuresToRemove) implements BiomeModifier {
	
	public static final Codec<ReplaceBiomeModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter((biomeModifier) -> {
			return biomeModifier.biomes;
		}), PlacedFeature.LIST_CODEC.fieldOf("features_to_add").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToAdd;
		})/*, PlacedFeature.LIST_CODEC.fieldOf("features_to_remove").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToRemove;
		})*/, ResourceLocation.CODEC.listOf().fieldOf("features_to_remove").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToRemove;
		})).apply(instance, ReplaceBiomeModifier::new);
	});
	
	@Override
	public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, @NotNull Builder builder) {
		if (this.biomes.contains(biome)) {
			MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
			if (server != null) {
				RegistryAccess registryAccess = server.registryAccess();
				if (phase == Phase.ADD) {
					this.addFeatures(builder, this.featuresToAdd);
				} else if (phase == Phase.REMOVE) {
					this.removeFeatures(builder, this.getHolderSet(registryAccess, this.featuresToRemove));
				}
			}
		}
	}
	
	private @NotNull HolderSet<PlacedFeature> getHolderSet(@NotNull RegistryAccess registryAccess, @NotNull List<ResourceLocation> locations) {
		List<Holder<PlacedFeature>> features = Lists.newArrayList();
		Registry<PlacedFeature> registry = registryAccess.registryOrThrow(Registries.PLACED_FEATURE);
		for (ResourceLocation location : locations) {
			features.add(registry.getHolderOrThrow(registry.getResourceKey(Objects.requireNonNull(registry.get(location))).orElseThrow()));
		}
		return HolderSet.direct(features);
	}
	
	private void addFeatures(@NotNull Builder builder, @NotNull HolderSet<PlacedFeature> features) {
		for (Holder<PlacedFeature> holder : features) {
			builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, holder);
		}
	}
	
	private void removeFeatures(@NotNull Builder builder, @NotNull HolderSet<PlacedFeature> features) {
		builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_ORES).removeIf(features::contains);
	}
	
	@Override
	public @NotNull Codec<ReplaceBiomeModifier> codec() {
		return XSBiomeModifiers.MOD_BASED_BIOME_MODIFIER.get();
	}
}
