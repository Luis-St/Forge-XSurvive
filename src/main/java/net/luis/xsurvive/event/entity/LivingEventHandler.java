package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.damagesource.XSDamageTypes;
import net.luis.xsurvive.world.effect.XSMobEffects;
import net.luis.xsurvive.world.entity.EntityHelper;
import net.luis.xsurvive.world.entity.ILivingEntity;
import net.luis.xsurvive.world.entity.ai.custom.CustomAi;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class LivingEventHandler {
	
	private static final Random RNG = new Random();
	public static final UUID GRAVITY_MODIFIER_UUID = UUID.fromString("715AF01B-DED3-45ED-8812-C8878C7F98CC");
	public static final UUID HEALTH_MODIFIER_UUID = UUID.fromString("CD14D16D-82ED-474E-97E9-403DE2439D01");
	public static final UUID ATTACK_RANGE_UUID = UUID.fromString("95C48A2D-C536-4FB4-88DF-0DC534CE012A");
	public static final UUID REACH_DISTANCE_UUID = UUID.fromString("F4502088-D181-44F4-A881-CDE977976A6D");
	
	@SubscribeEvent
	public static void livingAttack(@NotNull LivingAttackEvent event) {
		Entity target = event.getEntity();
		DamageSource source = event.getSource();
		float amount = event.getAmount();
		if (source.getEntity() instanceof Player player) {
			if (target instanceof LivingEntity livingTarget && amount > 0.0F) {
				int harmingCurse = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.CURSE_OF_HARMING.get(), player);
				int thunderbolt = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.THUNDERBOLT.get(), player);
				if (harmingCurse > 0) {
					float damage = (amount / 2.0F) * harmingCurse;
					if (player.hurt(new DamageSource(player.level().registryAccess().registry(Registries.DAMAGE_TYPE).orElseThrow().getHolderOrThrow(XSDamageTypes.CURSE_OF_HARMING), livingTarget), damage)) {
						event.setCanceled(true);
					}
				}
				if (thunderbolt > 0 && player.level() instanceof ServerLevel serverLevel) {
					LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
					lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
					serverLevel.addFreshEntity(lightningBolt);
					if (RNG.nextInt(5) == 0) {
						serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + RNG.nextFloat() * 0.2F);
					}
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 4, false, false), lightningBolt);
				}
			}
		}
		if (target instanceof Player player && source.is(DamageTypes.FELL_OUT_OF_WORLD) && amount > 0) {
			int voidProtection = player.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(XSEnchantments.VOID_PROTECTION.get());
			if (voidProtection > 0) {
				float percent = switch (voidProtection) {
					case 1 -> 1.0F;
					case 2 -> 0.75F;
					case 3 -> 0.5F;
					case 4 -> 0.25F;
					default -> 0.0F;
				};
				event.setCanceled(RNG.nextDouble() > percent);
			}
		}
	}
	
	@SubscribeEvent
	public static void livingDamage(@NotNull LivingDamageEvent event) {
		Entity target = event.getEntity();
		DamageSource source = event.getSource();
		float newAmount = event.getAmount();
		if (source.getEntity() instanceof Player player) {
			int enderSlayer = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.ENDER_SLAYER.get(), player);
			int impaling = XSEnchantmentHelper.getEnchantmentLevel(Enchantments.IMPALING, player);
			if (enderSlayer > 0 && EntityHelper.isAffectedByEnderSlayer(target)) {
				newAmount *= 2.5F;
			}
			if (impaling > 0 && EntityHelper.isAffectedByImpaling(target)) {
				newAmount *= 2.5F;
			}
		}
		if (target instanceof Player player && source.is(DamageTypes.FELL_OUT_OF_WORLD) && newAmount > 0) {
			int voidProtection = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.VOID_PROTECTION.get(), player);
			if (voidProtection > 0) {
				float percent = switch (voidProtection) {
					case 1 -> 0.8F;
					case 2 -> 0.6F;
					case 3 -> 0.4F;
					case 4 -> 0.2F;
					default -> 0.0F;
				};
				newAmount *= percent;
			}
		}
		event.setAmount(newAmount);
	}
	
	@SubscribeEvent
	public static void livingEquipmentChange(@NotNull LivingEquipmentChangeEvent event) {
		if (event.getEntity() instanceof Player player) {
			ItemStack toStack = event.getTo();
			ItemStack fromStack = event.getFrom();
			if (event.getSlot() == EquipmentSlot.FEET) {
				int voidWalkerTo = toStack.getEnchantmentLevel(XSEnchantments.VOID_WALKER.get());
				int voidWalkerFrom = fromStack.getEnchantmentLevel(XSEnchantments.VOID_WALKER.get());
				EntityHelper.updateAttributeModifier(player, ForgeMod.ENTITY_GRAVITY.get(), Operation.MULTIPLY_TOTAL, GRAVITY_MODIFIER_UUID, "EntityGravity", voidWalkerTo, voidWalkerFrom, 1.0);
			}
			int growthTo = EntityHelper.getGrowthLevel(player, event.getSlot(), toStack);
			int growthFrom = EntityHelper.getGrowthLevel(player, event.getSlot(), fromStack);
			EntityHelper.updateAttributeModifier(player, Attributes.MAX_HEALTH, Operation.ADDITION, HEALTH_MODIFIER_UUID, "MaxHealth", growthTo, growthFrom, 1.0);
			player.setHealth(Math.min(player.getHealth(), (float) Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).getValue()));
			int reachingTo = toStack.getEnchantmentLevel(XSEnchantments.REACHING.get());
			int reachingFrom = fromStack.getEnchantmentLevel(XSEnchantments.REACHING.get());
			EntityHelper.updateAttributeModifier(player, ForgeMod.ENTITY_REACH.get(), Operation.ADDITION, ATTACK_RANGE_UUID, "AttackRange", reachingTo, reachingFrom, 0.5);
			EntityHelper.updateAttributeModifier(player, ForgeMod.BLOCK_REACH.get(), Operation.ADDITION, REACH_DISTANCE_UUID, "ReachDistance", reachingTo, reachingFrom, 0.5);
		}
	}
	
	@SubscribeEvent
	public static void livingExperienceDrop(@NotNull LivingExperienceDropEvent event) {
		Player player = event.getAttackingPlayer();
		int xp = event.getOriginalExperience();
		if (player != null) {
			int experience = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.EXPERIENCE.get(), player);
			int looting = XSEnchantmentHelper.getEnchantmentLevel(Enchantments.MOB_LOOTING, player);
			if (xp > 0 && experience > 0) {
				event.setDroppedExperience(xp * ((experience + 1) * ((experience * 2) + looting)));
			}
		}
	}
	
	@SubscribeEvent
	public static void livingHurt(@NotNull LivingHurtEvent event) {
		LivingEntity livingTarget = event.getEntity();
		if (event.getSource().getEntity() instanceof LivingEntity livingAttacker) {
			int poisonAspect = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.POISON_ASPECT.get(), livingAttacker);
			int frostAspect = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.FROST_ASPECT.get(), livingAttacker);
			if (poisonAspect > 0) {
				livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100 * poisonAspect), livingAttacker);
			}
			if (frostAspect > 0) {
				livingTarget.addEffect(new MobEffectInstance(XSMobEffects.FROST.get(), 100 * frostAspect), livingAttacker);
			}
		}
	}
	
	@SubscribeEvent
	public static void livingTick(LivingEvent.@NotNull LivingTickEvent event) {
		if (event.getEntity() instanceof ILivingEntity livingEntity) {
			if (livingEntity.hasCustomAi()) {
				CustomAi customAi = Objects.requireNonNull(livingEntity.getCustomAi());
				if (customAi.canUse()) {
					customAi.tick();
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void mobEffectAdded(MobEffectEvent.@NotNull Added event) {
		MobEffectInstance instance = event.getEffectInstance();
		if (event.getEntity() instanceof ServerPlayer player && instance.getEffect() == XSMobEffects.FROST.get()) {
			PlayerProvider.getServer(player).setFrostTime(instance.getDuration());
		}
	}
	
	@SubscribeEvent
	public static void shieldBlock(@NotNull ShieldBlockEvent event) {
		if (event.getEntity() instanceof Player && event.getDamageSource().isIndirect()) {
			Entity attacker = event.getDamageSource().getEntity();
			if (attacker instanceof Blaze || attacker instanceof Ghast) {
				event.setCanceled(true);
			}
		}
	}
}