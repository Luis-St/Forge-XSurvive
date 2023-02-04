package net.luis.xsurvive.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

/**
 *
 * @author Luis-st
 *
 */

public record ReplaceBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> featuresToAdd, HolderSet<PlacedFeature> featuresToRemove) implements BiomeModifier {
	
	public static final Codec<ReplaceBiomeModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter((biomeModifier) -> {
			return biomeModifier.biomes;
		}), PlacedFeature.LIST_CODEC.fieldOf("features_to_add").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToAdd;
		}), PlacedFeature.LIST_CODEC.fieldOf("features_to_remove").forGetter((biomeModifier) -> {
			return biomeModifier.featuresToRemove;
		})).apply(instance, ReplaceBiomeModifier::new);
	});
	
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (this.biomes.contains(biome)) {
			if (phase == Phase.ADD) {
				this.addFeatures(builder, this.featuresToAdd);
			} else if (phase == Phase.REMOVE) {
				this.removeFeatures(builder, this.featuresToRemove);
			}
		}
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
	public Codec<ReplaceBiomeModifier> codec() {
		return XSBiomeModifiers.MOD_BASED_BIOME_MODIFIER.get();
	}
	
}
