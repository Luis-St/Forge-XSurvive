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

package net.luis.xsurvive.data.provider.additions;

import net.luis.xsurvive.tag.XSEnchantmentTags;
import net.luis.xsurvive.tag.XSItemTags;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.EnchantmentLevelProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luis-St
 *
 */

public class XSAdditionsEnchantmentProvider {
	
	public static void create(@NotNull BootstrapContext<Enchantment> context) {
		HolderGetter<Enchantment> enchantmentLookup = context.lookup(Registries.ENCHANTMENT);
		HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
		HolderGetter<EntityType<?>> entityTypeLookup = context.lookup(Registries.ENTITY_TYPE);
		HolderGetter<DamageType> damageTypeLookup = context.lookup(Registries.DAMAGE_TYPE);
		
		register(context, Enchantments.PUNCH, createPunch(itemLookup, entityTypeLookup));
		register(context, Enchantments.FIRE_PROTECTION, createFireProtection(itemLookup, enchantmentLookup));
		register(context, Enchantments.BLAST_PROTECTION, createBlastProtection(itemLookup, enchantmentLookup));
		register(context, Enchantments.PROJECTILE_PROTECTION, createProjectileProtection(itemLookup, enchantmentLookup));
		register(context, Enchantments.QUICK_CHARGE, createQuickCharge(itemLookup));
		register(context, Enchantments.THORNS, createThorns(itemLookup, damageTypeLookup));
		register(context, Enchantments.IMPALING, createImpaling(itemLookup, enchantmentLookup, entityTypeLookup));
		register(context, Enchantments.INFINITY, createInfinity(itemLookup));
		register(context, Enchantments.PIERCING, createPiercing(itemLookup));
		register(context, Enchantments.MULTISHOT, createMultishot(itemLookup));
		register(context, Enchantments.RIPTIDE, createRiptide(itemLookup));
	}
	
