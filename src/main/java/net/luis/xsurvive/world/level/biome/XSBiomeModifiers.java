package net.luis.xsurvive.world.level.biome;

import com.mojang.serialization.Codec;

import net.luis.xsurvive.XSurvive;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
	
	public static final RegistryObject<Codec<ReplaceBiomeModifier>> MOD_BASED_BIOME_MODIFIER = BIOME_MODIFIERS.register("replace_biome_modifier", () -> {
		return ReplaceBiomeModifier.CODEC;
	});
	public static final RegistryObject<Codec<IfElseBiomeModifier>> IF_ELSE_BIOME_MODIFIER = BIOME_MODIFIERS.register("if_else_biome_modifier", () -> {
		return IfElseBiomeModifier.CODEC;
	});
	
	public static class Keys {
		
		public static final ResourceKey<BiomeModifier> ADD_TO_BADLANDS = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "add_to_badlands"));
		public static final ResourceKey<BiomeModifier> ADD_COPPER_TO_OVERWORLD = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "add_copper_to_overworld"));
		public static final ResourceKey<BiomeModifier> ADD_TO_MOUNTAINS = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "add_to_mountains"));
		public static final ResourceKey<BiomeModifier> ADD_TO_NETHER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "add_to_nether"));
		public static final ResourceKey<BiomeModifier> ADD_TO_OVERWORLD = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "add_to_overworld"));
		
		public static final ResourceKey<BiomeModifier> REMOVE_FROM_NETHER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "remove_from_nether"));
		public static final ResourceKey<BiomeModifier> REMOVE_FROM_OVERWORLD = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "remove_from_overworld"));
		
		public static final ResourceKey<BiomeModifier> REPLACE_END = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "replace_end"));
		public static final ResourceKey<BiomeModifier> REPLACE_OVERWORLD = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "replace_overworld"));
		public static final ResourceKey<BiomeModifier> REPLACE_PEAK = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(XSurvive.MOD_ID, "replace_peak"));
		
	}
	
}
