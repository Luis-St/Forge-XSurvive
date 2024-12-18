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

package net.luis.xsurvive.data.provider.base.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.entity.npc.XSVillagerProfessions;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.alchemy.XSPotions;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class XSLanguageProvider extends LanguageProvider {
	
	public XSLanguageProvider(@NotNull DataGenerator generator) {
		super(generator.getPackOutput(), XSurvive.MOD_ID, "en_us");
	}
	
	public static @NotNull String getName(@Nullable ResourceLocation location) {
		StringBuilder name = new StringBuilder();
		if (location != null) {
			String[] nameParts = location.getPath().split("_");
			for (String namePart : nameParts) {
				String startChar = namePart.substring(0, 1).toUpperCase();
				name.append(startChar).append(namePart.substring(1)).append(" ");
			}
		}
		return name.toString().trim();
	}
	
	public static @NotNull String getLocalizedName(@Nullable ResourceLocation location) {
		String name = getName(location);
		if (name.contains(" Of ")) {
			return name.replace(" Of ", " of ");
		}
		if (name.contains(" The ")) {
			return name.replace(" The ", " the ");
		}
		return name;
	}
	
	@Override
	protected void addTranslations() {
		for (Item item : XSItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, getLocalizedName(ForgeRegistries.ITEMS.getKey(item)));
		}
		for (Item item : XSBlocks.ITEMS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(item, getName(ForgeRegistries.ITEMS.getKey(item)));
		}
		for (MobEffect mobEffect : XSMobEffects.MOB_EFFECTS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(mobEffect, getName(ForgeRegistries.MOB_EFFECTS.getKey(mobEffect)));
		}
		for (Potion potion : XSPotions.POTIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			this.add(potion);
		}
		for (VillagerProfession profession : XSVillagerProfessions.VILLAGER_PROFESSIONS.getEntries().stream().map(RegistryObject::get).toList()) {
			ResourceLocation location = Objects.requireNonNull(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession));
			this.add("entity.minecraft.villager." + XSurvive.MOD_ID + "." + location.getPath(), getName(location));
		}
		this.add("enchantment.xsurvive.aspect_of_the_end", "Aspect of The End");
		this.add("enchantment.xsurvive.blasting", "Blasting");
		this.add("enchantment.xsurvive.curse_of_breaking", "Curse of Breaking");
		this.add("enchantment.xsurvive.curse_of_harming", "Curse of Harming");
		this.add("enchantment.xsurvive.ender_slayer", "Ender Slayer");
		this.add("enchantment.xsurvive.experience", "Experience");
		this.add("enchantment.xsurvive.explosion", "Explosion");
		this.add("enchantment.xsurvive.frost_aspect", "Frost Aspect");
		this.add("enchantment.xsurvive.growth", "Growth");
		this.add("enchantment.xsurvive.harvesting", "Harvesting");
		this.add("enchantment.xsurvive.multi_drop", "Multi Drop");
		this.add("enchantment.xsurvive.poison_aspect", "Poison Aspect");
		this.add("enchantment.xsurvive.reaching", "Reaching");
		this.add("enchantment.xsurvive.replanting", "Replanting");
		this.add("enchantment.xsurvive.smelting", "Smelting");
		this.add("enchantment.xsurvive.thunderbolt", "Thunderbolt");
		this.add("enchantment.xsurvive.void_protection", "Void Protection");
		this.add("enchantment.xsurvive.void_walker", "Void Walker");
		
		this.add("item_tab." + XSurvive.MOD_ID + ".runes", "Runes");
		this.add("item_tab." + XSurvive.MOD_ID + ".golden_book", "Golden Books");
		
		this.add("death.attack.curse_of_harming", "{0} die by his own weapon");
		this.add("death.attack.curse_of_harming.player", "{0} die by his own weapon whilst fighting {1}");
		
		this.add(XSurvive.MOD_ID + ".container.smelting_furnace", "Smelting Furnace");
		this.add(XSurvive.MOD_ID + ".gui.recipebook.toggleRecipes.smeltable", "Showing Smeltable");
		
		this.add("options." + XSurvive.MOD_ID + ".gamma.infinite", "Infinite");
		this.add("options." + XSurvive.MOD_ID + ".glint.defaultVanilla", "Default vanilla");
		this.add("options." + XSurvive.MOD_ID + ".glint.maxVanilla", "Max vanilla");
		this.add("options." + XSurvive.MOD_ID + ".glint.original", "Original");
		
		this.add(XSurvive.MOD_ID + ".commands.gamma.get", "Gamma value is set to {0}");
		this.add(XSurvive.MOD_ID + ".commands.gamma.success", "Successfully sets the gamma value to {0}");
	}
	
	private void add(@NotNull Potion potion) {
		ResourceLocation location = Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(potion));
		String potionName = location.getPath();
		this.add("item.minecraft.potion.effect." + potionName, "Potion of " + this.getPotionName(location));
		this.add("item.minecraft.splash_potion.effect." + potionName, "Splash Potion of " + this.getPotionName(location));
		this.add("item.minecraft.lingering_potion.effect." + potionName, "Lingering Potion of " + this.getPotionName(location));
		this.add("item.minecraft.tipped_arrow.effect." + potionName, "Arrow of " + this.getPotionName(location));
	}
	
	private @NotNull String getPotionName(@NotNull ResourceLocation location) {
		if (location.getPath().startsWith("long_")) {
			String path = location.getPath();
			return getName(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), path.replace("long_", "")));
		} else if (location.getPath().startsWith("strong_")) {
			String path = location.getPath();
			return getName(ResourceLocation.fromNamespaceAndPath(location.getNamespace(), path.replace("strong_", "")));
		}
		return getName(location);
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Languages";
	}
}
