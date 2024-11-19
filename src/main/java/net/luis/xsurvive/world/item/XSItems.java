/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.world.item;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.core.XSResourceKeys;
import net.luis.xsurvive.core.components.XSDataComponents;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	public static final RegistryObject<EnchantedGoldenBookItem> ENCHANTED_GOLDEN_BOOK = register("enchanted_golden_book", (properties) -> {
		return new EnchantedGoldenBookItem(properties.stacksTo(1).rarity(Rarity.RARE));
	});
	public static final RegistryObject<Item> WHITE_RUNE = register("white_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.WHITE.getId()));
	});
	public static final RegistryObject<Item> ORANGE_RUNE = register("orange_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.ORANGE.getId()));
	});
	public static final RegistryObject<Item> MAGENTA_RUNE = register("magenta_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.MAGENTA.getId()));
	});
	public static final RegistryObject<Item> LIGHT_BLUE_RUNE = register("light_blue_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.LIGHT_BLUE.getId()));
	});
	public static final RegistryObject<Item> YELLOW_RUNE = register("yellow_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.YELLOW.getId()));
	});
	public static final RegistryObject<Item> LIME_RUNE = register("lime_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.LIME.getId()));
	});
	public static final RegistryObject<Item> PINK_RUNE = register("pink_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.PINK.getId()));
	});
	public static final RegistryObject<Item> GRAY_RUNE = register("gray_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.GRAY.getId()));
	});
	public static final RegistryObject<Item> LIGHT_GRAY_RUNE = register("light_gray_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.LIGHT_GRAY.getId()));
	});
	public static final RegistryObject<Item> CYAN_RUNE = register("cyan_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.CYAN.getId()));
	});
	public static final RegistryObject<Item> PURPLE_RUNE = register("purple_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.PURPLE.getId()));
	});
	public static final RegistryObject<Item> BLUE_RUNE = register("blue_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.BLUE.getId()));
	});
	public static final RegistryObject<Item> BROWN_RUNE = register("brown_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.BROWN.getId()));
	});
	public static final RegistryObject<Item> GREEN_RUNE = register("green_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.GREEN.getId()));
	});
	public static final RegistryObject<Item> RED_RUNE = register("red_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.RED.getId()));
	});
	public static final RegistryObject<Item> BLACK_RUNE = register("black_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.BLACK.getId()));
	});
	public static final RegistryObject<Item> RAINBOW_RUNE = register("rainbow_rune", (properties) -> {
		return new Item(properties.stacksTo(1).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), 16));
	});
	public static final RegistryObject<Item> HONEY_MELON_SEEDS = register("honey_melon_seeds", (properties) -> {
		return new BlockItem(XSBlocks.HONEY_MELON_STEM.get(), properties.useItemDescriptionPrefix());
	});
	public static final RegistryObject<Item> HONEY_MELON_SLICE = register("honey_melon_slice", (properties) -> {
		return new Item(properties.food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.7F).build()));
	});
	public static final RegistryObject<Item> DIAMOND_APPLE = register("diamond_apple", (properties) -> {
		return new Item(properties.rarity(Rarity.RARE).food(
			new FoodProperties.Builder().nutrition(8).saturationModifier(1.2F).alwaysEdible().build(),
			Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(
				List.of(
					new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 1),
					new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0),
					new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1),
					new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 1)
				)
			)).build()
		));
	});
	public static final RegistryObject<Item> ENCHANTED_DIAMOND_APPLE = register("enchanted_diamond_apple", (properties) -> {
		return new Item(properties.rarity(Rarity.EPIC).food(
			new FoodProperties.Builder().nutrition(10).saturationModifier(1.2F).alwaysEdible().build(),
			Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(
				List.of(
					new MobEffectInstance(MobEffects.REGENERATION, 800, 1),
					new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 1),
					new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0),
					new MobEffectInstance(MobEffects.ABSORPTION, 4800, 4),
					new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1),
					new MobEffectInstance(MobEffects.DIG_SPEED, 2400, 1)
				)
			)).build()
		).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).component(XSDataComponents.GLINT_COLOR.get(), DyeColor.LIGHT_BLUE.getId()));
	});
	
	private static <T extends Item> @NotNull RegistryObject<T> register(@NotNull String name, @NotNull Function<Item.Properties, T> item) {
		return register(name, new Item.Properties(), item);
	}
	
	private static <T extends Item> @NotNull RegistryObject<T> register(@NotNull String name, Item.@NotNull Properties properties, @NotNull Function<Item.Properties, T> item) {
		return ITEMS.register(name, () -> item.apply(properties.setId(XSResourceKeys.createItemKey(name))));
	}
}
