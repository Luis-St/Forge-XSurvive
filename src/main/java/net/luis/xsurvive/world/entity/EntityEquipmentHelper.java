package net.luis.xsurvive.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.luis.xsurvive.util.WeightCollection;
import net.luis.xsurvive.world.entity.player.PlayerHelper;
import net.luis.xsurvive.world.item.*;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

/**
 *
 * @author Luis-St
 *
 */

public class EntityEquipmentHelper {
	
	private static final Map<Difficulty, Double> MAX_DIFFICULTY = Util.make(Maps.newHashMap(), map -> {
		map.put(Difficulty.EASY, 5.0);
		map.put(Difficulty.NORMAL, 7.5);
		map.put(Difficulty.HARD, 10.0);
	});
	
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
		int days = (int) (level.getGameTime() / 24000);
		if (7 >= days || level.getDifficulty() == Difficulty.PEACEFUL) {
			return 0.0;
		}
		return level.getServer().getPlayerList().getPlayers().stream().mapToDouble(player -> getPlayerDifficulty(level, player)).average().orElse(0.0);
	}
	
	/*
	 * 0.0 - 10.0
	 * If game time is less than 7 days or the difficulty is peaceful:
	 *  - 0.0
	 *
	 * If the player has played for more than 45 days:
	 *  - Max value for difficulty
	 *
	 * If the player has not died:
	 *  - Max value for difficulty * (days / 45)
	 *
	 * If the mob kill to death ratio is less than 1.0:
	 *  - difficulty * ((days / 45) * 0.25)
	 *
	 *
	 *
	 *
	 */
	public static double getPlayerDifficulty(@NotNull ServerLevel level, @NotNull ServerPlayer player) {
		Difficulty difficulty = level.getDifficulty();
		int id = level.getDifficulty().getId();
		int days = (int) (level.getGameTime() / 24000);
		if (7 >= days || difficulty == Difficulty.PEACEFUL) {
			return 0.0;
		}
		double max = MAX_DIFFICULTY.getOrDefault(difficulty, 10.0);
		
		double playPercentage = days / 45.0;
		if (playPercentage >= 1.0) {
			return max;
		}
		
		int deaths = PlayerHelper.getStat(player, Stats.DEATHS);
		if (0 >= deaths) {
			return max * playPercentage;
		}
		
		int mobKills = PlayerHelper.getStat(player, Stats.MOB_KILLS);
		double mkd = (double) mobKills / deaths;
		if (1.0 > mkd) {
			return max * (playPercentage * 0.25);
		}
		if (mkd > 100.0) {
		
		}
		
		
		
		
		
		
		
	/*	level.getServer().isPvpAllowed()*/
		
		
		
		
		
		

		
		return 0.0;
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