	public static Enchantment.@NotNull Builder createPunch(@NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<EntityType<?>> entityTypeLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.BOW_ENCHANTABLE),
					2,
					3,
					Enchantment.dynamicCost(12, 20),
					Enchantment.dynamicCost(37, 20),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.withEffect(
				EnchantmentEffectComponents.KNOCKBACK,
				new AddValue(LevelBasedValue.perLevel(1.0F)),
				LootItemEntityPropertyCondition.hasProperties(
					LootContext.EntityTarget.DIRECT_ATTACKER,
					EntityPredicate.Builder.entity().of(entityTypeLookup, EntityTypeTags.ARROWS).build()
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createFireProtection(@NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<Enchantment> enchantmentLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
					5,
					4,
					Enchantment.dynamicCost(10, 8),
					Enchantment.dynamicCost(18, 8),
					2,
					EquipmentSlotGroup.ARMOR
				)
			)
			.exclusiveWith(enchantmentLookup.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
			.withEffect(
				EnchantmentEffectComponents.DAMAGE_PROTECTION,
				new AddValue(LevelBasedValue.perLevel(2.5F)),
				AllOfCondition.allOf(
					DamageSourceCondition.hasDamageSource(
						DamageSourcePredicate.Builder.damageType()
							.tag(TagPredicate.is(DamageTypeTags.IS_FIRE))
							.tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
					)
				)
			)
			.withEffect(
				EnchantmentEffectComponents.ATTRIBUTES,
				new EnchantmentAttributeEffect(
					ResourceLocation.withDefaultNamespace("enchantment.fire_protection"),
					Attributes.BURNING_TIME,
					LevelBasedValue.perLevel(-0.2F),
					AttributeModifier.Operation.ADD_MULTIPLIED_BASE
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createBlastProtection(@NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<Enchantment> enchantmentLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
					2,
					4,
					Enchantment.dynamicCost(5, 8),
					Enchantment.dynamicCost(13, 8),
					4,
					EquipmentSlotGroup.ARMOR
				)
			)
			.exclusiveWith(enchantmentLookup.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
			.withEffect(
				EnchantmentEffectComponents.DAMAGE_PROTECTION,
				new AddValue(LevelBasedValue.perLevel(2.5F)),
				DamageSourceCondition.hasDamageSource(
					DamageSourcePredicate.Builder.damageType()
						.tag(TagPredicate.is(DamageTypeTags.IS_EXPLOSION))
						.tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
				)
			)
			.withEffect(
				EnchantmentEffectComponents.ATTRIBUTES,
				new EnchantmentAttributeEffect(
					ResourceLocation.withDefaultNamespace("enchantment.blast_protection"),
					Attributes.EXPLOSION_KNOCKBACK_RESISTANCE,
					LevelBasedValue.perLevel(0.2F),
					AttributeModifier.Operation.ADD_VALUE
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createProjectileProtection(@NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<Enchantment> enchantmentLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
					5,
					4,
					Enchantment.dynamicCost(3, 6),
					Enchantment.dynamicCost(9, 6),
					2,
					EquipmentSlotGroup.ARMOR
				)
			)
			.exclusiveWith(enchantmentLookup.getOrThrow(XSEnchantmentTags.DEFAULT_PROTECTION))
			.withEffect(
				EnchantmentEffectComponents.DAMAGE_PROTECTION,
				new AddValue(LevelBasedValue.perLevel(2.5F)),
				DamageSourceCondition.hasDamageSource(
					DamageSourcePredicate.Builder.damageType()
						.tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))
						.tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createQuickCharge(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
					5,
					4,
					Enchantment.dynamicCost(12, 20),
					Enchantment.constantCost(50),
					2,
					EquipmentSlotGroup.MAINHAND,
					EquipmentSlotGroup.OFFHAND
				)
			)
			.withSpecialEffect(EnchantmentEffectComponents.CROSSBOW_CHARGE_TIME, new AddValue(LevelBasedValue.perLevel(-0.22F)))
			.withSpecialEffect(
				EnchantmentEffectComponents.CROSSBOW_CHARGING_SOUNDS,
				List.of(
					new CrossbowItem.ChargingSounds(Optional.of(SoundEvents.CROSSBOW_QUICK_CHARGE_1), Optional.empty(), Optional.of(SoundEvents.CROSSBOW_LOADING_END)),
					new CrossbowItem.ChargingSounds(Optional.of(SoundEvents.CROSSBOW_QUICK_CHARGE_2), Optional.empty(), Optional.of(SoundEvents.CROSSBOW_LOADING_END)),
					new CrossbowItem.ChargingSounds(Optional.of(SoundEvents.CROSSBOW_QUICK_CHARGE_3), Optional.empty(), Optional.of(SoundEvents.CROSSBOW_LOADING_END)),
					new CrossbowItem.ChargingSounds(Optional.of(SoundEvents.CROSSBOW_QUICK_CHARGE_3), Optional.empty(), Optional.of(SoundEvents.CROSSBOW_LOADING_END))
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createThorns(@NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<DamageType> damageTypeLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
					itemLookup.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
					1,
					4,
					Enchantment.dynamicCost(10, 20),
					Enchantment.dynamicCost(60, 20),
					8,
					EquipmentSlotGroup.ANY
				)
			)
			.withEffect(
				EnchantmentEffectComponents.POST_ATTACK,
				EnchantmentTarget.VICTIM,
				EnchantmentTarget.ATTACKER,
				AllOf.entityEffects(
					new DamageEntity(LevelBasedValue.perLevel(2.5F, 1.5F), LevelBasedValue.perLevel(2.5F, 1.5F), damageTypeLookup.getOrThrow(DamageTypes.THORNS)),
					new ChangeItemDamage(LevelBasedValue.constant(1.0F))
				),
				LootItemRandomChanceCondition.randomChance(EnchantmentLevelProvider.forEnchantmentLevel(LevelBasedValue.perLevel(0.25F)))
			);
	}
	
	public static Enchantment.@NotNull Builder createImpaling(@NotNull HolderGetter<Item> itemLookup, @NotNull HolderGetter<Enchantment> enchantmentLookup, @NotNull HolderGetter<EntityType<?>> entityTypeLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(XSItemTags.IMPALING_WEAPONS),
					2,
					5,
					Enchantment.dynamicCost(1, 8),
					Enchantment.dynamicCost(21, 8),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.exclusiveWith(enchantmentLookup.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
			.withEffect(
				EnchantmentEffectComponents.DAMAGE,
				new AddValue(LevelBasedValue.perLevel(2.5F)),
				LootItemEntityPropertyCondition.hasProperties(
					LootContext.EntityTarget.THIS,
					EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeLookup, EntityTypeTags.SENSITIVE_TO_IMPALING)).build()
				)
			);
	}
	
	public static Enchantment.@NotNull Builder createInfinity(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
					itemLookup.getOrThrow(ItemTags.BOW_ENCHANTABLE),
					1,
					1,
					Enchantment.constantCost(20),
					Enchantment.constantCost(50),
					8,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.withEffect(
				EnchantmentEffectComponents.AMMO_USE,
				new SetValue(LevelBasedValue.constant(0.0F)),
				MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemLookup, Items.ARROW))
			);
	}
	
	public static Enchantment.@NotNull Builder createPiercing(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
					10,
					4,
					Enchantment.dynamicCost(1, 10),
					Enchantment.constantCost(50),
					1,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.withEffect(
				EnchantmentEffectComponents.PROJECTILE_PIERCING,
				new AddValue(LevelBasedValue.perLevel(1.0F))
			);
	}
	
	public static Enchantment.@NotNull Builder createMultishot(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE),
					2,
					1,
					Enchantment.constantCost(20),
					Enchantment.constantCost(50),
					4,
					EquipmentSlotGroup.MAINHAND
				)
			)
			.withEffect(
				EnchantmentEffectComponents.PROJECTILE_COUNT,
				new AddValue(LevelBasedValue.perLevel(2.0F))
			)
			.withEffect(
				EnchantmentEffectComponents.PROJECTILE_SPREAD,
				new AddValue(LevelBasedValue.perLevel(10.0F))
			);
	}
	
	public static Enchantment.@NotNull Builder createRiptide(@NotNull HolderGetter<Item> itemLookup) {
		return Enchantment.enchantment(
				Enchantment.definition(
					itemLookup.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE),
					2,
					3,
					Enchantment.dynamicCost(17, 7),
					Enchantment.constantCost(50),
					4,
					EquipmentSlotGroup.HAND
				)
			)
			.withSpecialEffect(
				EnchantmentEffectComponents.TRIDENT_SPIN_ATTACK_STRENGTH,
				new AddValue(LevelBasedValue.perLevel(1.5F, 0.75F))
			)
			.withSpecialEffect(
				EnchantmentEffectComponents.TRIDENT_SOUND,
				List.of(SoundEvents.TRIDENT_RIPTIDE_1, SoundEvents.TRIDENT_RIPTIDE_2, SoundEvents.TRIDENT_RIPTIDE_3)
			);
	}
	
	private static void register(@NotNull BootstrapContext<Enchantment> context, @NotNull ResourceKey<Enchantment> key, Enchantment.@NotNull Builder builder) {
		context.register(key, builder.build(key.location()));
	}
}
