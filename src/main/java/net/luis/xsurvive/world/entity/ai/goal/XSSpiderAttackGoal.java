package net.luis.xsurvive.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 *
 * @author Luis-St
 *
 */

public class XSSpiderAttackGoal extends MeleeAttackGoal {
	
	private static final Method HITBOX = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_293919_");
	private static final Method ATTACK_BOUNDING_BOX = ObfuscationReflectionHelper.findMethod(Mob.class, "m_292684_");
	
	public XSSpiderAttackGoal(Spider spider) {
		super(spider, 1.0, true);
	}
	
	@Override
	public boolean canUse() {
		return super.canUse() && !this.mob.isVehicle();
	}
	
	@Override
	protected boolean canPerformAttack(@NotNull LivingEntity target) {
		if (!this.isTimeToAttack() || !this.mob.getSensing().hasLineOfSight(target)) {
			return false;
		}
		try {
			AABB attackBoundingBox = (AABB) ATTACK_BOUNDING_BOX.invoke(this.mob);
			AABB hitbox = (AABB) HITBOX.invoke(target);
			return attackBoundingBox.inflate(2.0).intersects(hitbox);
		} catch (Exception e) {
			return this.mob.isWithinMeleeAttackRange(target);
		}
	}
}
