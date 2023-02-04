package net.luis.xsurvive.world.level.levelgen.feature;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 *
 * @author Luis-st
 *
 */

public class XSOreFeatures {
	
	public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE = createKey("coal_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE_BURIED = createKey("coal_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE_SMALL = createKey("copper_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE_LARGE = createKey("copper_ore_large");
	public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE = createKey("iron_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE_SMALL = createKey("iron_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE = createKey("gold_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE_BURIED = createKey("gold_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_ORE = createKey("lapis_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_ORE_BURIED = createKey("lapis_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> REDSTONE_ORE = createKey("redstone_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_SMALL = createKey("diamond_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_LARGE = createKey("diamond_ore_large");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_ORE_BURIED = createKey("diamond_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_ORE = createKey("emerald_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_GOLD_ORE = createKey("nether_gold_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_ORE = createKey("quartz_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_ORE_SMALL = createKey("ancient_debris_ore_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_ORE_LARGE = createKey("ancient_debris_ore_large");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JADE_ORE_UPPER = createKey("jade_ore_upper");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JADE_ORE_MIDDLE = createKey("jade_ore_middle");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAPHIRE_ORE_RARE_UPPER = createKey("saphire_ore_rare_upper");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAPHIRE_ORE = createKey("saphire_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAPHIRE_ORE_BURIED = createKey("saphire_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LIMONITE_ORE_BURIED = createKey("limonite_ore_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LIMONITE_ORE_DEEP_BURIED = createKey("limonite_ore_deep_buried");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ENDERITE_ORE_RARE = createKey("enderite_ore_rare");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ENDERITE_ORE_BURIED = createKey("enderite_ore_buried");
	
	public static void register() {
	
	}
	
	private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(XSurvive.MOD_ID, name));
	}
	
}
