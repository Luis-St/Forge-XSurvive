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
import net.luis.xsurvive.core.XSResourceKeys;
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
	
	public static final ResourceKey<Enchantment> MULTI_DROP = XSResourceKeys.createEnchantmentKey("multi_drop");
	public static final ResourceKey<Enchantment> ENDER_SLAYER = XSResourceKeys.createEnchantmentKey("ender_slayer");
	public static final ResourceKey<Enchantment> FROST_ASPECT = XSResourceKeys.createEnchantmentKey("frost_aspect");
	public static final ResourceKey<Enchantment> POISON_ASPECT = XSResourceKeys.createEnchantmentKey("poison_aspect");
	public static final ResourceKey<Enchantment> EXPERIENCE = XSResourceKeys.createEnchantmentKey("experience");
	public static final ResourceKey<Enchantment> SMELTING = XSResourceKeys.createEnchantmentKey("smelting");
	public static final ResourceKey<Enchantment> CURSE_OF_BREAKING = XSResourceKeys.createEnchantmentKey("curse_of_breaking");
	public static final ResourceKey<Enchantment> CURSE_OF_HARMING = XSResourceKeys.createEnchantmentKey("curse_of_harming");
	public static final ResourceKey<Enchantment> VOID_WALKER = XSResourceKeys.createEnchantmentKey("void_walker");
	public static final ResourceKey<Enchantment> BLASTING = XSResourceKeys.createEnchantmentKey("blasting");
	public static final ResourceKey<Enchantment> THUNDERBOLT = XSResourceKeys.createEnchantmentKey("thunderbolt");
	public static final ResourceKey<Enchantment> VOID_PROTECTION = XSResourceKeys.createEnchantmentKey("void_protection");
	public static final ResourceKey<Enchantment> HARVESTING = XSResourceKeys.createEnchantmentKey("harvesting");
	public static final ResourceKey<Enchantment> REPLANTING = XSResourceKeys.createEnchantmentKey("replanting");
	public static final ResourceKey<Enchantment> ASPECT_OF_THE_END = XSResourceKeys.createEnchantmentKey("aspect_of_the_end");
	public static final ResourceKey<Enchantment> EXPLOSION = XSResourceKeys.createEnchantmentKey("explosion");
	public static final ResourceKey<Enchantment> REACHING = XSResourceKeys.createEnchantmentKey("reaching");
	public static final ResourceKey<Enchantment> GROWTH = XSResourceKeys.createEnchantmentKey("growth");
	
	public static void register() {}
}
