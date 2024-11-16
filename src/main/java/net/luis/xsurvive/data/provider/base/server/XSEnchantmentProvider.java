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

package net.luis.xsurvive.data.provider.base.server;

import net.luis.xsurvive.tag.XSEntityTypeTags;
import net.luis.xsurvive.tag.XSItemTags;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSEnchantmentProvider {
	
	public static void create(@NotNull BootstrapContext<Enchantment> context) {
		HolderGetter<Enchantment> enchantmentLookup = context.lookup(Registries.ENCHANTMENT);
		HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
		HolderGetter<EntityType<?>> entityTypeLookup = context.lookup(Registries.ENTITY_TYPE);
		
		register(context, XSEnchantments.MULTI_DROP, createMultiDrop(itemLookup));
		register(context, XSEnchantments.ENDER_SLAYER, createEnderSlayer(enchantmentLookup, itemLookup, entityTypeLookup));
		register(context, XSEnchantments.FROST_ASPECT, createFrostAspect(enchantmentLookup, itemLookup));
		register(context, XSEnchantments.POISON_ASPECT, createPoisonAspect(itemLookup));
		register(context, XSEnchantments.EXPERIENCE, createExperience(itemLookup));
		register(context, XSEnchantments.SMELTING, createSmelting(itemLookup));
		register(context, XSEnchantments.CURSE_OF_BREAKING, createCurseOfBreaking(itemLookup));
		register(context, XSEnchantments.CURSE_OF_HARMING, createCurseOfHarming(itemLookup));
		register(context, XSEnchantments.VOID_WALKER, createVoidWalker(enchantmentLookup, itemLookup));
		register(context, XSEnchantments.BLASTING, createBlasting(enchantmentLookup, itemLookup));
		register(context, XSEnchantments.THUNDERBOLT, createThunderbolt(enchantmentLookup, itemLookup));
		register(context, XSEnchantments.VOID_PROTECTION, createVoidProtection(enchantmentLookup, itemLookup));
		register(context, XSEnchantments.HARVESTING, createHarvesting(itemLookup));
		register(context, XSEnchantments.REPLANTING, createReplanting(itemLookup));
		register(context, XSEnchantments.ASPECT_OF_THE_END, createAspectOfTheEnd(itemLookup));
		register(context, XSEnchantments.EXPLOSION, createExplosion(itemLookup));
		register(context, XSEnchantments.REACHING, createReaching(itemLookup));
		register(context, XSEnchantments.GROWTH, createGrowth(itemLookup));
	}
	
	public static Enchantment.@NotNull Builder createMultiDrop(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.MINING_ENCHANTABLE),
					1,
					1,
					Enchantment.constantCost(20),
					Enchantment.constantCost(50),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createEnderSlayer(@NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<EntityType<?>> entityTypeLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
					itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
					5,
					5,
					Enchantment.dynamicCost(5, 25),
					Enchantment.dynamicCost(25, 45),
					2,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.exclusiveWith(enchantmentLookup.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
			.withEffect(
				EnchantmentEffectComponents.DAMAGE,
				new AddValue(LevelBasedValue.perLevel(2.5F)),
				LootItemEntityPropertyCondition.hasProperties(
					LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeLookup, XSEntityTypeTags.SENSITIVE_TO_ENDER_SLAYER))
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createFrostAspect(@NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.FIRE_ASPECT_ENCHANTABLE),
					itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
					2,
					2,
					Enchantment.dynamicCost(10, 20),
					Enchantment.dynamicCost(60, 20),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(Enchantments.FIRE_ASPECT)));
	}
	
	public static Enchantment.@NotNull Builder createPoisonAspect(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.FIRE_ASPECT_ENCHANTABLE),
					itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
					2,
					2,
					Enchantment.dynamicCost(10, 20),
					Enchantment.dynamicCost(60, 20),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createExperience(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.MINING_ENCHANTABLE),
				5,
				4,
				Enchantment.dynamicCost(15, 10),
				Enchantment.dynamicCost(40, 15),
				2,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createSmelting(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.MINING_ENCHANTABLE),
				2,
				1,
				Enchantment.constantCost(20),
				Enchantment.constantCost(50),
				3,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createCurseOfHarming(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
				1,
				1,
				Enchantment.constantCost(25),
				Enchantment.constantCost(50),
				4,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createCurseOfBreaking(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE),
				1,
				1,
				Enchantment.constantCost(25),
				Enchantment.constantCost(50),
				4,
				EquipmentSlotGroup.ANY
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createVoidWalker(@NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
				1,
				1,
				Enchantment.constantCost(20),
				Enchantment.constantCost(50),
				4,
				EquipmentSlotGroup.FEET
			)
		).exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(Enchantments.DEPTH_STRIDER), enchantmentLookup.getOrThrow(Enchantments.FROST_WALKER)));
	}
	
	public static Enchantment.@NotNull Builder createBlasting(@NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.MINING_ENCHANTABLE),
				5,
				4,
				Enchantment.dynamicCost(1, 5),
				Enchantment.dynamicCost(20, 10),
				2,
				EquipmentSlotGroup.MAINHAND
			)
		).exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(Enchantments.SILK_TOUCH), enchantmentLookup.getOrThrow(XSEnchantments.SMELTING)));
	}
	
	public static Enchantment.@NotNull Builder createThunderbolt(@NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
				itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
				1,
				1,
				Enchantment.constantCost(20),
				Enchantment.constantCost(50),
				4,
				EquipmentSlotGroup.MAINHAND
			)
		).exclusiveWith(HolderSet.direct(
			enchantmentLookup.getOrThrow(Enchantments.KNOCKBACK), enchantmentLookup.getOrThrow(Enchantments.FIRE_ASPECT),
			enchantmentLookup.getOrThrow(XSEnchantments.SMELTING), enchantmentLookup.getOrThrow(XSEnchantments.FROST_ASPECT), enchantmentLookup.getOrThrow(XSEnchantments.POISON_ASPECT)
		));
	}
	
	public static Enchantment.@NotNull Builder createVoidProtection(@NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(XSItemTags.CHEST_WEARABLE),
				itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
				1,
				1,
				Enchantment.constantCost(20),
				Enchantment.constantCost(50),
				4,
				EquipmentSlotGroup.ARMOR
			)
		).exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(Enchantments.THORNS)));
	}
	
	public static Enchantment.@NotNull Builder createHarvesting(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.AXES),
				5,
				5,
				Enchantment.dynamicCost(1, 5),
				Enchantment.dynamicCost(30, 10),
				2,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createReplanting(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.HOES),
				2,
				1,
				Enchantment.constantCost(20),
				Enchantment.constantCost(50),
				3,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createAspectOfTheEnd(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
					1,
					4,
					Enchantment.constantCost(20),
					Enchantment.constantCost(50),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createExplosion(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.BOW_ENCHANTABLE),
				2,
				4,
				Enchantment.dynamicCost(1, 5),
				Enchantment.dynamicCost(30, 10),
				3,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createReaching(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.MINING_ENCHANTABLE),
				1,
				3,
				Enchantment.dynamicCost(30, 10),
				Enchantment.dynamicCost(50, 10),
				4,
				EquipmentSlotGroup.MAINHAND
			)
		);
	}
	
	public static Enchantment.@NotNull Builder createGrowth(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
			Enchantment.definition(
				itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
				10,
				5,
				Enchantment.dynamicCost(1, 10),
				Enchantment.dynamicCost(25, 15),
				1,
				EquipmentSlotGroup.ARMOR
			)
		);
	}
	
	private static void register(@NotNull BootstrapContext<Enchantment> context, @NotNull ResourceKey<Enchantment> key, Enchantment.@NotNull Builder builder) {
		context.register(key, builder.build(key.location()));
	}
}
