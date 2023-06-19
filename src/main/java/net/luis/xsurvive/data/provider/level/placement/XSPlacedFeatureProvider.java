package net.luis.xsurvive.data.provider.level.placement;

import net.luis.xsurvive.world.level.levelgen.feature.XSOreFeatures;
import net.luis.xsurvive.world.level.levelgen.placement.XSOrePlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class XSPlacedFeatureProvider {
	
	private static final PlacementModifier RANGE_64_48 = HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48));
	private static final PlacementModifier RANGE_144_16 = HeightRangePlacement.triangle(triangleMinGeneration(16), VerticalAnchor.absolute(16));
	private static final PlacementModifier RANGE_8_24 = HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24));
	
	public static void create(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> registry = context.lookup(Registries.CONFIGURED_FEATURE);
		context.register(XSOrePlacements.COAL_ORE_UPPER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.COAL_ORE), uniformOrePlacement(20, 136, 320))); // 30, 136, 320
		context.register(XSOrePlacements.COAL_ORE_LOWER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.COAL_ORE_BURIED), triangleOrePlacement(13, 0, 192))); // 20, 0, 192
		context.register(XSOrePlacements.COPPER_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.COPPER_ORE_SMALL), triangleOrePlacement(10, -16, 112))); // 16, -16, 112
		context.register(XSOrePlacements.COPPER_ORE_LARGE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.COPPER_ORE_LARGE), triangleOrePlacement(10, -16, 112))); // 16, -16, 112
		context.register(XSOrePlacements.IRON_ORE_UPPER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.IRON_ORE), triangleOrePlacement(60, 80, 384))); // 90, 80, 384
		context.register(XSOrePlacements.IRON_ORE_MIDDLE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.IRON_ORE), triangleOrePlacement(7, -24, 56))); // 10, -24, 56
		context.register(XSOrePlacements.IRON_ORE_SMALL, new PlacedFeature(registry.getOrThrow(XSOreFeatures.IRON_ORE_SMALL), uniformOrePlacement(7, -64, 72))); // 10, -64, 72
		context.register(XSOrePlacements.GOLD_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.GOLD_ORE), triangleOrePlacement(3, -64, 32))); // 4, -64, 32
		context.register(XSOrePlacements.GOLD_ORE_EXTRA, new PlacedFeature(registry.getOrThrow(XSOreFeatures.GOLD_ORE_BURIED), uniformOrePlacement(33, 32, 256))); // 50, 32, 256
		context.register(XSOrePlacements.GOLD_ORE_LOWER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.GOLD_ORE_BURIED), orePlacement(CountPlacement.of(UniformInt.of(0, 1)), RANGE_64_48)));
		context.register(XSOrePlacements.LAPIS_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.LAPIS_ORE), triangleOrePlacement(1, -32, 32))); // 2, -32, 32
		context.register(XSOrePlacements.LAPIS_ORE_BURIED, new PlacedFeature(registry.getOrThrow(XSOreFeatures.LAPIS_ORE_BURIED), uniformOrePlacement(3, -64, 64))); // 4, -64, 64
		context.register(XSOrePlacements.REDSTONE_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.REDSTONE_ORE), uniformOrePlacement(3, -64, 15))); // 4, -64, 15
		context.register(XSOrePlacements.REDSTONE_ORE_LOWER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.REDSTONE_ORE), buriedOrePlacement(6, -32))); // 8, -32
		context.register(XSOrePlacements.DIAMOND_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.DIAMOND_ORE_SMALL), buriedOrePlacement(5, 16))); // 7, 16
		context.register(XSOrePlacements.DIAMOND_ORE_LARGE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.DIAMOND_ORE_LARGE), orePlacement(RarityFilter.onAverageOnceEvery(9), RANGE_144_16))); // 9
		context.register(XSOrePlacements.DIAMOND_ORE_BURIED, new PlacedFeature(registry.getOrThrow(XSOreFeatures.DIAMOND_ORE_BURIED), buriedOrePlacement(3, 16))); // 4, 16
		context.register(XSOrePlacements.EMERALD_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.EMERALD_ORE), triangleOrePlacement(66, -16, 480))); // 100, -16, 480
		context.register(XSOrePlacements.NETHER_GOLD_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.NETHER_GOLD_ORE), orePlacement(CountPlacement.of(7), PlacementUtils.RANGE_10_10))); // 10
		context.register(XSOrePlacements.NETHER_GOLD_ORE_DELTA, new PlacedFeature(registry.getOrThrow(XSOreFeatures.NETHER_GOLD_ORE), orePlacement(CountPlacement.of(14), PlacementUtils.RANGE_10_10))); // 20
		context.register(XSOrePlacements.QUARTZ_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.QUARTZ_ORE), orePlacement(CountPlacement.of(10), PlacementUtils.RANGE_10_10))); // 16
		context.register(XSOrePlacements.QUARTZ_ORE_DELTA, new PlacedFeature(registry.getOrThrow(XSOreFeatures.QUARTZ_ORE), orePlacement(CountPlacement.of(16), PlacementUtils.RANGE_10_10))); // 32
		context.register(XSOrePlacements.ANCIENT_DEBRIS_ORE_SMALL, new PlacedFeature(registry.getOrThrow(XSOreFeatures.ANCIENT_DEBRIS_ORE_SMALL), List.of(InSquarePlacement.spread(), PlacementUtils.RANGE_8_8, BiomeFilter.biome())));
		context.register(XSOrePlacements.ANCIENT_DEBRIS_ORE_LARGE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.ANCIENT_DEBRIS_ORE_LARGE), List.of(InSquarePlacement.spread(), RANGE_8_24, BiomeFilter.biome())));
		context.register(XSOrePlacements.JADE_ORE_UPPER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.JADE_ORE_UPPER), upperOrePlacement(10, 128))); // 20, 128
		context.register(XSOrePlacements.JADE_ORE_MIDDLE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.JADE_ORE_MIDDLE), uniformOrePlacement(6, -32, 96))); // 14, -32, 96
		context.register(XSOrePlacements.SAPHIRE_ORE_RARE_UPPER, new PlacedFeature(registry.getOrThrow(XSOreFeatures.SAPHIRE_ORE_RARE_UPPER), upperOrePlacement(5, 256))); // 7, 256
		context.register(XSOrePlacements.SAPHIRE_ORE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.SAPHIRE_ORE), uniformOrePlacement(10, -64, 256))); // 18, -64, 256
		context.register(XSOrePlacements.SAPHIRE_ORE_BURIED, new PlacedFeature(registry.getOrThrow(XSOreFeatures.SAPHIRE_ORE_BURIED), buriedOrePlacement(4, -32))); // 5, -32
		context.register(XSOrePlacements.LIMONITE_ORE_BURIED, new PlacedFeature(registry.getOrThrow(XSOreFeatures.LIMONITE_ORE_BURIED), uniformOrePlacement(4, -48, 0))); // 5, -48, 0
		context.register(XSOrePlacements.LIMONITE_ORE_DEEP_BURIED, new PlacedFeature(registry.getOrThrow(XSOreFeatures.LIMONITE_ORE_DEEP_BURIED), buriedOrePlacement(1, -48))); // 2, -48
		context.register(XSOrePlacements.ENDERITE_ORE_RARE, new PlacedFeature(registry.getOrThrow(XSOreFeatures.ENDERITE_ORE_RARE), uniformOrePlacement(1, 0, 128))); // 2, 0, 128
		context.register(XSOrePlacements.ENDERITE_ORE_BURIED, new PlacedFeature(registry.getOrThrow(XSOreFeatures.ENDERITE_ORE_BURIED), triangleOrePlacement(2, 0, 128))); // 3, 0, 128
	}
	
	private static List<PlacementModifier> orePlacement(PlacementModifier firstModifier, PlacementModifier secondModifier) {
		return List.of(firstModifier, InSquarePlacement.spread(), secondModifier, BiomeFilter.biome());
	}
	
	private static List<PlacementModifier> triangleOrePlacement(int count, int min, int max) {
		return orePlacement(CountPlacement.of(count), HeightRangePlacement.triangle(VerticalAnchor.absolute(min), VerticalAnchor.absolute(max)));
	}
	
	private static List<PlacementModifier> uniformOrePlacement(int count, int min, int max) {
		return orePlacement(CountPlacement.of(count), HeightRangePlacement.uniform(VerticalAnchor.absolute(min), VerticalAnchor.absolute(max)));
	}
	
	private static List<PlacementModifier> upperOrePlacement(int count, int minGeneration) {
		return orePlacement(CountPlacement.of(count), HeightRangePlacement.triangle(VerticalAnchor.absolute(minGeneration), triangleMaxGeneration(minGeneration)));
	}
	
	private static List<PlacementModifier> buriedOrePlacement(int count, int maxGeneration) {
		return orePlacement(CountPlacement.of(count), HeightRangePlacement.triangle(triangleMinGeneration(maxGeneration), VerticalAnchor.absolute(maxGeneration)));
	}
	
	private static VerticalAnchor triangleMaxGeneration(int minGeneration) {
		return VerticalAnchor.absolute(320 + (320 - minGeneration));
	}
	
	private static VerticalAnchor triangleMinGeneration(int maxGeneration) {
		return VerticalAnchor.absolute((64 + maxGeneration) * -1 - 64);
	}
}
