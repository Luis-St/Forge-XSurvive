package net.luis.xsurvive.world.entity;

import com.google.common.collect.Lists;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.ItemStackHelper;
import net.luis.xsurvive.world.item.ItemWeightHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

/**
 *
 * @author Luis-st
 *
 */

public class EntityHelper {
	
	public static boolean isAffectedByEnderSlayer(Entity entity) {
		return entity instanceof EnderMan || entity instanceof Endermite || entity instanceof Shulker;
	}
	
	public static boolean isAffectedByImpaling(Entity entity) {
		return entity instanceof AbstractFish || entity instanceof Dolphin || entity instanceof Squid || entity instanceof Guardian || entity instanceof Drowned || entity instanceof Turtle;
	}
	
	public static boolean isAffectedByFrost(Entity entity) {
		return entity instanceof MagmaCube || entity instanceof Ghast || entity instanceof Blaze || entity instanceof Strider;
	}
	
	public static int getGrowthLevel(LivingEntity entity, EquipmentSlot slot, ItemStack stack) {
		int growth = 0;
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
				if (equipmentSlot == slot) {
					growth += stack.getEnchantmentLevel(XSEnchantments.GROWTH.get());
				} else {
					growth += entity.getItemBySlot(equipmentSlot).getEnchantmentLevel(XSEnchantments.GROWTH.get());
				}
			}
		}
		return growth;
	}
	
	public static void updateAttributeModifier(Player player, Attribute attribute, Operation operation, UUID uuid, String name, double to, double from) {
		AttributeInstance instance = Objects.requireNonNull(player.getAttribute(attribute));
		AttributeModifier modifier = new AttributeModifier(uuid, XSurvive.MOD_NAME + name, to, operation);
		boolean hasModifier = instance.getModifier(uuid) != null;
		if (to == from && !hasModifier) {
			instance.addTransientModifier(modifier);
		} else if (to != from) {
			if (hasModifier) {
				instance.removeModifier(uuid);
				instance.addTransientModifier(modifier);
			} else {
				instance.addTransientModifier(modifier);
			}
		}
	}
	
	public static void equipEntityForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		double difficulty = instance.getEffectiveDifficulty();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot != EquipmentSlot.OFFHAND && entity.getItemBySlot(slot).isEmpty() && (entity.getRandom().nextDouble() / 2.0) + 0.5 * instance.getSpecialMultiplier() > entity.getRandom().nextDouble()) {
				WeightCollection<List<Item>> itemWeights = slot == EquipmentSlot.MAINHAND ? ItemWeightHelper.getWeaponWeightsForDifficulty(difficulty) : ItemWeightHelper.getArmorWeightsForDifficulty(difficulty);
				if (!itemWeights.isEmpty()) {
					entity.setItemSlot(slot, ItemStackHelper.setupRandomItemForSlot(entity, slot, Lists.newArrayList(itemWeights.next()), instance.getSpecialMultiplier()));
					if (entity instanceof Monster monster) {
						monster.setDropChance(slot, slot.getType() == Type.HAND ? 0.04F : 0.02F);
					}
				}
			}
		}
	}
	
	public static Vec3 clipWithDistance(Player player, Level level, double clipDistance) {
		double vecX = Math.sin(-player.getYRot() * (Math.PI / 180.0) - Math.PI) * -Math.cos(-player.getXRot() * (Math.PI / 180.0));
		double vecY = Math.sin(-player.getXRot() * (Math.PI / 180.0));
		double vecZ = Math.cos(-player.getYRot() * (Math.PI / 180.0) - Math.PI) * -Math.cos(-player.getXRot() * (Math.PI / 180.0));
		return level.clip(new ClipContext(player.getEyePosition(), player.getEyePosition().add(vecX * clipDistance, vecY * clipDistance, vecZ * clipDistance), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)).getLocation();
	}
	
	public static void addAttributeModifier(LivingEntity entity, Attribute attribute, AttributeModifier modifier) {
		AttributeMap attributes = entity.getAttributes();
		if (attributes.hasAttribute(attribute)) {
			AttributeInstance instance = Objects.requireNonNull(attributes.getInstance(attribute));
			if (instance.getModifier(modifier.getId()) == null) {
				instance.addTransientModifier(modifier);
			}
		}
	}
}
