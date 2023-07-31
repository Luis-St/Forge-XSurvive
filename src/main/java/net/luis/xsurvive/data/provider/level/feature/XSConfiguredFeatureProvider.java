package net.luis.xsurvive.data.provider.level.feature;

import net.luis.xores.world.level.block.XOBlocks;
import net.luis.xsurvive.world.level.levelgen.feature.XSOreFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Luis-St
 *
 */

public class XSConfiguredFeatureProvider {
	
	private static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
	private static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
	private static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
	private static final RuleTest BASE_STONE_NETHER = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
	
	private static final Supplier<List<OreConfiguration.TargetBlockState>> COAL_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.COAL_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> COPPER_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.COPPER_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> IRON_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.IRON_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> GOLD_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.GOLD_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> LAPIS_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.LAPIS_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> REDSTONE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.REDSTONE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> DIAMOND_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.DIAMOND_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> EMERALD_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, Blocks.EMERALD_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> JADE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.JADE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_JADE_ORE.get().defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> LIMONITE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.LIMONITE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_LIMONITE_ORE.get().defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> SAPHIRE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, XOBlocks.SAPHIRE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, XOBlocks.DEEPSLATE_SAPHIRE_ORE.get().defaultBlockState()));
	};
	private static final Supplier<List<OreConfiguration.TargetBlockState>> ENDERITE_ORE_TARGETS = () -> {
		return List.of(OreConfiguration.target(new TagMatchTest(Tags.Blocks.END_STONES), XOBlocks.ENDERITE_ORE.get().defaultBlockState()));
	};
	
	public static void create(BootstapContext<ConfiguredFeature<?, ?>> context) {
		context.register(XSOreFeatures.COAL_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COAL_ORE_TARGETS.get(), 9))); // 17
		context.register(XSOreFeatures.COAL_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COAL_ORE_TARGETS.get(), 12, 0.8F))); // 17, 0.5
		context.register(XSOreFeatures.COPPER_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COPPER_ORE_TARGETS.get(), 5))); // 10
		context.register(XSOreFeatures.COPPER_ORE_LARGE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COPPER_ORE_TARGETS.get(), 10))); // 20
		context.register(XSOreFeatures.IRON_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(IRON_ORE_TARGETS.get(), 5))); // 9
		context.register(XSOreFeatures.IRON_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(IRON_ORE_TARGETS.get(), 2))); // 4
		context.register(XSOreFeatures.GOLD_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GOLD_ORE_TARGETS.get(), 5))); // 9
		context.register(XSOreFeatures.GOLD_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GOLD_ORE_TARGETS.get(), 6, 0.8F))); // 9, 0.5
		context.register(XSOreFeatures.LAPIS_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LAPIS_ORE_TARGETS.get(), 4))); // 7
		context.register(XSOreFeatures.LAPIS_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LAPIS_ORE_TARGETS.get(), 4, 1.0F))); // 7, 1.0
		context.register(XSOreFeatures.REDSTONE_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(REDSTONE_ORE_TARGETS.get(), 4))); // 8
		context.register(XSOreFeatures.DIAMOND_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DIAMOND_ORE_TARGETS.get(), 3, 0.7F))); // 4, 0.5
		context.register(XSOreFeatures.DIAMOND_ORE_LARGE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DIAMOND_ORE_TARGETS.get(), 8, 0.9F))); // 12, 0.7
		context.register(XSOreFeatures.DIAMOND_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DIAMOND_ORE_TARGETS.get(), 4, 1.0F))); // 8 1.0
		context.register(XSOreFeatures.EMERALD_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(EMERALD_ORE_TARGETS.get(), 2))); // 3
		context.register(XSOreFeatures.NETHER_GOLD_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHERRACK, Blocks.NETHER_GOLD_ORE.defaultBlockState(), 5))); // 10
		context.register(XSOreFeatures.QUARTZ_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHERRACK, Blocks.NETHER_QUARTZ_ORE.defaultBlockState(), 7))); // 14
		context.register(XSOreFeatures.ANCIENT_DEBRIS_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(BASE_STONE_NETHER, Blocks.ANCIENT_DEBRIS.defaultBlockState(), 1, 1.0F))); // 2, 1.0
		context.register(XSOreFeatures.ANCIENT_DEBRIS_ORE_LARGE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(BASE_STONE_NETHER, Blocks.ANCIENT_DEBRIS.defaultBlockState(), 2, 1.0F))); // 3, 1.0
		context.register(XSOreFeatures.JADE_ORE_UPPER, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(JADE_ORE_TARGETS.get(), 3))); // 5
		context.register(XSOreFeatures.JADE_ORE_MIDDLE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(JADE_ORE_TARGETS.get(), 4))); // 7
		context.register(XSOreFeatures.SAPHIRE_ORE_RARE_UPPER, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SAPHIRE_ORE_TARGETS.get(), 1))); // 2
		context.register(XSOreFeatures.SAPHIRE_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SAPHIRE_ORE_TARGETS.get(), 3, 0.4F))); // 5, 0.0
		context.register(XSOreFeatures.SAPHIRE_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SAPHIRE_ORE_TARGETS.get(), 4, 1.0F))); // 7, 1.0
		context.register(XSOreFeatures.LIMONITE_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LIMONITE_ORE_TARGETS.get(), 2, 0.5F))); // 4, 1.0
		context.register(XSOreFeatures.LIMONITE_ORE_DEEP_BURIED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(LIMONITE_ORE_TARGETS.get(), 5, 1.0F))); // 10, 1.0
		context.register(XSOreFeatures.ENDERITE_ORE_RARE, new ConfiguredFeature<>(Feature.SCATTERED_ORE, new OreConfiguration(ENDERITE_ORE_TARGETS.get(), 1, 0.4F))); // 1, 0.0		
		context.register(XSOreFeatures.ENDERITE_ORE_BURIED, new ConfiguredFeature<>(Feature.SCATTERED_ORE, new OreConfiguration(ENDERITE_ORE_TARGETS.get(), 2, 1.0F))); // 3, 1.0
	}
}
