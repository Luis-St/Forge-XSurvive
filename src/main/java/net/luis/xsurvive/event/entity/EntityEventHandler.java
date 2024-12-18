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

package net.luis.xsurvive.event.entity;

import com.mojang.datafixers.util.Pair;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.entity.EntityHelper;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.ai.goal.*;
import net.luis.xsurvive.world.entity.monster.ICreeper;
import net.luis.xsurvive.world.entity.player.PlayerProvider;
import net.luis.xsurvive.world.entity.projectile.IArrow;
import net.luis.xsurvive.world.item.ItemStackHelper;
import net.luis.xsurvive.world.item.WeaponType;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.LevelHelper;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class EntityEventHandler {
	
	private static final Method CAN_STAY_AT = ObfuscationReflectionHelper.findMethod(Shulker.class, "canStayAt", BlockPos.class, Direction.class);
	private static final ResourceLocation MAX_HEALTH = ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "increase_max_health");
	private static final ResourceLocation ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "increase_attack_damage");
	private static final ResourceLocation FOLLOW_RANGE = ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "increase_follow_range");
	
	@SubscribeEvent
	public static void entityJoinLevel(@NotNull EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity.level() instanceof ServerLevel serverLevel) {
			RandomSource rng = serverLevel.getRandom();
			Difficulty difficulty = serverLevel.getDifficulty();
			if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
				boolean isHard = difficulty == Difficulty.HARD;
				if (event.getEntity() instanceof LivingEntity living) {
					if (!(living instanceof Player)) {
						if (living instanceof EnderDragon || living instanceof WitherBoss || living instanceof ElderGuardian || living instanceof Warden || living instanceof Breeze || living instanceof Ravager) {
							double maxHealth = 1000.0;
							if (living instanceof Breeze || living instanceof Ravager) {
								maxHealth = 250.0;
							}
							AttributeInstance instance = living.getAttribute(Attributes.MAX_HEALTH);
							if (instance != null) {
								instance.setBaseValue(maxHealth);
							}
							EntityHelper.addAttributeModifier(living, Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE, isHard ? 1.0 : 0.5, Operation.ADD_MULTIPLIED_TOTAL)); // *= isHard ? 2.0 : 1.5
							EntityHelper.addAttributeModifier(living, Attributes.FOLLOW_RANGE, new AttributeModifier(FOLLOW_RANGE, 1.5, Operation.ADD_MULTIPLIED_TOTAL)); // *= 2.5
						} else if (living instanceof Enemy || living instanceof AbstractGolem) {
							EntityHelper.addAttributeModifier(living, Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH, isHard ? 1.5 : 0.5, Operation.ADD_MULTIPLIED_TOTAL)); // *= isHard ? 2.5 : 0.5
							EntityHelper.addAttributeModifier(living, Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE, isHard ? 1.5 : 1.0, Operation.ADD_MULTIPLIED_TOTAL)); // *= isHard ? 2.5 : 2.0
							EntityHelper.addAttributeModifier(living, Attributes.FOLLOW_RANGE, new AttributeModifier(FOLLOW_RANGE, 1.0, Operation.ADD_MULTIPLIED_TOTAL)); // *= 2.0
						}
						living.setHealth(living.getMaxHealth());
					}
				}
				if (entity instanceof Blaze blaze) {
					blaze.goalSelector.removeAllGoals(goal -> true);
					blaze.goalSelector.addGoal(4, new XSBlazeAttackGoal(blaze));
					blaze.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(blaze, 1.0));
					blaze.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(blaze, 1.0, 0.0F));
					blaze.goalSelector.addGoal(8, new LookAtPlayerGoal(blaze, Player.class, 8.0F));
					blaze.goalSelector.addGoal(8, new RandomLookAroundGoal(blaze));
				}
				if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.DROWNED || entity.getType() == EntityType.HUSK) {
					if (entity instanceof Zombie zombie) {
						EntityHelper.equipEntityForDifficulty(serverLevel, WeaponType.SWORD, zombie);
						if (isHard) {
							zombie.setCanBreakDoors(true);
						}
					}
				}
				if (entity instanceof Creeper creeper && creeper instanceof ICreeper iCreeper) {
					iCreeper.setExplosionRadius(isHard ? 3 : 2);
					if (isHard && 0.95 > rng.nextDouble()) {
						iCreeper.setPowered(true);
					}
				}
				if (entity instanceof Spider spider) {
					spider.goalSelector.removeAllGoals(goal -> true);
					spider.goalSelector.addGoal(1, new FloatGoal(spider));
					spider.goalSelector.addGoal(3, new LeapAtTargetGoal(spider, 0.4F));
					spider.goalSelector.addGoal(4, new XSSpiderAttackGoal(spider));
					spider.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(spider, 0.8));
					spider.goalSelector.addGoal(6, new LookAtPlayerGoal(spider, Player.class, 8.0F));
					spider.goalSelector.addGoal(6, new RandomLookAroundGoal(spider));
					spider.targetSelector.removeAllGoals(goal -> true);
					spider.targetSelector.addGoal(1, new HurtByTargetGoal(spider));
					spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(spider, Player.class, true));
					spider.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(spider, IronGolem.class, true));
				}
				if (entity instanceof AbstractSkeleton skeleton && !(entity instanceof WitherSkeleton)) {
					EntityHelper.equipEntityForDifficulty(serverLevel, WeaponType.BOW, skeleton);
				}
				if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
					zombifiedPiglin.targetSelector.removeAllGoals(goal -> true);
					zombifiedPiglin.targetSelector.addGoal(1, new HurtByTargetGoal(zombifiedPiglin).setAlertOthers());
					zombifiedPiglin.targetSelector.addGoal(2, new XSZombifiedPiglinAttackGoal<>(zombifiedPiglin, Player.class, true, false));
					zombifiedPiglin.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(zombifiedPiglin, true));
					EntityHelper.equipEntityForDifficulty(serverLevel, WeaponType.SWORD, zombifiedPiglin);
				}
				if (entity instanceof Vex vex) {
					vex.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForDifficulty(difficulty, vex, ItemStackHelper.getSwordForDifficulty(difficulty, vex)));
				}
				if (entity instanceof Pillager pillager) {
					pillager.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForDifficulty(difficulty, pillager, ItemStackHelper.getCrossbowForDifficulty(difficulty)));
				}
				if (entity instanceof Vindicator vindicator) {
					vindicator.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForDifficulty(difficulty, vindicator, ItemStackHelper.getAxeForDifficulty(difficulty, vindicator)));
				}
			}
			if (entity instanceof AbstractArrow abstractArrow && abstractArrow instanceof IArrow arrow) {
				if (abstractArrow.getOwner() instanceof LivingEntity user && user.getMainHandItem().getItem() instanceof BowItem) {
					int explosion = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.EXPLOSION, user);
					if (explosion > 0) {
						arrow.setExplosionLevel(explosion);
						user.getMainHandItem().hurtAndBreak(3, user, user.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
					}
				}
			}
		}
		EntityProvider.get(entity).broadcastChanges();
		if (entity instanceof Player player) {
			PlayerProvider.get(player).broadcastChanges();
			LevelProvider.get(player.level()).broadcastChanges();
		}
	}
	
	@SubscribeEvent
	public static void entityMount(@NotNull EntityMountEvent event) {
		Entity entity = event.getEntity();
		Entity vehicle = event.getEntityBeingMounted();
		if (event.isMounting()) {
			if (entity instanceof EnderMan || entity instanceof AbstractPiglin || entity instanceof ZombifiedPiglin) {
				if (vehicle instanceof Boat || vehicle instanceof AbstractMinecart) {
					event.setCanceled(true);
				}
			} else if (entity instanceof Raider raider && raider.hasActiveRaid()) {
				if (vehicle instanceof Boat || vehicle instanceof AbstractMinecart) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void enderPearl(@NotNull EntityTeleportEvent.EnderPearl event) {
		event.setAttackDamage(event.getAttackDamage() * 1.5F);
	}
	
	/*@SubscribeEvent
	public static void projectileImpact(@NotNull ProjectileImpactEvent event) { // Event not fired -> replaced with Mixin
		Projectile projectile = event.getProjectile();
		if (projectile instanceof IArrow arrow) {
			int explosionLevel = arrow.getExplosionLevel();
			if (explosionLevel > 0 && event.getRayTraceResult() instanceof BlockHitResult hitResult) {
				Vec3 location = hitResult.getLocation();
				projectile.level().explode(projectile.getOwner(), location.x(), location.y(), location.z(), explosionLevel, Level.ExplosionInteraction.BLOCK);
				projectile.discard();
			}
		}
		if (event.getRayTraceResult() instanceof EntityHitResult result && result.getEntity() instanceof LivingEntity target) {
			Pair<Integer, Integer> pair = XSEnchantmentHelper.getTotalEnchantmentLevel(target, Enchantments.PROJECTILE_PROTECTION.getOrThrow(target));
			int total = pair.getFirst();
			int items = pair.getSecond();
			if (total > 0 && items > 0) {
				double average = (double) total / items;
				if (total >= 20) {
					int piercing = projectile instanceof AbstractArrow arrow ? arrow.getPierceLevel() : 0;
					if (0 >= piercing) {
						event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
					} else if (EntityHelper.isUsingItem(target, stack -> stack.getItem() instanceof ShieldItem)) {
						event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
					}
				} else if (average > 0.0) {
					double skipChance = 1.0 - (0.225 * average);
					if (target.getRandom().nextDouble() > skipChance) {
						event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
					}
				}
			}
		}
	}*/
	
	@SubscribeEvent
	public static void shulkerTeleport(@NotNull EntityTeleportEvent.EnderEntity event) {
		if (event.getEntity() instanceof Shulker shulker) {
			boolean canStayAt = false;
			try {
				canStayAt = (boolean) CAN_STAY_AT.invoke(shulker, shulker.blockPosition(), shulker.getAttachFace());
			} catch (Exception e) {
				XSurvive.LOGGER.error("Failed to invoke Shulker#canStayAt(BlockPos, Direction) report this issue to the mod author", e);
			}
			event.setCanceled(shulker.getTarget() != null && canStayAt);
		}
	}
}
