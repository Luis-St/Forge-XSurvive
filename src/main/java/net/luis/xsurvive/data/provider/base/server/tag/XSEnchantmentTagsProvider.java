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

package net.luis.xsurvive.data.provider.base.server.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSEnchantmentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.luis.xsurvive.world.item.enchantment.XSEnchantments.*;

/**
 *
 * @author Luis-St
 *
 */

public class XSEnchantmentTagsProvider extends TagsProvider<Enchantment> {
	
	public XSEnchantmentTagsProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider, @NotNull ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), Registries.ENCHANTMENT, lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.@NotNull Provider lookup) {
		this.tag(EnchantmentTags.TOOLTIP_ORDER)
			.add(MULTI_DROP, SMELTING, VOID_WALKER, THUNDERBOLT, VOID_PROTECTION, REPLANTING, ASPECT_OF_THE_END, REACHING)
			.add(ENDER_SLAYER, FROST_ASPECT, POISON_ASPECT, EXPLOSION, BLASTING, HARVESTING, EXPLOSION, GROWTH)
			.add(CURSE_OF_HARMING);
		this.tag(EnchantmentTags.BOOTS_EXCLUSIVE).add(VOID_WALKER);
		this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE).add(ENDER_SLAYER);
		this.tag(EnchantmentTags.TREASURE)
			.add(MULTI_DROP, SMELTING, VOID_WALKER, THUNDERBOLT, VOID_PROTECTION, REPLANTING, ASPECT_OF_THE_END, REACHING);
		this.tag(EnchantmentTags.NON_TREASURE)
			.add(ENDER_SLAYER, FROST_ASPECT, POISON_ASPECT, EXPLOSION, BLASTING, HARVESTING, EXPLOSION, GROWTH);
		this.tag(EnchantmentTags.CURSE)
			.add(CURSE_OF_HARMING);
		this.tag(EnchantmentTags.ON_RANDOM_LOOT)
			.add(CURSE_OF_HARMING);
		this.tag(XSEnchantmentTags.GOLDEN_ENCHANTMENT)
			.add(MULTI_DROP, ENDER_SLAYER, FROST_ASPECT, POISON_ASPECT, EXPERIENCE, BLASTING, HARVESTING, EXPLOSION, REACHING, GROWTH, VOID_PROTECTION)
			.add(Enchantments.PROTECTION, Enchantments.FIRE_PROTECTION, Enchantments.FEATHER_FALLING, Enchantments.BLAST_PROTECTION, Enchantments.PROJECTILE_PROTECTION)
			.add(Enchantments.SHARPNESS, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SMITE, Enchantments.FIRE_ASPECT, Enchantments.IMPALING)
			.add(Enchantments.RESPIRATION, Enchantments.DEPTH_STRIDER, Enchantments.SOUL_SPEED, Enchantments.KNOCKBACK, Enchantments.LOOTING, Enchantments.SWEEPING_EDGE)
			.add(Enchantments.EFFICIENCY, Enchantments.UNBREAKING, Enchantments.FORTUNE, Enchantments.POWER, Enchantments.PUNCH, Enchantments.LUCK_OF_THE_SEA)
			.add(Enchantments.LURE, Enchantments.LOYALTY, Enchantments.RIPTIDE, Enchantments.QUICK_CHARGE, Enchantments.PIERCING, Enchantments.SWIFT_SNEAK);
		this.tag(XSEnchantmentTags.UPGRADE_ENCHANTMENT)
			.add(ASPECT_OF_THE_END, REACHING, VOID_PROTECTION)
			.add(Enchantments.WIND_BURST, Enchantments.BREACH, Enchantments.DENSITY);
		this.tag(XSEnchantmentTags.DEFAULT_PROTECTION).add(Enchantments.PROTECTION);
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Enchantment Tags";
	}
}
