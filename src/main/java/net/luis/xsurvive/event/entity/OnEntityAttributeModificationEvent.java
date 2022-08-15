package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID, bus = Bus.MOD)
public class OnEntityAttributeModificationEvent {
	
	/* Attribute rules:
	 *  - Healh *= 5
	 *  - Attack damage *= 2
	 *  - Armor *= 2
	 *  - Movement & Fly speed += 0.1
	 *  - Follow range = 128
	 */
	
	@SubscribeEvent
	public static void entityAttributeModification(EntityAttributeModificationEvent event) {
		// EnderDragon
		event.add(EntityType.ENDER_DRAGON, Attributes.MAX_HEALTH, 1000.0);
		// WitherBoss
		event.add(EntityType.WITHER, Attributes.MAX_HEALTH, 1000.0);
		event.add(EntityType.WITHER, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.WITHER, Attributes.ARMOR, 8.0);
		event.add(EntityType.WITHER, Attributes.MOVEMENT_SPEED, 0.7);
		event.add(EntityType.WITHER, Attributes.FLYING_SPEED, 0.7);
		event.add(EntityType.WITHER, Attributes.FOLLOW_RANGE, 128.0);
		// ElderGuardian
		event.add(EntityType.ELDER_GUARDIAN, Attributes.MAX_HEALTH, 1000.0);
		event.add(EntityType.ELDER_GUARDIAN, Attributes.ATTACK_DAMAGE, 16.0);
		event.add(EntityType.ELDER_GUARDIAN, Attributes.MOVEMENT_SPEED, 0.4);
		event.add(EntityType.ELDER_GUARDIAN, Attributes.FOLLOW_RANGE, 128.0);
		// Guardian
		event.add(EntityType.GUARDIAN, Attributes.MAX_HEALTH, 150.0);
		event.add(EntityType.GUARDIAN, Attributes.ATTACK_DAMAGE, 12.0);
		event.add(EntityType.GUARDIAN, Attributes.MOVEMENT_SPEED, 0.6);
		event.add(EntityType.GUARDIAN, Attributes.FOLLOW_RANGE, 128.0);
		// Blaze
		event.add(EntityType.BLAZE, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.BLAZE, Attributes.ATTACK_DAMAGE, 12.0);
		event.add(EntityType.BLAZE, Attributes.MOVEMENT_SPEED, 0.33);
		event.add(EntityType.BLAZE, Attributes.FOLLOW_RANGE, 128.0);
		// Drowned
		event.add(EntityType.DROWNED, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.DROWNED, Attributes.ATTACK_DAMAGE, 6.0);
		event.add(EntityType.DROWNED, Attributes.MOVEMENT_SPEED, 0.33);
		event.add(EntityType.DROWNED, Attributes.FOLLOW_RANGE, 128.0);
		event.add(EntityType.DROWNED, Attributes.ARMOR, 4.0);
		// Creeper
		event.add(EntityType.CREEPER, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.CREEPER, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.CREEPER, Attributes.MOVEMENT_SPEED, 0.35);
		event.add(EntityType.CREEPER, Attributes.FOLLOW_RANGE, 128.0);
		// CaveSpider
		event.add(EntityType.CAVE_SPIDER, Attributes.MAX_HEALTH, 60.0);
		event.add(EntityType.CAVE_SPIDER, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.CAVE_SPIDER, Attributes.MOVEMENT_SPEED, 0.4);
		event.add(EntityType.CAVE_SPIDER, Attributes.FOLLOW_RANGE, 128.0);
		// EnderMan
		event.add(EntityType.ENDERMAN, Attributes.MAX_HEALTH, 200.0);
		event.add(EntityType.ENDERMAN, Attributes.ATTACK_DAMAGE, 14.0);
		event.add(EntityType.ENDERMAN, Attributes.MOVEMENT_SPEED, 0.4);
		event.add(EntityType.ENDERMAN, Attributes.FOLLOW_RANGE, 128.0);
		// Evoker
		event.add(EntityType.EVOKER, Attributes.MAX_HEALTH, 120.0);
		event.add(EntityType.EVOKER, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.EVOKER, Attributes.MOVEMENT_SPEED, 0.6);
		event.add(EntityType.EVOKER, Attributes.FOLLOW_RANGE, 128.0);
		// Ghast
		event.add(EntityType.GHAST, Attributes.MAX_HEALTH, 50.0);
		event.add(EntityType.GHAST, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.GHAST, Attributes.MOVEMENT_SPEED, 0.8);
		event.add(EntityType.GHAST, Attributes.FOLLOW_RANGE, 128.0);
		// Drowned
		event.add(EntityType.HUSK, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.HUSK, Attributes.ATTACK_DAMAGE, 6.0);
		event.add(EntityType.HUSK, Attributes.MOVEMENT_SPEED, 0.33);
		event.add(EntityType.HUSK, Attributes.FOLLOW_RANGE, 128.0);
		event.add(EntityType.HUSK, Attributes.ARMOR, 4.0);
		// MagmaCube
		event.add(EntityType.MAGMA_CUBE, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.MAGMA_CUBE, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.MAGMA_CUBE, Attributes.MOVEMENT_SPEED, 0.3);
		event.add(EntityType.MAGMA_CUBE, Attributes.FOLLOW_RANGE, 128.0);
		// Phantom
		event.add(EntityType.PHANTOM, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.PHANTOM, Attributes.ATTACK_DAMAGE, 8.0);
		event.add(EntityType.PHANTOM, Attributes.MOVEMENT_SPEED, 0.8);
		event.add(EntityType.PHANTOM, Attributes.FOLLOW_RANGE, 128.0);
		// Piglin
		event.add(EntityType.PIGLIN, Attributes.MAX_HEALTH, 80.0);
		event.add(EntityType.PIGLIN, Attributes.ATTACK_DAMAGE, 7.0);
		event.add(EntityType.PIGLIN, Attributes.MOVEMENT_SPEED, 0.45);
		event.add(EntityType.PIGLIN, Attributes.FOLLOW_RANGE, 128.0);
		// PiglinBrute
		event.add(EntityType.PIGLIN_BRUTE, Attributes.MAX_HEALTH, 250.0);
		event.add(EntityType.PIGLIN_BRUTE, Attributes.ATTACK_DAMAGE, 9.0);
		event.add(EntityType.PIGLIN_BRUTE, Attributes.MOVEMENT_SPEED, 0.45);
		event.add(EntityType.PIGLIN_BRUTE, Attributes.FOLLOW_RANGE, 128.0);
		// Pillager
		event.add(EntityType.PILLAGER, Attributes.MAX_HEALTH, 120.0);
		event.add(EntityType.PILLAGER, Attributes.ATTACK_DAMAGE, 7.0);
		event.add(EntityType.PILLAGER, Attributes.MOVEMENT_SPEED, 0.45);
		event.add(EntityType.PILLAGER, Attributes.FOLLOW_RANGE, 128.0);
		// Ravager
		event.add(EntityType.RAVAGER, Attributes.MAX_HEALTH, 500.0);
		event.add(EntityType.RAVAGER, Attributes.ATTACK_DAMAGE, 14.0);
		event.add(EntityType.RAVAGER, Attributes.MOVEMENT_SPEED, 0.4);
		event.add(EntityType.RAVAGER, Attributes.FOLLOW_RANGE, 128.0);
		// Vindicator
		event.add(EntityType.VINDICATOR, Attributes.MAX_HEALTH, 120.0);
		event.add(EntityType.VINDICATOR, Attributes.ATTACK_DAMAGE, 7.0);
		event.add(EntityType.VINDICATOR, Attributes.MOVEMENT_SPEED, 0.45);
		event.add(EntityType.VINDICATOR, Attributes.FOLLOW_RANGE, 128.0);
		// Vex
		event.add(EntityType.VEX, Attributes.MAX_HEALTH, 70.0);
		event.add(EntityType.VEX, Attributes.ATTACK_DAMAGE, 6.0);
		event.add(EntityType.VEX, Attributes.MOVEMENT_SPEED, 0.8);
		event.add(EntityType.VEX, Attributes.FOLLOW_RANGE, 128.0);
		// Spider
		event.add(EntityType.SPIDER, Attributes.MAX_HEALTH, 80.0);
		event.add(EntityType.SPIDER, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.SPIDER, Attributes.MOVEMENT_SPEED, 0.4);
		event.add(EntityType.SPIDER, Attributes.FOLLOW_RANGE, 128.0);
		// Skeleton
		event.add(EntityType.SKELETON, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.SKELETON, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.SKELETON, Attributes.MOVEMENT_SPEED, 0.35);
		event.add(EntityType.SKELETON, Attributes.FOLLOW_RANGE, 128.0);
		// Stray
		event.add(EntityType.STRAY, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.STRAY, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.STRAY, Attributes.MOVEMENT_SPEED, 0.35);
		event.add(EntityType.STRAY, Attributes.FOLLOW_RANGE, 128.0);
		// WitherSkeleton
		event.add(EntityType.WITHER_SKELETON, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.WITHER_SKELETON, Attributes.ATTACK_DAMAGE, 6.0);
		event.add(EntityType.WITHER_SKELETON, Attributes.MOVEMENT_SPEED, 0.35);
		event.add(EntityType.WITHER_SKELETON, Attributes.FOLLOW_RANGE, 128.0);
		// Shulker
		event.add(EntityType.SHULKER, Attributes.MAX_HEALTH, 150.0);
		event.add(EntityType.SHULKER, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.SHULKER, Attributes.FOLLOW_RANGE, 128.0);
		// Silverfish
		event.add(EntityType.SILVERFISH, Attributes.MAX_HEALTH, 40.0);
		event.add(EntityType.SILVERFISH, Attributes.ATTACK_DAMAGE, 3.0);
		event.add(EntityType.SILVERFISH, Attributes.MOVEMENT_SPEED, 0.35);
		event.add(EntityType.SILVERFISH, Attributes.FOLLOW_RANGE, 128.0);
		// Witch
		event.add(EntityType.WITCH, Attributes.MAX_HEALTH, 130.0);
		event.add(EntityType.WITCH, Attributes.ATTACK_DAMAGE, 4.0);
		event.add(EntityType.WITCH, Attributes.MOVEMENT_SPEED, 0.35);
		event.add(EntityType.WITCH, Attributes.FOLLOW_RANGE, 128.0);
		// ZombifiedPiglin
		event.add(EntityType.ZOMBIFIED_PIGLIN, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.ZOMBIFIED_PIGLIN, Attributes.ATTACK_DAMAGE, 7.0);
		event.add(EntityType.ZOMBIFIED_PIGLIN, Attributes.MOVEMENT_SPEED, 0.33);
		event.add(EntityType.ZOMBIFIED_PIGLIN, Attributes.FOLLOW_RANGE, 128.0);
		event.add(EntityType.ZOMBIFIED_PIGLIN, Attributes.ARMOR, 4.0);
		// Zombie
		event.add(EntityType.ZOMBIE, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.ZOMBIE, Attributes.ATTACK_DAMAGE, 6.0);
		event.add(EntityType.ZOMBIE, Attributes.MOVEMENT_SPEED, 0.33);
		event.add(EntityType.ZOMBIE, Attributes.FOLLOW_RANGE, 128.0);
		event.add(EntityType.ZOMBIE, Attributes.ARMOR, 4.0);
	}
	
}
