package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.entity.EntityHelper;
import net.luis.xsurvive.world.entity.EntityProvider;
import net.luis.xsurvive.world.entity.ai.goal.*;
import net.luis.xsurvive.world.entity.projectile.IArrow;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.item.enchantment.XSEnchantments;
import net.luis.xsurvive.world.level.LevelProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 *
 * @author Luis-St
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class EntityEventHandler {
	
	private static final UUID MAX_HEALTH_UUID = UUID.fromString("21E6F6F7-4ED8-4DA4-A921-BFFC33BD6E55");
	private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("FF121C82-5FEE-4D7C-9074-A001F24EBE16");
	private static final UUID FOLLOW_RANGE_UUID = UUID.fromString("59CDBB10-9F24-41D2-8CBF-82ACF38D5F6D");
	private static final String BLAZE_ATTACK_GOAL = "net.minecraft.world.entity.monster.Blaze.BlazeAttackGoal";
	private static final String SPIDER_ATTACK_GOAL = "net.minecraft.world.entity.monster.Spider.SpiderAttackGoal";
	private static final String SPIDER_TARGET_GOAL = "net.minecraft.world.entity.monster.Spider.SpiderTargetGoal";
	private static final String ZOMBIFIED_PIGLIN_TARGET_GOAL = "net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal";
	
	@SubscribeEvent
	public static void entityJoinLevel(@NotNull EntityJoinLevelEvent event) {
		if (event.getLevel().isClientSide()) {
			return;
		}
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
		if (entity instanceof Blaze blaze) {
			blaze.goalSelector.removeAllGoals(goal -> BLAZE_ATTACK_GOAL.equals(goal.getClass().getCanonicalName()));
			blaze.goalSelector.addGoal(4, new XSBlazeAttackGoal(blaze));
		}
		if (entity instanceof Zombie zombie && !(entity instanceof ZombifiedPiglin)) {
			zombie.setCanBreakDoors(true);
		}
		if (entity instanceof Spider spider) {
			spider.goalSelector.removeAllGoals(goal -> SPIDER_ATTACK_GOAL.equals(goal.getClass().getCanonicalName()));
			spider.goalSelector.addGoal(4, new XSSpiderAttackGoal(spider));
			spider.targetSelector.removeAllGoals(goal -> SPIDER_TARGET_GOAL.equals(goal.getClass().getCanonicalName()));
			spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(spider, Player.class, true));
			spider.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(spider, IronGolem.class, true));
		}
		if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
			zombifiedPiglin.targetSelector.removeAllGoals(goal -> ZOMBIFIED_PIGLIN_TARGET_GOAL.equals(goal.getClass().getCanonicalName()));
			zombifiedPiglin.targetSelector.addGoal(2, new XSZombifiedPiglinAttackGoal<>(zombifiedPiglin, Player.class, true, false));
		}
		if (entity instanceof AbstractArrow abstractArrow && abstractArrow instanceof IArrow arrow) {
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
	public static void enderPearl(@NotNull EnderPearl event) {
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
	}
}
