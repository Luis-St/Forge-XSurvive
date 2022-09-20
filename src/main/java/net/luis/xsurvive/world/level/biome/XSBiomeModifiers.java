package net.luis.xsurvive.world.level.biome;

import com.mojang.serialization.Codec;

import net.luis.xsurvive.XSurvive;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * @author Luis-st
 *
 */

public class XSBiomeModifiers {
	
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, XSurvive.MOD_ID);
	
	public static final RegistryObject<Codec<ModBasedBiomeModifier>> MOD_BASED_BIOME_MODIFIER = BIOME_MODIFIERS.register("mod_based_biome_modifier", () -> {
		return ModBasedBiomeModifier.CODEC;
	});
	public static final RegistryObject<Codec<IfElseBiomeModifier>> IF_ELSE_BIOME_MODIFIER = BIOME_MODIFIERS.register("if_else_biome_modifier", () -> {
		return IfElseBiomeModifier.CODEC;
	});
	
}
