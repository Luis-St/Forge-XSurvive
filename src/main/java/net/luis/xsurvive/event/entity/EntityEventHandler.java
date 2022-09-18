package net.luis.xsurvive.event.entity;

import java.util.UUID;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.ItemStackHelper;
import net.luis.xsurvive.world.level.LevelHelper;
import net.luis.xsurvive.world.level.entity.EntityHelper;
import net.luis.xsurvive.world.level.entity.ai.goal.XSBlazeAttackGoal;
import net.luis.xsurvive.world.level.entity.ai.goal.XSSpiderAttackGoal;
import net.luis.xsurvive.world.level.entity.ai.goal.XSZombifiedPiglinAttackGoal;
import net.luis.xsurvive.world.level.entity.monster.ICreeper;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.luis.xsurvive.world.level.entity.projectile.IArrow;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 *
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class EntityEventHandler {
	
	public static final UUID MAX_HEALTH_UUID = UUID.fromString("21E6F6F7-4ED8-4DA4-A921-BFFC33BD6E55");
	public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("FF121C82-5FEE-4D7C-9074-A001F24EBE16");
	public static final UUID ARMOR_UUID = UUID.fromString("5C22132F-8448-438A-B060-99B748897CC4");
	public static final UUID FOLLOW_RANGE_UUID = UUID.fromString("59CDBB10-9F24-41D2-8CBF-82ACF38D5F6D");
	
	@SubscribeEvent
	public static void entityJoinLevel(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof LivingEntity entity) {
			if (!(entity instanceof Player)) {
				if (entity instanceof EnderDragon) {
					entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000.0);
				} else if (entity instanceof WitherBoss) {
					entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000.0);
				} else if (entity instanceof ElderGuardian) {
					entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000.0);
				} else if (entity instanceof Warden) {
					entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000.0);
				} else if (entity instanceof Enemy) {
					EntityHelper.addAttributeModifier(entity, Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_UUID, "IncreaseMaxHealthAttribute", 4.0, Operation.MULTIPLY_TOTAL)); // *= 5.0
					EntityHelper.addAttributeModifier(entity, Attributes.FOLLOW_RANGE, new AttributeModifier(FOLLOW_RANGE_UUID, "IncreaseFollowRangeAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
				} else if (entity instanceof AbstractGolem) {
					EntityHelper.addAttributeModifier(entity, Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_UUID, "IncreaseMaxHealthAttribute", 4.0, Operation.MULTIPLY_TOTAL)); // *= 4.0
				} else {
					EntityHelper.addAttributeModifier(entity, Attributes.MAX_HEALTH, new AttributeModifier(MAX_HEALTH_UUID, "IncreaseMaxHealthAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
				}
				EntityHelper.addAttributeModifier(entity, Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_UUID, "IncreaseAttackDamageAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
				EntityHelper.addAttributeModifier(entity, Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "IncreaseArmorAttribute", 1.0, Operation.MULTIPLY_TOTAL)); // *= 2.0
				entity.setHealth(entity.getMaxHealth());
			}
		}
		Entity entity = event.getEntity();
		RandomSource rng = RandomSource.create();
		if (entity instanceof Player player) {
			PlayerProvider.get(player).setChanged();
		} else if (entity instanceof Blaze blaze) {
			blaze.goalSelector.removeAllGoals();
			blaze.goalSelector.addGoal(4, new XSBlazeAttackGoal(blaze));
			blaze.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(blaze, 1.0D));
			blaze.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(blaze, 1.0D, 0.0F));
			blaze.goalSelector.addGoal(8, new LookAtPlayerGoal(blaze, Player.class, 8.0F));
			blaze.goalSelector.addGoal(8, new RandomLookAroundGoal(blaze));
		} else if (entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.DROWNED || entity.getType() == EntityType.HUSK) {
			if (entity instanceof Zombie zombie) {
				DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(zombie.level, zombie.blockPosition());
				if (instance.getEffectiveDifficulty() > 0.0) {
					EntityHelper.equipEntityForDifficulty(zombie, instance);
				}
				zombie.setCanBreakDoors(true);
			}
		} else if (entity instanceof Creeper creeper && creeper instanceof ICreeper iCreeper) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(creeper.level, creeper.blockPosition());
			iCreeper.setExplosionRadius((int) Math.max(3.0, instance.getEffectiveDifficulty()));
			if (instance.getSpecialMultiplier() >= 1.0 &&  0.5 > rng.nextDouble()) {
				iCreeper.setPowered(true);
			}
		} else if (entity instanceof Spider spider) {
			spider.goalSelector.removeAllGoals();
			spider.goalSelector.addGoal(1, new FloatGoal(spider));
			spider.goalSelector.addGoal(3, new LeapAtTargetGoal(spider, 0.4F));
			spider.goalSelector.addGoal(4, new XSSpiderAttackGoal(spider));
			spider.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(spider, 0.8D));
			spider.goalSelector.addGoal(6, new LookAtPlayerGoal(spider, Player.class, 8.0F));
			spider.goalSelector.addGoal(6, new RandomLookAroundGoal(spider));
			spider.targetSelector.removeAllGoals();
			spider.targetSelector.addGoal(1, new HurtByTargetGoal(spider));
			spider.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(spider, Player.class, true));
			spider.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(spider, IronGolem.class, true));
		} else if (entity instanceof Skeleton skeleton) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(skeleton.level, skeleton.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				EntityHelper.equipEntityForDifficulty(skeleton, instance);
			}
		} else if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
			zombifiedPiglin.targetSelector.removeAllGoals();
			zombifiedPiglin.targetSelector.addGoal(1, new HurtByTargetGoal(zombifiedPiglin).setAlertOthers());
			zombifiedPiglin.targetSelector.addGoal(2, new XSZombifiedPiglinAttackGoal<>(zombifiedPiglin, Player.class, true, false));
			zombifiedPiglin.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(zombifiedPiglin, true));
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(zombifiedPiglin.level, zombifiedPiglin.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				EntityHelper.equipEntityForDifficulty(zombifiedPiglin, instance);
			}
		} else if (entity instanceof Vex vex) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(vex.level, vex.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				vex.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForSlot(vex, EquipmentSlot.MAINHAND, ItemStackHelper.getSwordForDifficulty(vex, instance), instance.getSpecialMultiplier()));
			}
		} else if (entity instanceof Pillager pillager) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(pillager.level, pillager.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				pillager.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForSlot(pillager, EquipmentSlot.MAINHAND, ItemStackHelper.getCrossbowForDifficulty(pillager, instance), instance.getSpecialMultiplier()));
			}
		} else if (entity instanceof Vindicator vindicator) {
			DifficultyInstance instance = LevelHelper.getCurrentDifficultyAt(vindicator.level, vindicator.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				vindicator.setItemInHand(InteractionHand.MAIN_HAND, ItemStackHelper.setupItemForSlot(vindicator, EquipmentSlot.MAINHAND, ItemStackHelper.getAxeForDifficulty(vindicator, instance), instance.getSpecialMultiplier()));
			}
		}
	}
	
	@SubscribeEvent
	public static void entityMount(EntityMountEvent event) {
		Entity entity = event.getEntity();
		Entity vehicle = event.getEntityBeingMounted();
		if (event.isMounting()) {
			if (entity instanceof EnderMan enderMan) {
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
	public static void projectileImpact(EnderPearl event) {
		event.setAttackDamage(event.getAttackDamage() * 1.5F);
	}
	
	@SubscribeEvent
	public static void projectileImpact(ProjectileImpactEvent event) {
		if (event.getProjectile() instanceof IArrow arrow) {
			int explosionLevel = arrow.getExplosionLevel();
			if (explosionLevel > 0 && event.getRayTraceResult() instanceof BlockHitResult hitResult) {
				Vec3 location = hitResult.getLocation();
				event.getProjectile().level.explode(event.getProjectile().getOwner(), location.x(), location.y(), location.z(), explosionLevel, Explosion.BlockInteraction.BREAK);
			}
		}
	}
	
}
