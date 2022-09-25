package net.luis.xsurvive.world.level.levelgen.feature;

import java.util.List;
import java.util.function.Supplier;

import net.luis.xores.world.level.block.XOBlocks;
import net.luis.xsurvive.XSurvive;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class XSOreFeatures {
	
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, XSurvive.MOD_ID);
	
	private static final RuleTest STONE_ORE_REPLACEABLES = OreFeatures.STONE_ORE_REPLACEABLES;
	private static final RuleTest DEEPSLATE_ORE_REPLACEABLES = OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
	
	private static final Supplier<List<TargetBlockState>> COAL_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.COAL_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> COPPER_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.COPPER_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> IRON_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.IRON_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> GOLD_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.GOLD_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> LAPIS_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.LAPIS_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> REDSTONE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.REDSTONE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> DIAMOND_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.DIAMOND_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> EMERALD_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.EMERALD_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> JADE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.JADE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_JADE_ORE.get().defaultBlockState()));
	};              
	private static final Supplier<List<TargetBlockState>> LIMONITE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.LIMONITE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_LIMONITE_ORE.get().defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> SAPHIRE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.SAPHIRE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_SAPHIRE_ORE.get().defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> ROSITE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.ROSITE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_ROSITE_ORE.get().defaultBlockState()));
	};
	private static final Supplier<List<TargetBlockState>> ENDERITE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(new TagMatchTest(Tags.Blocks.END_STONES), XOBlocks.ENDERITE_ORE.get().defaultBlockState()));
	};
	
	public static final RegistryObject<ConfiguredFeature<?, ?>> COAL_ORE = CONFIGURED_FEATURES.register("coal_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COAL_ORE_TARGETS.get(), 9)); // 17 
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> COAL_ORE_BURIED = CONFIGURED_FEATURES.register("coal_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COAL_ORE_TARGETS.get(), 12, 0.8F)); // 17, 0.5
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> COPPPER_ORE_SMALL = CONFIGURED_FEATURES.register("coppper_ore_small", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COPPER_ORE_TARGETS.get(), 5)); // 10
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> COPPER_ORE_LARGE = CONFIGURED_FEATURES.register("copper_ore_large", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COPPER_ORE_TARGETS.get(), 10)); // 20
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> IRON_ORE = CONFIGURED_FEATURES.register("iron_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(IRON_ORE_TARGETS.get(), 5)); // 9
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> IRON_ORE_SMALL = CONFIGURED_FEATURES.register("iron_ore_small", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(IRON_ORE_TARGETS.get(), 2)); // 4
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> GOLD_ORE = CONFIGURED_FEATURES.register("gold_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GOLD_ORE_TARGETS.get(), 5)); // 9
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> GOLD_ORE_BURIED = CONFIGURED_FEATURES.register("gold_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GOLD_ORE_TARGETS.get(), 6, 0.8F)); // 9, 0.5
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> LAPIS_ORE = CONFIGURED_FEATURES.register("lapis_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LAPIS_ORE_TARGETS.get(), 4)); // 7
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> LAPIS_ORE_BURIED = CONFIGURED_FEATURES.register("lapis_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LAPIS_ORE_TARGETS.get(), 4, 1.0F)); // 7, 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> REDSTONE_ORE = CONFIGURED_FEATURES.register("redstone_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(REDSTONE_ORE_TARGETS.get(), 4)); // 8
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> DIAMOND_ORE_SMALL = CONFIGURED_FEATURES.register("diamond_ore_small", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DIAMOND_ORE_TARGETS.get(), 3, 0.7F)); // 4, 0.5
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> DIAMOND_ORE_LARGE = CONFIGURED_FEATURES.register("diamond_ore_large", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DIAMOND_ORE_TARGETS.get(), 8, 0.9F)); // 12, 0.7
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> DIAMOND_ORE_BURIED = CONFIGURED_FEATURES.register("diamond_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DIAMOND_ORE_TARGETS.get(), 4, 1.0F)); // 8 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> EMERALD_ORE = CONFIGURED_FEATURES.register("emerald_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(EMERALD_ORE_TARGETS.get(), 2)); // 3
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> NETHER_GOLD_ORE = CONFIGURED_FEATURES.register("nether_gold_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NETHERRACK, Blocks.NETHER_GOLD_ORE.defaultBlockState(), 5)); // 10
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> QUARTZ_ORE = CONFIGURED_FEATURES.register("quartz_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NETHERRACK, Blocks.NETHER_QUARTZ_ORE.defaultBlockState(), 7)); // 14
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_ORE_SMALL = CONFIGURED_FEATURES.register("ancient_debris_ore_small", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, Blocks.ANCIENT_DEBRIS.defaultBlockState(), 1, 1.0F)); // 2, 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_ORE_LARGE = CONFIGURED_FEATURES.register("ancient_debris_ore_large", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, Blocks.ANCIENT_DEBRIS.defaultBlockState(), 2, 1.0F)); // 3, 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> JADE_ORE_UPPER = CONFIGURED_FEATURES.register("jade_ore_upper", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(JADE_ORE_TARGETS.get(), 3)); // 5
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> JADE_ORE_MIDDLE = CONFIGURED_FEATURES.register("jade_ore_middle", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(JADE_ORE_TARGETS.get(), 4)); // 7
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> SAPHIRE_ORE_RARE_UPPER = CONFIGURED_FEATURES.register("saphire_ore_rare_upper", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SAPHIRE_ORE_TARGETS.get(), 1)); // 2
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> SAPHIRE_ORE = CONFIGURED_FEATURES.register("saphire_ore", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SAPHIRE_ORE_TARGETS.get(), 3)); // 5
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> SAPHIRE_ORE_BURIED = CONFIGURED_FEATURES.register("saphire_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SAPHIRE_ORE_TARGETS.get(), 4, 1.0F)); // 7, 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> LIMONITE_ORE_BURIED = CONFIGURED_FEATURES.register("limonite_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LIMONITE_ORE_TARGETS.get(), 2, 0.5F)); // 4, 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> LIMONITE_ORE_DEEP_BURIED = CONFIGURED_FEATURES.register("limonite_ore_deep_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LIMONITE_ORE_TARGETS.get(), 5, 1.0F)); // 10, 1.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> ROSITE_ORE_RARE = CONFIGURED_FEATURES.register("rosite_ore_rare", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ROSITE_ORE_TARGETS.get(), 2, 0.8F)); // 2, 0.2
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> ROSITE_ORE_BURIED = CONFIGURED_FEATURES.register("rosite_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ROSITE_ORE_TARGETS.get(), 2)); // 4
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> ENDERITE_ORE_RARE = CONFIGURED_FEATURES.register("enderite_ore_rare", () -> {
		return new ConfiguredFeature<>(Feature.SCATTERED_ORE, new OreConfiguration(ENDERITE_ORE_TARGETS.get(), 1, 0.4F)); // 1, 0.0
	});
	public static final RegistryObject<ConfiguredFeature<?, ?>> ENDERITE_ORE_BURIED = CONFIGURED_FEATURES.register("enderite_ore_buried", () -> {
		return new ConfiguredFeature<>(Feature.SCATTERED_ORE, new OreConfiguration(ENDERITE_ORE_TARGETS.get(), 2, 1.0F)); // 3, 1.0
	});
	
}
