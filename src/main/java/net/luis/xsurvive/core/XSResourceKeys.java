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

package net.luis.xsurvive.core;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSResourceKeys {
	
	public static final ResourceKey<Block> HONEY_MELON = createBlockKey("honey_melon");
	public static final ResourceKey<Block> HONEY_MELON_STEM = createBlockKey("honey_melon_stem");
	public static final ResourceKey<Block> ATTACHED_HONEY_MELON_STEM = createBlockKey("attached_honey_melon_stem");
	
	public static final ResourceKey<Item> HONEY_MELON_SEEDS = createItemKey("honey_melon_seeds");
	
	public static final ResourceKey<DamageType> CURSE_OF_HARMING = createDamageTypeKey("curse_of_harming");
	
	public static void register() {}
	
	public static @NotNull ResourceKey<Block> createBlockKey(@NotNull String name) {
		return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
	
	public static @NotNull ResourceKey<Item> createItemKey(@NotNull String name) {
		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
	
	public static @NotNull ResourceKey<DamageType> createDamageTypeKey(@NotNull String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
	
	public static @NotNull ResourceKey<Enchantment> createEnchantmentKey(@NotNull String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
	
	public static @NotNull ResourceKey<EntityType<?>> createEntityTypeKey(@NotNull String name) {
		return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
	
	public static @NotNull ResourceKey<RecipePropertySet> createRecipePropertySetKey(@NotNull String name) {
		return ResourceKey.create(RecipePropertySet.TYPE_KEY, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
}
