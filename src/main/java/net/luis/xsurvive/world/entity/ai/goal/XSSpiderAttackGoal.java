package net.luis.xsurvive.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Spider;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-st
 *
 */

public class XSSpiderAttackGoal extends MeleeAttackGoal {
	
	public XSSpiderAttackGoal(Spider spider) {
		super(spider, 1.0, true);
	}
	
	@Override
	public boolean canUse() {
		return super.canUse() && !this.mob.isVehicle();
	}
	
	@Override
	protected double getAttackReachSqr(@NotNull LivingEntity entity) {
		return 4.0F + entity.getBbWidth();
	}
}
