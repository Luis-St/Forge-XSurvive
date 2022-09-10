package net.luis.xsurvive.world.level.entity;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.dependency.DependencyItems;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.item.ItemHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
					growth += stack.getEnchantmentLevel(XSEnchantments.GROWTH.get());
				} else {
					growth += entity.getItemBySlot(equipmentSlot).getEnchantmentLevel(XSEnchantments.GROWTH.get());
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
	
	public static void equipEntityForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		double difficulty = instance.getEffectiveDifficulty();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot != EquipmentSlot.OFFHAND && entity.getItemBySlot(slot).isEmpty() && (entity.getRandom().nextDouble() / 2.0) + 0.5 * instance.getSpecialMultiplier() > entity.getRandom().nextDouble()) {
				WeightCollection<List<Item>> itemWeights = slot == EquipmentSlot.MAINHAND ? getWeaponWeightsForDifficulty(difficulty) : getArmorWeightsForDifficulty(difficulty);
				if (!itemWeights.isEmpty()) {
					entity.setItemSlot(slot, setupRandomItemForSlot(entity, slot, Lists.newArrayList(itemWeights.next()), instance.getSpecialMultiplier()));
					if (entity instanceof Monster monster) {
						monster.setDropChance(slot, slot.getType() == Type.HAND ? 0.04F : 0.02F);
					}
				}
			}
		}
	}
	
	private static WeightCollection<List<Item>> getArmorWeightsForDifficulty(double difficulty) {
		WeightCollection<List<Item>> itemWeights = new WeightCollection<>();
		if (difficulty >= 5.5) {
			itemWeights.add(1, ItemHelper.getEnderiteArmor());
		}
		if (difficulty >= 4.5) {
			itemWeights.add(10, ItemHelper.getNetheriteArmor());
		}
		if (difficulty >= 3.5) {
			itemWeights.add(40, ItemHelper.getDiamondArmor());
		}
		if (difficulty >= 2.75) {
			itemWeights.add(30, ItemHelper.getIronArmor());
		} 
		if (3.5 >= difficulty && difficulty >= 1.75) {
			itemWeights.add(20, ItemHelper.getChainArmor());
		} 
		if (2.75 >= difficulty && difficulty >= 1.0) {
			itemWeights.add(10, ItemHelper.getGoldArmor());
		}
		if (1.75 >= difficulty) {
			itemWeights.add(5, ItemHelper.getLeatherArmor());
		}
		return itemWeights;
	}
	
	public static WeightCollection<List<Item>> getWeaponWeightsForDifficulty(double difficulty) {
		WeightCollection<List<Item>> itemWeights = new WeightCollection<>();
		if (difficulty >= 5.5) {
			itemWeights.add(1, ItemHelper.getEnderiteWeapons());
		}
		if (difficulty >= 4.5) {
			itemWeights.add(10, ItemHelper.getNetheriteWeapons());
		}
		if (difficulty >= 3.5) {
			itemWeights.add(40, ItemHelper.getDiamondWeapons());
		}
		if (difficulty >= 2.75) {
			itemWeights.add(30, ItemHelper.getIronWeapons());
		} 
		if (3.5 >= difficulty && difficulty >= 1.75) {
			itemWeights.add(20, ItemHelper.getStoneWeapons());
		} 
		if (2.75 >= difficulty && difficulty >= 1.0) {
			itemWeights.add(10, ItemHelper.getGoldWeapons());
		}
		if (1.75 >= difficulty) {
			itemWeights.add(5, ItemHelper.getWoodWeapons());
		}
		return itemWeights;
	}
	
	private static WeightCollection<Item> getCrossbowWeightsForDifficulty(double difficulty) {
		WeightCollection<Item> itemWeights = new WeightCollection<>();
		if (difficulty >= 5.0 && DependencyItems.NIGHT_CROSSBOW.isPresent()) {
			itemWeights.add(10, DependencyItems.NIGHT_CROSSBOW.get());
		}
		if (difficulty >= 2.75 && DependencyItems.ENDERITE_CROSSBOW.isPresent()) {
			itemWeights.add(30, DependencyItems.ENDERITE_CROSSBOW.get());
		} 
		itemWeights.add(60, Items.CROSSBOW);
		return itemWeights;
	}
	
	public static ItemStack setupRandomItemForSlot(LivingEntity entity, EquipmentSlot slot, List<Item> items, double specialMultiplier) {
		RandomSource rng = entity.getRandom();
		ItemStack stack = ItemStack.EMPTY;
		int tries = 0;
		do {
			ItemStack tempStack = setupItemForSlot(entity, slot, new ItemStack(items.get(rng.nextInt(items.size()))), specialMultiplier);
			if (tempStack.canEquip(slot, entity)) {
				stack = tempStack;
				break;
			}
			++tries;
		} while (stack.isEmpty() && 10 > tries);
		return stack;
	}
	
	public static ItemStack setupItemForSlot(LivingEntity entity, EquipmentSlot slot, ItemStack stack, double specialMultiplier) {
		RandomSource rng = entity.getRandom();
		if (stack.canEquip(slot, entity)) {
			enchantItem(rng, stack, (int) (2 + (specialMultiplier * 2.0)), (int) (20 + specialMultiplier * rng.nextInt(18)), false, false);
		}
		return stack;
	}
	
	private static void enchantItem(RandomSource rng, ItemStack stack, int count, int cost, boolean treasure, boolean golden) {
		List<EnchantmentInstance> instances = Lists.newArrayList();
		List<EnchantmentInstance> availableInstances = EnchantmentHelper.getAvailableEnchantmentResults(cost, stack, treasure);
		Consumer<? super EnchantmentInstance> action = (instance) -> {
			if (golden) {
				instances.add(XSEnchantmentHelper.increaseEnchantment(instance, golden));
			} else {
				instances.add(instance);
			}
		};
		if (!availableInstances.isEmpty()) {
			WeightedRandom.getRandomItem(rng, availableInstances).ifPresent(action);
			for (int i = 0; i < count - 1; i++) {
				if (!instances.isEmpty()) {
					EnchantmentHelper.filterCompatibleEnchantments(availableInstances, Util.lastOf(instances));
				}
				if (availableInstances.isEmpty()) {
					break;
				}
				WeightedRandom.getRandomItem(rng, availableInstances).ifPresent(action);
			}
		}
		for (EnchantmentInstance instance : instances) {
			stack.enchant(instance.enchantment, instance.level);
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
			AttributeInstance instance = attributes.getInstance(attribute);
			if (instance.getModifier(modifier.getId()) == null) {
				instance.addTransientModifier(modifier);
			}
		}
	}
	
	public static ItemStack getSwordForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		WeightCollection<List<Item>> itemWeights = getWeaponWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			List<Item> weapons = itemWeights.next();
			weapons.removeAll(ItemHelper.getWoodWeapons());
			weapons.removeAll(ItemHelper.getGoldWeapons());
			weapons.removeAll(ItemHelper.getStoneWeapons());
			weapons.removeIf((item) -> {
				return !(item instanceof SwordItem);
			});
			if (!weapons.isEmpty()) {
				return new ItemStack(weapons.get(entity.getRandom().nextInt(weapons.size())));
			}
		}
		return new ItemStack(Items.IRON_SWORD);
	}
	
	public static ItemStack getCrossbowForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		WeightCollection<Item> itemWeights = getCrossbowWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			return new ItemStack(itemWeights.next());
		}
		return new ItemStack(Items.CROSSBOW);
	}
	
	public static ItemStack getAxeForDifficulty(LivingEntity entity, DifficultyInstance instance) {
		WeightCollection<List<Item>> itemWeights = getWeaponWeightsForDifficulty(instance.getEffectiveDifficulty());
		if (!itemWeights.isEmpty()) {
			List<Item> weapons = itemWeights.next();
			weapons.removeAll(ItemHelper.getWoodWeapons());
			weapons.removeAll(ItemHelper.getGoldWeapons());
			weapons.removeAll(ItemHelper.getStoneWeapons());
			weapons.removeIf((item) -> {
				return !(item instanceof AxeItem);
			});
			if (!weapons.isEmpty()) {
				return new ItemStack(weapons.get(entity.getRandom().nextInt(weapons.size())));
			}
		}
		return new ItemStack(Items.IRON_AXE);
	}
	
}
