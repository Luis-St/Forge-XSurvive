package net.luis.xsurvive.world.level.levelgen.placement;

import java.util.List;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.levelgen.feature.XSOreFeatures;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class XSOrePlacements {
	
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, XSurvive.MOD_ID);
	
	private static final PlacementModifier RANGE_64_48 = HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48));
	private static final PlacementModifier RANGE_144_16 = HeightRangePlacement.triangle(triangleMinGeneration(16), VerticalAnchor.absolute(16));
	private static final PlacementModifier RANGE_8_24 = HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24));
	
	public static final RegistryObject<PlacedFeature> COAL_ORE_UPPER = PLACED_FEATURES.register("coal_ore_upper", () -> {
		return new PlacedFeature(XSOreFeatures.COAL_ORE.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(20, 136, 320)); // 30, 136, 320
	});
	public static final RegistryObject<PlacedFeature> COAL_ORE_LOWER = PLACED_FEATURES.register("coal_ore_lower", () -> {
		return new PlacedFeature(XSOreFeatures.COAL_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(13, 0, 192)); // 20, 0, 192
	});
	public static final RegistryObject<PlacedFeature> COPPER_ORE = PLACED_FEATURES.register("copper_ore", () -> {
		return new PlacedFeature(XSOreFeatures.COPPPER_ORE_SMALL.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(10, -16, 112)); // 16, -16, 112
	});
	public static final RegistryObject<PlacedFeature> COPPER_ORE_LARGE = PLACED_FEATURES.register("copper_ore_large", () -> {
		return new PlacedFeature(XSOreFeatures.COPPER_ORE_LARGE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(10, -16, 112)); // 16, -16, 112
	});
	public static final RegistryObject<PlacedFeature> IRON_ORE_UPPER = PLACED_FEATURES.register("iron_ore_upper", () -> {
		return new PlacedFeature(XSOreFeatures.IRON_ORE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(60, 80, 384)); // 90, 80, 384
	});
	public static final RegistryObject<PlacedFeature> IRON_ORE_MIDDLE = PLACED_FEATURES.register("iron_ore_middle", () -> {
		return new PlacedFeature(XSOreFeatures.IRON_ORE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(7, -24, 56)); // 10, -24, 56
	});
	public static final RegistryObject<PlacedFeature> IRON_ORE_SMALL = PLACED_FEATURES.register("iron_ore_small", () -> {
		return new PlacedFeature(XSOreFeatures.IRON_ORE_SMALL.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(7, -64, 72)); // 10, -64, 72
	});
	public static final RegistryObject<PlacedFeature> GOLD_ORE = PLACED_FEATURES.register("gold_ore", () -> {
		return new PlacedFeature(XSOreFeatures.GOLD_ORE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(3, -64, 32)); // 4, -64, 32
	});
	public static final RegistryObject<PlacedFeature> GOLD_ORE_EXTRA = PLACED_FEATURES.register("gold_ore_extra", () -> {
		return new PlacedFeature(XSOreFeatures.GOLD_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(33, 32, 256)); // 50, 32, 256
	});
	public static final RegistryObject<PlacedFeature> GOLD_ORE_LOWER = PLACED_FEATURES.register("gold_ore_lower", () -> {
		return new PlacedFeature(XSOreFeatures.GOLD_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), orePlacement(CountPlacement.of(UniformInt.of(0, 1)), RANGE_64_48));
	});
	public static final RegistryObject<PlacedFeature> LAPIS_ORE = PLACED_FEATURES.register("lapis_ore", () -> {
		return new PlacedFeature(XSOreFeatures.LAPIS_ORE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(1, -32, 32)); // 2, -32, 32
	});
	public static final RegistryObject<PlacedFeature> LAPIS_ORE_BURIED = PLACED_FEATURES.register("lapis_ore_buried", () -> {
		return new PlacedFeature(XSOreFeatures.LAPIS_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(3, -64, 64)); // 4, -64, 64
	});
	public static final RegistryObject<PlacedFeature> REDSTONE_ORE = PLACED_FEATURES.register("redstone_ore", () -> {
		return new PlacedFeature(XSOreFeatures.REDSTONE_ORE.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(3, -64, 15)); // 4, -64, 15
	});
	public static final RegistryObject<PlacedFeature> REDSTONE_ORE_LOWER = PLACED_FEATURES.register("redstone_ore_lower", () -> {
		return new PlacedFeature(XSOreFeatures.REDSTONE_ORE.getHolder().orElseThrow(NullPointerException::new), buriedOrePlacement(6, -32)); // 8, -32
	});
	public static final RegistryObject<PlacedFeature> DIAMOND_ORE = PLACED_FEATURES.register("diamond_ore", () -> {
		return new PlacedFeature(XSOreFeatures.DIAMOND_ORE_SMALL.getHolder().orElseThrow(NullPointerException::new), buriedOrePlacement(5, 16)); // 7, 16
	});
	public static final RegistryObject<PlacedFeature> DIAMOND_ORE_LARGE = PLACED_FEATURES.register("diamond_ore_large", () -> {
		return new PlacedFeature(XSOreFeatures.DIAMOND_ORE_LARGE.getHolder().orElseThrow(NullPointerException::new), orePlacement(RarityFilter.onAverageOnceEvery(9), RANGE_144_16)); // 9
	});
	public static final RegistryObject<PlacedFeature> DIAMOND_ORE_BURIED = PLACED_FEATURES.register("diamond_ore_buried", () -> {
		return new PlacedFeature(XSOreFeatures.DIAMOND_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), buriedOrePlacement(3, 16)); // 4, 16
	});
	public static final RegistryObject<PlacedFeature> EMERALD_ORE = PLACED_FEATURES.register("emerald_ore", () -> {
		return new PlacedFeature(XSOreFeatures.EMERALD_ORE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(66, -16, 480)); // 100, -16, 480
	});
	public static final RegistryObject<PlacedFeature> NETHER_GOLD_ORE = PLACED_FEATURES.register("nether_gold_ore", () -> {
		return new PlacedFeature(XSOreFeatures.NETHER_GOLD_ORE.getHolder().orElseThrow(NullPointerException::new), orePlacement(CountPlacement.of(7), PlacementUtils.RANGE_10_10)); // 10
	});
	public static final RegistryObject<PlacedFeature> QUARTZ_ORE = PLACED_FEATURES.register("quartz_ore", () -> {
		return new PlacedFeature(XSOreFeatures.QUARTZ_ORE.getHolder().orElseThrow(NullPointerException::new), orePlacement(CountPlacement.of(10), PlacementUtils.RANGE_10_10)); // 16
	});
	public static final RegistryObject<PlacedFeature> ANCIENT_DEBRIS_ORE_SMALL = PLACED_FEATURES.register("ancient_debris_ore_small", () -> {
		return new PlacedFeature(XSOreFeatures.ANCIENT_DEBRIS_ORE_SMALL.getHolder().orElseThrow(NullPointerException::new), List.of(InSquarePlacement.spread(), PlacementUtils.RANGE_8_8, BiomeFilter.biome()));
	});
	public static final RegistryObject<PlacedFeature> ANCIENT_DEBRIS_ORE_LARGE = PLACED_FEATURES.register("ancient_debris_ore_large", () -> {
		return new PlacedFeature(XSOreFeatures.ANCIENT_DEBRIS_ORE_LARGE.getHolder().orElseThrow(NullPointerException::new), List.of(InSquarePlacement.spread(), RANGE_8_24, BiomeFilter.biome()));
	});
	public static final RegistryObject<PlacedFeature> JADE_ORE_UPPER = PLACED_FEATURES.register("jade_ore_upper", () -> {
		return new PlacedFeature(XSOreFeatures.JADE_ORE_UPPER.getHolder().orElseThrow(NullPointerException::new), upperOrePlacement(12, 128)); // 20, 128
	});
	public static final RegistryObject<PlacedFeature> JADE_ORE_MIDDLE = PLACED_FEATURES.register("jade_ore_middle", () -> {
		return new PlacedFeature(XSOreFeatures.JADE_ORE_MIDDLE.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(7, -32, 96)); // 14, -32, 96
	});
	public static final RegistryObject<PlacedFeature> SAPHIRE_ORE_RARE_UPPER = PLACED_FEATURES.register("saphire_ore_rare_upper", () -> {
		return new PlacedFeature(XSOreFeatures.SAPHIRE_ORE_RARE_UPPER.getHolder().orElseThrow(NullPointerException::new), upperOrePlacement(5, 256)); // 7, 256
	});
	public static final RegistryObject<PlacedFeature> SAPHIRE_ORE = PLACED_FEATURES.register("saphire_ore", () -> {
		return new PlacedFeature(XSOreFeatures.SAPHIRE_ORE.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(12, -64, 256)); // 18, -64, 256
	});
	public static final RegistryObject<PlacedFeature> SAPHIRE_ORE_BURIED = PLACED_FEATURES.register("saphire_ore_buried", () -> {
		return new PlacedFeature(XSOreFeatures.SAPHIRE_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), buriedOrePlacement(4, -32)); // 5, -32
	});
	public static final RegistryObject<PlacedFeature> LIMONITE_ORE_BURIED = PLACED_FEATURES.register("limonite_ore_buried", () -> {
		return new PlacedFeature(XSOreFeatures.LIMONITE_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(4, -48, 0)); // 5, -48, 0
	});
	public static final RegistryObject<PlacedFeature> LIMONITE_ORE_DEEP_BURIED = PLACED_FEATURES.register("limonite_ore_deep_buried", () -> {
		return new PlacedFeature(XSOreFeatures.LIMONITE_ORE_DEEP_BURIED.getHolder().orElseThrow(NullPointerException::new), buriedOrePlacement(1, -48)); // 2, -48
	});
	public static final RegistryObject<PlacedFeature> ROSITE_ORE_RARE = PLACED_FEATURES.register("rosite_ore_rare", () -> {
		return new PlacedFeature(XSOreFeatures.ROSITE_ORE_RARE.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(4, -16, 128)); // 5, -16, 128
	});
	public static final RegistryObject<PlacedFeature> ROSITE_ORE_BURIED = PLACED_FEATURES.register("rosite_ore_buried", () -> {
		return new PlacedFeature(XSOreFeatures.ROSITE_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), buriedOrePlacement(4, -32)); // 7, -32
	});
	public static final RegistryObject<PlacedFeature> ENDERITE_ORE_RARE = PLACED_FEATURES.register("enderite_ore_rare", () -> {
		return new PlacedFeature(XSOreFeatures.ENDERITE_ORE_RARE.getHolder().orElseThrow(NullPointerException::new), uniformOrePlacement(1, 0, 128)); // 2, 0, 128
	});
	public static final RegistryObject<PlacedFeature> ENDERITE_ORE_BURIED = PLACED_FEATURES.register("enderite_ore_buried", () -> {
		return new PlacedFeature(XSOreFeatures.ENDERITE_ORE_BURIED.getHolder().orElseThrow(NullPointerException::new), triangleOrePlacement(2, 0, 128)); // 3, 0, 128
	});
	
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
