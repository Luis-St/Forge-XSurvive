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

public record IfElseBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> ifFeatures, HolderSet<PlacedFeature> elseFeatures) implements BiomeModifier {
	
	public static final Codec<IfElseBiomeModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter((biomeModifier) -> {
			return biomeModifier.biomes;
		}), PlacedFeature.LIST_CODEC.fieldOf("if_features").forGetter((biomeModifier) -> {
			return biomeModifier.ifFeatures;
		}), PlacedFeature.LIST_CODEC.fieldOf("else_features").forGetter((biomeModifier) -> {
			return biomeModifier.elseFeatures;
		})).apply(instance, IfElseBiomeModifier::new);
	});
	
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD) {
			if (this.biomes.contains(biome)) {
				for (Holder<PlacedFeature> holder : this.ifFeatures) {
					builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_ORES).add(holder);
				}
			} else {
				for (Holder<PlacedFeature> holder : this.elseFeatures) {
					builder.getGenerationSettings().getFeatures(Decoration.UNDERGROUND_ORES).add(holder);
				}
			}
		}
	}

	@Override
	public Codec<IfElseBiomeModifier> codec() {
		return XSBiomeModifiers.IF_ELSE_BIOME_MODIFIER.get();
	}
	
}
