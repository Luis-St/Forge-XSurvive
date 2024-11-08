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

package net.luis.xsurvive.world.item.enchantment;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSEnchantments {
	
	public static final ResourceKey<Enchantment> MULTI_DROP = createKey("multi_drop");
	public static final ResourceKey<Enchantment> ENDER_SLAYER = createKey("ender_slayer");
	public static final ResourceKey<Enchantment> FROST_ASPECT = createKey("frost_aspect");
	public static final ResourceKey<Enchantment> POISON_ASPECT = createKey("poison_aspect");
	public static final ResourceKey<Enchantment> EXPERIENCE = createKey("experience");
	public static final ResourceKey<Enchantment> SMELTING = createKey("smelting");
	public static final ResourceKey<Enchantment> CURSE_OF_BREAKING = createKey("curse_of_breaking");
	public static final ResourceKey<Enchantment> CURSE_OF_HARMING = createKey("curse_of_harming");
	public static final ResourceKey<Enchantment> VOID_WALKER = createKey("void_walker");
	public static final ResourceKey<Enchantment> BLASTING = createKey("blasting");
	public static final ResourceKey<Enchantment> THUNDERBOLT = createKey("thunderbolt");
	public static final ResourceKey<Enchantment> VOID_PROTECTION = createKey("void_protection");
	public static final ResourceKey<Enchantment> HARVESTING = createKey("harvesting");
	public static final ResourceKey<Enchantment> REPLANTING = createKey("replanting");
	public static final ResourceKey<Enchantment> ASPECT_OF_THE_END = createKey("aspect_of_the_end");
	public static final ResourceKey<Enchantment> EXPLOSION = createKey("explosion");
	public static final ResourceKey<Enchantment> REACHING = createKey("reaching");
	public static final ResourceKey<Enchantment> GROWTH = createKey("growth");
	
	public static void register() {}
	
	private static @NotNull ResourceKey<Enchantment> createKey(@NotNull String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, name));
	}
}
