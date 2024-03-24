package net.luis.xsurvive.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSBiomeTags {
	
	public static final TagKey<Biome> IS_MOUNTAIN = bind(new ResourceLocation(XSurvive.MOD_ID, "is_mountain"));
	
	public static void register() {}
	
	private static @NotNull TagKey<Biome> bind(@NotNull ResourceLocation location) {
		return TagKey.create(Registries.BIOME, location);
	}
}
