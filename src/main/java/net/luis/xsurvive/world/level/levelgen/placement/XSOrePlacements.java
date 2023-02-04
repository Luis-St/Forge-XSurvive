package net.luis.xsurvive.world.level.levelgen.placement;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 *
 * @author Luis-st
 *
 */

public class XSOrePlacements {
	
	public static final ResourceKey<PlacedFeature> COAL_ORE_UPPER = createKey("coal_ore_upper");
	public static final ResourceKey<PlacedFeature> COAL_ORE_LOWER = createKey("coal_ore_lower");
	public static final ResourceKey<PlacedFeature> COPPER_ORE = createKey("copper_ore");
	public static final ResourceKey<PlacedFeature> COPPER_ORE_LARGE = createKey("copper_ore_large");
	public static final ResourceKey<PlacedFeature> IRON_ORE_UPPER = createKey("iron_ore_upper");
	public static final ResourceKey<PlacedFeature> IRON_ORE_MIDDLE = createKey("iron_ore_middle");
	public static final ResourceKey<PlacedFeature> IRON_ORE_SMALL = createKey("iron_ore_small");
	public static final ResourceKey<PlacedFeature> GOLD_ORE = createKey("gold_ore");
	public static final ResourceKey<PlacedFeature> GOLD_ORE_EXTRA = createKey("gold_ore_extra");
	public static final ResourceKey<PlacedFeature> GOLD_ORE_LOWER = createKey("gold_ore_lower");
	public static final ResourceKey<PlacedFeature> LAPIS_ORE = createKey("lapis_ore");
	public static final ResourceKey<PlacedFeature> LAPIS_ORE_BURIED = createKey("lapis_ore_buried");
	public static final ResourceKey<PlacedFeature> REDSTONE_ORE = createKey("redstone_ore");
	public static final ResourceKey<PlacedFeature> REDSTONE_ORE_LOWER = createKey("redstone_ore_lower");
	public static final ResourceKey<PlacedFeature> DIAMOND_ORE = createKey("diamond_ore");
	public static final ResourceKey<PlacedFeature> DIAMOND_ORE_LARGE = createKey("diamond_ore_large");
	public static final ResourceKey<PlacedFeature> DIAMOND_ORE_BURIED = createKey("diamond_ore_buried");
	public static final ResourceKey<PlacedFeature> EMERALD_ORE = createKey("emerald_ore");
	public static final ResourceKey<PlacedFeature> NETHER_GOLD_ORE = createKey("nether_gold_ore");
	public static final ResourceKey<PlacedFeature> QUARTZ_ORE = createKey("quartz_ore");
	public static final ResourceKey<PlacedFeature> ANCIENT_DEBRIS_ORE_SMALL = createKey("ancient_debris_ore_small");
	public static final ResourceKey<PlacedFeature> ANCIENT_DEBRIS_ORE_LARGE = createKey("ancient_debris_ore_large");
	public static final ResourceKey<PlacedFeature> JADE_ORE_UPPER = createKey("jade_ore_upper");
	public static final ResourceKey<PlacedFeature> JADE_ORE_MIDDLE = createKey("jade_ore_middle");
	public static final ResourceKey<PlacedFeature> SAPHIRE_ORE_RARE_UPPER = createKey("saphire_ore_rare_upper");
	public static final ResourceKey<PlacedFeature> SAPHIRE_ORE = createKey("saphire_ore");
	public static final ResourceKey<PlacedFeature> SAPHIRE_ORE_BURIED = createKey("saphire_ore_buried");
	public static final ResourceKey<PlacedFeature> LIMONITE_ORE_BURIED = createKey("limonite_ore_buried");
	public static final ResourceKey<PlacedFeature> LIMONITE_ORE_DEEP_BURIED = createKey("limonite_ore_deep_buried");
	public static final ResourceKey<PlacedFeature> ENDERITE_ORE_RARE = createKey("enderite_ore_rare");
	public static final ResourceKey<PlacedFeature> ENDERITE_ORE_BURIED = createKey("enderite_ore_buried");
	
	public static void register() {
		
	}
	
	private static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(XSurvive.MOD_ID, name));
	}
	
}
