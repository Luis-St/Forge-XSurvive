package net.luis.xsurvive.world.entity;

import com.google.common.collect.Lists;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.entity.player.PlayerHelper;
import net.luis.xsurvive.world.item.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Luis-St
 *
 */

public class EntityEquipmentHelper {
	
	public static void equipEntity(@NotNull LivingEntity entity, @NotNull ServerLevel level) {
		if (entity instanceof Zombie zombie && !(entity instanceof ZombifiedPiglin) && !(entity instanceof ZombieVillager)) {
			// EntityEquipmentHelper.equipEntityForDifficulty(zombie, LevelHelper.getCurrentDifficultyAt(zombie.level(), zombie.blockPosition()));
		}
		if (entity instanceof AbstractSkeleton skeleton && !(entity instanceof WitherSkeleton)) {
			// EntityEquipmentHelper.equipEntityForDifficulty(skeleton, LevelHelper.getCurrentDifficultyAt(skeleton.level(), skeleton.blockPosition()));
		}
		if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
			// EntityEquipmentHelper.equipEntityForDifficulty(zombifiedPiglin, LevelHelper.getCurrentDifficultyAt(zombifiedPiglin.level(), zombifiedPiglin.blockPosition()));
		}
		if (entity instanceof Pillager pillager) {
		
		}
		if (entity instanceof Vindicator vindicator) {
		
		}
		if (entity instanceof Vex vex) {
		
		}
	}
	
	public static double getGlobalDifficulty(@NotNull ServerLevel level) {
		int weeks = (int) (level.getGameTime() / (24000 * 7));
		if (0 >= weeks) {
			return 0.0;
		}
		return level.getServer().getPlayerList().getPlayers().stream().mapToDouble(player -> getPlayerDifficulty(level, player)).average().orElse(0.0);
	}
	
	public static double getPlayerDifficulty(@NotNull ServerLevel level, @NotNull ServerPlayer player) {
		int difficulty = level.getDifficulty().getId();
		int days = (int) (level.getGameTime() / 24000);
		int weeks = days / 7;
		if (0 >= weeks) {
			return 0.0;
		}
		int deaths = PlayerHelper.getStat(player, Stats.DEATHS);
		int mobKills = PlayerHelper.getStat(player, Stats.MOB_KILLS);
		boolean pvp = PlayerHelper.getStat(player, Stats.PLAYER_KILLS) > 0;
		double equipment = getPlayerEquipment(player);
		
		double deathsPerWeek = (double) deaths / weeks;
		
		

		
		return 0.0;
	}
	
	public static double getPlayerEquipment(@NotNull ServerPlayer player) {
		double equipment = 0;
		IItemHandler handler = player.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(NullPointerException::new);
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack stack = handler.getStackInSlot(i);
			if (stack.isEmpty()) {
				continue;
			}
			equipment += getItemValue(player, stack);
		}
		return equipment;
	}
	
	private static double getItemValue(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
		double value = 0.0;
		double durability = ItemStackHelper.getDurabilityPercent(stack);
		if (stack.isEmpty()) {
			return 0.0;
		} else if (stack.getItem() instanceof ArmorItem item) {
			value = (item.getDefense() * durability) * (1 + item.getMaterial().getKnockbackResistance());
		} else if (stack.getItem() instanceof SwordItem item) {
			value = (1 + item.getDamage()) * durability;
		} else if (stack.getItem() instanceof DiggerItem item) {
			value = (1 + item.getAttackDamage()) * durability;
		} else if (stack.getItem() instanceof ShieldItem item) {
			value = 75 * durability;
		} else if (stack.getItem() instanceof BowItem item) {
			value = 50 * durability;
		} else if (stack.getItem() instanceof CrossbowItem item) {
			value = 100 * durability;
		} else if (stack.getItem() instanceof TridentItem item) {
			value = 150 * durability;
		} else if (stack.getItem() instanceof PotionItem item) {
			value = PotionUtils.getMobEffects(stack).stream().mapToInt(MobEffectInstance::getAmplifier).sum();
		}
		if (stack.isEnchanted()) {
			value *= 2;
		}
		return value;
	}
	
	/*
	if (entity instanceof Creeper creeper && creeper instanceof ICreeper iCreeper) {
		DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(creeper.level(), creeper.blockPosition());
		iCreeper.setExplosionRadius((int) Math.max(3.0, instance.getEffectiveDifficulty()));
		if (instance.getSpecialMultiplier() >= 1.0 && 0.5 > creeper.getRandom().nextDouble()) {
			iCreeper.setPowered(true);
		}
	}
	if (entity instanceof Vex vex) {
		DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(vex.level(), vex.blockPosition());
		if (instance.getEffectiveDifficulty() > 0.0) {
			vex.setItemInHand(InteractionHand.MAIN_HAND, ItemStackEquipmentHelper.setupItemForSlot(vex, EquipmentSlot.MAINHAND, ItemStackEquipmentHelper.getSwordForDifficulty(vex, instance), instance.getSpecialMultiplier()));
		}
	}
	if (entity instanceof Pillager pillager) {
		DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(pillager.level(), pillager.blockPosition());
		if (instance.getEffectiveDifficulty() > 0.0) {
			pillager.setItemInHand(InteractionHand.MAIN_HAND, ItemStackEquipmentHelper.setupItemForSlot(pillager, EquipmentSlot.MAINHAND, ItemStackEquipmentHelper.getCrossbowForDifficulty(instance), instance.getSpecialMultiplier()));
		}
	}
	if (entity instanceof Vindicator vindicator) {
		DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(vindicator.level(), vindicator.blockPosition());
		if (instance.getEffectiveDifficulty() > 0.0) {
			vindicator.setItemInHand(InteractionHand.MAIN_HAND, ItemStackEquipmentHelper.setupItemForSlot(vindicator, EquipmentSlot.MAINHAND, ItemStackEquipmentHelper.getAxeForDifficulty(vindicator, instance), instance.getSpecialMultiplier()));
		}
	}
	 */
	
	public static void equipEntityForDifficulty(@NotNull LivingEntity entity, @NotNull DifficultyInstance instance) {
		double difficulty = instance.getEffectiveDifficulty();
		if (difficulty > 0.0) {
			for (EquipmentSlot slot : EquipmentSlot.values()) {
				if (slot != EquipmentSlot.OFFHAND && entity.getItemBySlot(slot).isEmpty() && (entity.getRandom().nextDouble() / 2.0) + 0.5 * instance.getSpecialMultiplier() > entity.getRandom().nextDouble()) {
					WeightCollection<List<Item>> itemWeights = slot == EquipmentSlot.MAINHAND ? ItemEquipmentHelper.getWeaponWeightsForDifficulty(difficulty) : ItemEquipmentHelper.getArmorWeightsForDifficulty(difficulty);
					if (!itemWeights.isEmpty()) {
						entity.setItemSlot(slot, ItemStackEquipmentHelper.setupRandomItemForSlot(entity, slot, Lists.newArrayList(itemWeights.next()), instance.getSpecialMultiplier()));
						if (entity instanceof Monster monster) {
							monster.setDropChance(slot, slot.getType() == EquipmentSlot.Type.HAND ? 0.04F : 0.02F);
						}
					}
				}
			}
		}
	}
}
