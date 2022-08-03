package net.luis.xsurvive.world.level.entity;

import java.util.UUID;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.enchantment.XSurviveEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EntityHelper {
	
	public static boolean isAffectedByEnderSlayer(Entity entity) {
		return entity instanceof EnderMan || entity instanceof Endermite || entity instanceof Shulker || entity instanceof EnderDragon;
	}
	
	public static boolean isAffectedByImpaling(Entity entity) {
		return entity instanceof AbstractFish || entity instanceof Dolphin || entity instanceof Squid || entity instanceof Guardian || entity instanceof ElderGuardian || entity instanceof Drowned || entity instanceof Turtle;
	}
	
	public static boolean isAffectedByFrost(Entity entity) {
		return entity instanceof MagmaCube || entity instanceof Ghast || entity instanceof Blaze || entity instanceof Strider;
	}
	
	public static int getGrowthLevel(LivingEntity entity, EquipmentSlot slot, ItemStack stack) {
		int growth = 0;
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR) {
				if (equipmentSlot == slot) {
					growth += stack.getEnchantmentLevel(XSurviveEnchantments.GROWTH.get());
				} else {
					growth += entity.getItemBySlot(equipmentSlot).getEnchantmentLevel(XSurviveEnchantments.GROWTH.get());
				}
			}
		}
		return growth;
	}
	
	public static void updateAttributeModifier(Player player, Attribute attribute, Operation operation, UUID uuid, String name, double to, double from) {
		AttributeInstance instance = player.getAttribute(attribute);
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
	
}
