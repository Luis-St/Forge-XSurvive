package net.luis.xsurvive.data.provider.level;

import net.luis.xsurvive.data.provider.level.biome.XSBiomeModifierProvider;
import net.luis.xsurvive.data.provider.level.feature.XSConfiguredFeatureProvider;
import net.luis.xsurvive.data.provider.level.placement.XSPlacedFeatureProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

public class XSLevelProvider {
	
	public static RegistrySetBuilder createProvider() {
		RegistrySetBuilder builder = new RegistrySetBuilder();
		builder.add(Registries.CONFIGURED_FEATURE, XSConfiguredFeatureProvider::create);
		builder.add(Registries.PLACED_FEATURE, XSPlacedFeatureProvider::create);
		builder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, XSBiomeModifierProvider::create);
		return builder;
	}
	
}
