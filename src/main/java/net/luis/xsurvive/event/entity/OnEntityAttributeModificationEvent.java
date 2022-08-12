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
	
	@SubscribeEvent
	public static void entityAttributeModification(EntityAttributeModificationEvent event) {
		// Ender Dragon
		event.add(EntityType.ENDER_DRAGON, Attributes.MAX_HEALTH, 1000.0);
		// Wither
		event.add(EntityType.WITHER, Attributes.MAX_HEALTH, 1000.0);
		event.add(EntityType.WITHER, Attributes.MOVEMENT_SPEED, 0.75);
		event.add(EntityType.WITHER, Attributes.FLYING_SPEED, 0.75);
		event.add(EntityType.WITHER, Attributes.FOLLOW_RANGE, 128.0);
		event.add(EntityType.WITHER, Attributes.ARMOR, 12.0);
		// Elder Guardian
		event.add(EntityType.ELDER_GUARDIAN, Attributes.MAX_HEALTH, 1000.0);
		event.add(EntityType.ELDER_GUARDIAN, Attributes.MOVEMENT_SPEED, 0.45);
		event.add(EntityType.ELDER_GUARDIAN, Attributes.ATTACK_DAMAGE, 12.0);
		// Guardian
		event.add(EntityType.GUARDIAN, Attributes.MAX_HEALTH, 150.0);
		event.add(EntityType.GUARDIAN, Attributes.ATTACK_DAMAGE, 8.0);
		event.add(EntityType.GUARDIAN, Attributes.MOVEMENT_SPEED, 0.65);
		event.add(EntityType.GUARDIAN, Attributes.FOLLOW_RANGE, 128.0);
		// Blaze
		event.add(EntityType.BLAZE, Attributes.MAX_HEALTH, 100.0);
		event.add(EntityType.BLAZE, Attributes.ATTACK_DAMAGE, 8.0);
		event.add(EntityType.BLAZE, Attributes.MOVEMENT_SPEED, 0.4);
		event.add(EntityType.BLAZE, Attributes.FOLLOW_RANGE, 128.0);
		
		
	}
	
}
