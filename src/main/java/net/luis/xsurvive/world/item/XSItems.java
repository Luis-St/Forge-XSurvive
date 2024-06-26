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
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, XSurvive.MOD_ID);
	
	public static final RegistryObject<EnchantedGoldenBookItem> ENCHANTED_GOLDEN_BOOK = ITEMS.register("enchanted_golden_book", () -> {
		return new EnchantedGoldenBookItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
	});
	public static final RegistryObject<GlintColorItem> WHITE_RUNE = ITEMS.register("white_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.WHITE.getId());
	});
	public static final RegistryObject<GlintColorItem> ORANGE_RUNE = ITEMS.register("orange_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.ORANGE.getId());
	});
	public static final RegistryObject<GlintColorItem> MAGENTA_RUNE = ITEMS.register("magenta_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.MAGENTA.getId());
	});
	public static final RegistryObject<GlintColorItem> LIGHT_BLUE_RUNE = ITEMS.register("light_blue_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.LIGHT_BLUE.getId());
	});
	public static final RegistryObject<GlintColorItem> YELLOW_RUNE = ITEMS.register("yellow_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.YELLOW.getId());
	});
	public static final RegistryObject<GlintColorItem> LIME_RUNE = ITEMS.register("lime_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.LIME.getId());
	});
	public static final RegistryObject<GlintColorItem> PINK_RUNE = ITEMS.register("pink_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.PINK.getId());
	});
	public static final RegistryObject<GlintColorItem> GRAY_RUNE = ITEMS.register("gray_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.GRAY.getId());
	});
	public static final RegistryObject<GlintColorItem> LIGHT_GRAY_RUNE = ITEMS.register("light_gray_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.LIGHT_GRAY.getId());
	});
	public static final RegistryObject<GlintColorItem> CYAN_RUNE = ITEMS.register("cyan_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.CYAN.getId());
	});
	public static final RegistryObject<GlintColorItem> PURPLE_RUNE = ITEMS.register("purple_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.PURPLE.getId());
	});
	public static final RegistryObject<GlintColorItem> BLUE_RUNE = ITEMS.register("blue_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.BLUE.getId());
	});
	public static final RegistryObject<GlintColorItem> BROWN_RUNE = ITEMS.register("brown_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.BROWN.getId());
	});
	public static final RegistryObject<GlintColorItem> GREEN_RUNE = ITEMS.register("green_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.GREEN.getId());
	});
	public static final RegistryObject<GlintColorItem> RED_RUNE = ITEMS.register("red_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.RED.getId());
	});
	public static final RegistryObject<GlintColorItem> BLACK_RUNE = ITEMS.register("black_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), DyeColor.BLACK.getId());
	});
	public static final RegistryObject<GlintColorItem> RAINBOW_RUNE = ITEMS.register("rainbow_rune", () -> {
		return new GlintColorItem(new Item.Properties().stacksTo(1), 16);
	});
	public static final RegistryObject<Item> HONEY_MELON_SEEDS = ITEMS.register("honey_melon_seeds", () -> {
		return new ItemNameBlockItem(XSBlocks.HONEY_MELON_STEM.get(), new Item.Properties());
	});
	public static final RegistryObject<Item> HONEY_MELON_SLICE = ITEMS.register("honey_melon_slice", () -> {
		return new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.7F).build()));
	});
	public static final RegistryObject<Item> DIAMOND_APPLE = ITEMS.register("diamond_apple", () -> {
		return new Item(new Item.Properties().rarity(Rarity.RARE).food(new FoodProperties.Builder().nutrition(8).saturationMod(1.2F).effect(() -> {
			return new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 0);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 3000, 0);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 0);
		}, 1.0F).alwaysEat().build()));
	});
	public static final RegistryObject<GlintColorItem> ENCHANTED_DIAMOND_APPLE = ITEMS.register("enchanted_diamond_apple", () -> {
		return new GlintColorItem(new Item.Properties().rarity(Rarity.EPIC).food(new FoodProperties.Builder().nutrition(10).saturationMod(1.2F).effect(() -> {
			return new MobEffectInstance(MobEffects.REGENERATION, 800, 1);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 1);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.ABSORPTION, 4800, 4);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1);
		}, 1.0F).effect(() -> {
			return new MobEffectInstance(MobEffects.DIG_SPEED, 2400, 1);
		}, 1.0F).alwaysEat().build()), DyeColor.LIGHT_BLUE.getId());
	});
	public static final RegistryObject<Item> CURSED_ENDER_EYE = ITEMS.register("cursed_eye_of_ender", () -> {
		return new Item(new Item.Properties());
	});
	
	public static class Keys {
		
		public static final ResourceKey<Item> HONEY_MELON_SEEDS = createKey("honey_melon_seeds");
		
		public static void register() {}
		
		private static @NotNull ResourceKey<Item> createKey(String name) {
			return ResourceKey.create(Registries.ITEM, new ResourceLocation(XSurvive.MOD_ID, name));
		}
	}
}
