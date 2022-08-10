package net.luis.xsurvive.world.level.storage.loot;

import com.mojang.serialization.Codec;

import net.luis.xsurvive.XSurvive;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveGlobalLootModifiers {
	
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, XSurvive.MOD_ID);
	
	public static final RegistryObject<Codec<MultiDropModifier>> MULTI_DROP_MODIFIER = LOOT_MODIFIERS.register("multi_drop_modifier", () -> {
		return MultiDropModifier.CODEC;
	});
	public static final RegistryObject<Codec<RuneItemModifier>> RUNE_ITEM_MODIFIER = LOOT_MODIFIERS.register("rune_item_modifier", () -> {
		return RuneItemModifier.CODEC;
	});
	public static final RegistryObject<Codec<GoldenBookModifier>> GOLDEN_BOOK_MODIFIER = LOOT_MODIFIERS.register("golden_book_modifier", () -> {
		return GoldenBookModifier.CODEC;
	});
	public static final RegistryObject<Codec<DiamondAppleModifier>> DIAMOND_APPLE_MODIFIER = LOOT_MODIFIERS.register("diamond_apple_modifier", () -> {
		return DiamondAppleModifier.CODEC;
	});
	
}
