package net.luis.xsurvive.event.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.entity.EntityHelper;
import net.luis.xsurvive.world.level.entity.ai.goal.XSBlazeAttackGoal;
import net.luis.xsurvive.world.level.entity.ai.goal.XSSpiderAttackGoal;
import net.luis.xsurvive.world.level.entity.ai.goal.XSZombifiedPiglinAttackGoal;
import net.luis.xsurvive.world.level.entity.monster.ICreeper;
import net.luis.xsurvive.world.level.entity.player.PlayerProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * 
 * @author Luis-st
 *
 */

@EventBusSubscriber(modid = XSurvive.MOD_ID)
public class OnEntityJoinWorldEvent {
	
	@SubscribeEvent
	public static void entityJoinWorld(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		XSurvive.LOGGER.debug("Entity joined world: {}", entity.getType());
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
				DifficultyInstance instance = zombie.level.getCurrentDifficultyAt(zombie.blockPosition());
				if (instance.getEffectiveDifficulty() > 0.0) {
					EntityHelper.equipEntityForDifficulty(zombie, instance);
				}
				zombie.setCanBreakDoors(true);
			}
		} else if (entity instanceof ICreeper creeper) {
			DifficultyInstance instance = entity.level.getCurrentDifficultyAt(entity.blockPosition());
			creeper.setExplosionRadius((int) Math.max(3.0, instance.getEffectiveDifficulty()));
			if (instance.getSpecialMultiplier() >= 1.0 &&  0.5 > rng.nextDouble()) {
				creeper.setPowered(true);
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
			DifficultyInstance instance = skeleton.level.getCurrentDifficultyAt(skeleton.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				EntityHelper.equipEntityForDifficulty(skeleton, instance);
			}
		} else if (entity instanceof ZombifiedPiglin zombifiedPiglin) {
			zombifiedPiglin.targetSelector.removeAllGoals();
			zombifiedPiglin.targetSelector.addGoal(1, new HurtByTargetGoal(zombifiedPiglin).setAlertOthers());
			zombifiedPiglin.targetSelector.addGoal(2, new XSZombifiedPiglinAttackGoal<>(zombifiedPiglin, Player.class, true, false));
			zombifiedPiglin.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(zombifiedPiglin, true));
			DifficultyInstance instance = zombifiedPiglin.level.getCurrentDifficultyAt(zombifiedPiglin.blockPosition());
			if (instance.getEffectiveDifficulty() > 0.0) {
				EntityHelper.equipEntityForDifficulty(zombifiedPiglin, instance);
			}
		}
	}
	
}
