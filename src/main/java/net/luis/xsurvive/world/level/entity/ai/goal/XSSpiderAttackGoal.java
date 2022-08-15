package net.luis.xsurvive.world.level.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Spider;

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
	protected double getAttackReachSqr(LivingEntity p_33825_) {
		return (double) (4.0F + p_33825_.getBbWidth());
	}
	
}
