package net.luis.xsurvive.data.provider.level;

import com.google.common.collect.Lists;
import net.luis.xores.XOres;
import net.luis.xsurvive.tag.XSBiomeTags;
import net.luis.xsurvive.world.level.biome.*;
import net.luis.xsurvive.world.level.levelgen.placement.XSOrePlacements;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.RemoveFeaturesBiomeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Luis-St
 *
 */

public class XSBiomeModifierProvider {
	
	public static void create(@NotNull BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> featureRegistry = context.lookup(Registries.PLACED_FEATURE);
		context.register(XSBiomeModifiers.Keys.ADD_TO_BADLANDS, createAddToBadlands(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.ADD_COPPER_TO_OVERWORLD, createAddCopper(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.ADD_TO_MOUNTAINS, createAddToMountain(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.ADD_TO_NETHER, createAddToNether(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.ADD_ANCIENT_DEBRIS_TO_NETHER, createAddAncientDebrisToNether(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.ADD_TO_OVERWORLD, createAddToOverworld(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.REMOVE_FROM_NETHER, createRemoveFromNether(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.REMOVE_FROM_OVERWORLD, createRemoveFromOverworld(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.REPLACE_END, createReplaceEnd(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.REPLACE_OVERWORLD, createReplaceOverworld(biomeRegistry, featureRegistry));
		context.register(XSBiomeModifiers.Keys.REPLACE_PEAK, createReplacePeak(biomeRegistry, featureRegistry));
	}
	
	private static @NotNull AddFeaturesBiomeModifier createAddToBadlands(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		return new AddFeaturesBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_BADLANDS), HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.GOLD_ORE_EXTRA)), Decoration.UNDERGROUND_ORES);
	}
	
	private static @NotNull IfElseBiomeModifier createAddCopper(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<Biome> biomes = HolderSet.direct(biomeRegistry.getOrThrow(Biomes.DRIPSTONE_CAVES), biomeRegistry.getOrThrow(Biomes.DEEP_DARK));
		Holder<PlacedFeature> ifFeature = featureRegistry.getOrThrow(XSOrePlacements.COPPER_ORE_LARGE);
		Holder<PlacedFeature> elseFeature = featureRegistry.getOrThrow(XSOrePlacements.COPPER_ORE);
		return new IfElseBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_OVERWORLD), biomes, HolderSet.direct(ifFeature), HolderSet.direct(elseFeature));
	}
	
	private static @NotNull AddFeaturesBiomeModifier createAddToMountain(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		return new AddFeaturesBiomeModifier(biomeRegistry.getOrThrow(XSBiomeTags.IS_MOUNTAIN), HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.EMERALD_ORE)), Decoration.UNDERGROUND_ORES);
	}
	
	private static @NotNull IfElseBiomeModifier createAddToNether(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> ifFeatures = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.NETHER_GOLD_ORE_DELTA), featureRegistry.getOrThrow(XSOrePlacements.QUARTZ_ORE_DELTA));
		HolderSet<PlacedFeature> elseFeatures = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.NETHER_GOLD_ORE), featureRegistry.getOrThrow(XSOrePlacements.QUARTZ_ORE));
		return new IfElseBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_NETHER), HolderSet.direct(biomeRegistry.getOrThrow(Biomes.BASALT_DELTAS)), ifFeatures, elseFeatures);
	}
	
	private static @NotNull AddFeaturesBiomeModifier createAddAncientDebrisToNether(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> features = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.ANCIENT_DEBRIS_ORE_SMALL), featureRegistry.getOrThrow(XSOrePlacements.ANCIENT_DEBRIS_ORE_LARGE));
		return new AddFeaturesBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_NETHER), features, Decoration.UNDERGROUND_ORES);
	}
	
	private static @NotNull AddFeaturesBiomeModifier createAddToOverworld(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> features = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.COAL_ORE_UPPER), featureRegistry.getOrThrow(XSOrePlacements.COAL_ORE_LOWER),
			featureRegistry.getOrThrow(XSOrePlacements.IRON_ORE_UPPER), featureRegistry.getOrThrow(XSOrePlacements.IRON_ORE_MIDDLE), featureRegistry.getOrThrow(XSOrePlacements.IRON_ORE_SMALL),
			featureRegistry.getOrThrow(XSOrePlacements.GOLD_ORE), featureRegistry.getOrThrow(XSOrePlacements.GOLD_ORE_LOWER), featureRegistry.getOrThrow(XSOrePlacements.LAPIS_ORE),
			featureRegistry.getOrThrow(XSOrePlacements.LAPIS_ORE_BURIED), featureRegistry.getOrThrow(XSOrePlacements.REDSTONE_ORE), featureRegistry.getOrThrow(XSOrePlacements.REDSTONE_ORE_LOWER),
			featureRegistry.getOrThrow(XSOrePlacements.DIAMOND_ORE), featureRegistry.getOrThrow(XSOrePlacements.DIAMOND_ORE_LARGE), featureRegistry.getOrThrow(XSOrePlacements.DIAMOND_ORE_BURIED));
		return new AddFeaturesBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_OVERWORLD), features, Decoration.UNDERGROUND_ORES);
	}
	
	private static @NotNull RemoveFeaturesBiomeModifier createRemoveFromNether(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> features = HolderSet.direct(featureRegistry.getOrThrow(OrePlacements.ORE_GOLD_NETHER), featureRegistry.getOrThrow(OrePlacements.ORE_QUARTZ_NETHER),
			featureRegistry.getOrThrow(OrePlacements.ORE_ANCIENT_DEBRIS_SMALL), featureRegistry.getOrThrow(OrePlacements.ORE_ANCIENT_DEBRIS_LARGE));
		return new RemoveFeaturesBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_NETHER), features, Set.of(Decoration.UNDERGROUND_ORES));
	}
	
	private static @NotNull RemoveFeaturesBiomeModifier createRemoveFromOverworld(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> features = HolderSet.direct(featureRegistry.getOrThrow(OrePlacements.ORE_COAL_UPPER), featureRegistry.getOrThrow(OrePlacements.ORE_COAL_LOWER), featureRegistry.getOrThrow(OrePlacements.ORE_IRON_UPPER),
			featureRegistry.getOrThrow(OrePlacements.ORE_IRON_MIDDLE), featureRegistry.getOrThrow(OrePlacements.ORE_IRON_SMALL), featureRegistry.getOrThrow(OrePlacements.ORE_GOLD),
			featureRegistry.getOrThrow(OrePlacements.ORE_GOLD_LOWER),
			featureRegistry.getOrThrow(OrePlacements.ORE_LAPIS), featureRegistry.getOrThrow(OrePlacements.ORE_LAPIS_BURIED), featureRegistry.getOrThrow(OrePlacements.ORE_REDSTONE),
			featureRegistry.getOrThrow(OrePlacements.ORE_REDSTONE_LOWER),
			featureRegistry.getOrThrow(OrePlacements.ORE_DIAMOND), featureRegistry.getOrThrow(OrePlacements.ORE_DIAMOND_LARGE), featureRegistry.getOrThrow(OrePlacements.ORE_DIAMOND_BURIED));
		return new RemoveFeaturesBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_OVERWORLD), features, Set.of(Decoration.UNDERGROUND_ORES));
	}
	
	private static @NotNull ReplaceBiomeModifier createReplaceEnd(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> toAdd = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.ENDERITE_ORE_RARE), featureRegistry.getOrThrow(XSOrePlacements.ENDERITE_ORE_BURIED));
		List<ResourceLocation> toRemove = Lists.newArrayList(new ResourceLocation(XOres.MOD_ID, "enderite_ore_rare"), new ResourceLocation(XOres.MOD_ID, "enderite_ore_buried"));
		return new ReplaceBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_END), toAdd, toRemove);
	}
	
	private static @NotNull ReplaceBiomeModifier createReplaceOverworld(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> toAdd = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.JADE_ORE_MIDDLE), featureRegistry.getOrThrow(XSOrePlacements.SAPHIRE_ORE), featureRegistry.getOrThrow(XSOrePlacements.SAPHIRE_ORE_BURIED),
			featureRegistry.getOrThrow(XSOrePlacements.LIMONITE_ORE_BURIED), featureRegistry.getOrThrow(XSOrePlacements.LIMONITE_ORE_DEEP_BURIED));
		List<ResourceLocation> toRemove = Lists.newArrayList(new ResourceLocation(XOres.MOD_ID, "jade_ore_middle"), new ResourceLocation(XOres.MOD_ID, "saphire_ore"), new ResourceLocation(XOres.MOD_ID, "saphire_ore_buried"),
			new ResourceLocation(XOres.MOD_ID, "limonite_ore_buried"), new ResourceLocation(XOres.MOD_ID, "limonite_ore_deep_buried"));
		return new ReplaceBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_END), toAdd, toRemove);
	}
	
	private static @NotNull ReplaceBiomeModifier createReplacePeak(@NotNull HolderGetter<Biome> biomeRegistry, @NotNull HolderGetter<PlacedFeature> featureRegistry) {
		HolderSet<PlacedFeature> toAdd = HolderSet.direct(featureRegistry.getOrThrow(XSOrePlacements.JADE_ORE_UPPER), featureRegistry.getOrThrow(XSOrePlacements.SAPHIRE_ORE_RARE_UPPER));
		List<ResourceLocation> toRemove = Lists.newArrayList(new ResourceLocation(XOres.MOD_ID, "jade_ore_upper"), new ResourceLocation(XOres.MOD_ID, "saphire_ore_rare_upper"));
		return new ReplaceBiomeModifier(biomeRegistry.getOrThrow(BiomeTags.IS_END), toAdd, toRemove);
	}
}