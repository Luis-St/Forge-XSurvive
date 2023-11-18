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
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.LevelHelper;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
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
import java.util.UUID;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class EntityEventHandler {
	
	private static final Method CAN_STAY_AT = ObfuscationReflectionHelper.findMethod(Shulker.class, "m_149785_", BlockPos.class, Direction.class);
	public static final UUID MAX_HEALTH_UUID = UUID.fromString("21E6F6F7-4ED8-4DA4-A921-BFFC33BD6E55");
	public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("FF121C82-5FEE-4D7C-9074-A001F24EBE16");
	public static final UUID FOLLOW_RANGE_UUID = UUID.fromString("59CDBB10-9F24-41D2-8CBF-82ACF38D5F6D");
	
	@SubscribeEvent
	public static void entityJoinLevel(@NotNull EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof LivingEntity entity) {
			if (!(entity instanceof Player)) {
				if (entity instanceof EnderDragon || entity instanceof WitherBoss || entity instanceof ElderGuardian || entity instanceof Warden) {
					AttributeInstance instance = entity.getAttribute(Attributes.MAX_HEALTH);
					if (instance != null) {
						instance.setBaseValue(1000.0);
					}
					EntityHelper.addAttributeModifier(entity, Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_UUID, "IncreaseAttackDamageAttribute", 2.0, Operation.MULTIPLY_TOTAL)); // *= 3.0
				} else if (entity instanceof Enemy || entity instanceof AbstractGolem) {
					EntityHelper.addAttributeModifier(entity, Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_UUID, "IncreaseMaxHealthAttribute", 3.0, Operation.MULTIPLY_TOTAL)); // *= 4.0
					EntityHelper.addAttributeModifier(entity, Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_UUID, "IncreaseAttackDamageAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
					EntityHelper.addAttributeModifier(entity, Attributes.FOLLOW_RANGE, new AttributeModifier(FOLLOW_RANGE_UUID, "IncreaseFollowRangeAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
				} else {
					EntityHelper.addAttributeModifier(entity, Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_UUID, "IncreaseMaxHealthAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
				}
				entity.setHealth(entity.getMaxHealth());
			}
		}
		Entity entity = event.getEntity();
		RandomSource rng = RandomSource.create();
		if (entity instanceof Player player) {
			PlayerProvider.get(player).broadcastChanges();
		} else if (entity instanceof Blaze blaze) {
			blaze.goalSelector.removeAllGoals(goal -> true);
			blaze.goalSelector.addGoal(4, new XSBlazeAttackGoal(blaze));
			blaze.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(blaze, 1.0));
			blaze.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(blaze, 1.0, 0.0F));
			blaze.goalSelector.addGoal(8, new LookAtPlayerGoal(blaze, Player.class, 8.0F));
			blaze.goalSelector.addGoal(8, new RandomLookAroundGoal(blaze));
		} else if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.DROWNED || entity.getType() == EntityType.HUSK) {
			if (entity instanceof Zombie zombie) {
				DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(zombie.level(), zombie.blockPosition());
				if (instance.getEffectiveDifficulty() > 0.0) {
					EntityHelper.equipEntityForDifficulty(zombie, instance);
				}
				zombie.setCanBreakDoors(true);
			}
		} else if (entity instanceof Creeper creeper && creeper instanceof ICreeper iCreeper) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(creeper.level(), creeper.blockPosition());
			iCreeper.setExplosionRadius((int) Math.max(3.0, instance.getEffectiveDifficulty()));
			if (instance.getSpecialMultiplier() >= 1.0 && 0.5 > rng.nextDouble()) {
				iCreeper.setPowered(true);
			}
		} else if (entity instanceof Spider spider) {
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
		} else if (entity instanceof AbstractSkeleton skeleton && !(entity instanceof WitherSkeleton)) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(skeleton.level(), skeleton.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				EntityHelper.equipEntityForDifficulty(skeleton, instance);
			}
		} else if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
			zombifiedPiglin.targetSelector.removeAllGoals(goal -> true);
			zombifiedPiglin.targetSelector.addGoal(1, new HurtByTargetGoal(zombifiedPiglin).setAlertOthers());
			zombifiedPiglin.targetSelector.addGoal(2, new XSZombifiedPiglinAttackGoal<>(zombifiedPiglin, Player.class, true, false));
			zombifiedPiglin.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(zombifiedPiglin, true));
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(zombifiedPiglin.level(), zombifiedPiglin.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				EntityHelper.equipEntityForDifficulty(zombifiedPiglin, instance);
			}
		} else if (entity instanceof Vex vex) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(vex.level(), vex.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				vex.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForSlot(vex, EquipmentSlot.MAINHAND, ItemStackHelper.getSwordForDifficulty(vex, instance), instance.getSpecialMultiplier()));
			}
		} else if (entity instanceof Pillager pillager) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(pillager.level(), pillager.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				pillager.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForSlot(pillager, EquipmentSlot.MAINHAND, ItemStackHelper.getCrossbowForDifficulty(instance), instance.getSpecialMultiplier()));
			}
		} else if (entity instanceof Vindicator vindicator) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(vindicator.level(), vindicator.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				vindicator.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForSlot(vindicator, EquipmentSlot.MAINHAND, ItemStackHelper.getAxeForDifficulty(vindicator, instance), instance.getSpecialMultiplier()));
			}
		} else if (entity instanceof AbstractArrow abstractArrow && abstractArrow instanceof IArrow arrow) {
			if (abstractArrow.getOwner() instanceof LivingEntity user && user.getMainHandItem().getItem() instanceof BowItem) {
				int explosion = XSEnchantmentHelper.getEnchantmentLevel(XSEnchantments.EXPLOSION.get(), user);
				if (explosion > 0) {
					arrow.setExplosionLevel(explosion);
					user.getMainHandItem().hurtAndBreak(3, user, (e) -> e.broadcastBreakEvent(user.getUsedItemHand()));
				}
			}
		}
		EntityProvider.get(entity).broadcastChanges();
		if (entity instanceof Player) {
			LevelProvider.get(entity.level()).broadcastChanges();
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
	
	@SubscribeEvent
	public static void projectileImpact(@NotNull ProjectileImpactEvent event) {
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
			Pair<Integer, Integer> pair = XSEnchantmentHelper.getTotalEnchantmentLevel(target, Enchantments.PROJECTILE_PROTECTION);
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
	}
	
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
