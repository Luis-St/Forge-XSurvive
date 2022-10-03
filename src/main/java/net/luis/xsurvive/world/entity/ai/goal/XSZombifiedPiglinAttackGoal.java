package net.luis.xsurvive.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;

/**
 *
 * @author Luis-st
 *
 */

public class XSZombifiedPiglinAttackGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

	public XSZombifiedPiglinAttackGoal(ZombifiedPiglin zombifiedPiglin, Class<T> targetType, boolean mustSee, boolean mustReach) {
		super(zombifiedPiglin, targetType,  mustSee, mustReach);
	}
	
	@Override
	public boolean canUse() {
		return super.canUse() && this.target instanceof Player player && !player.isCreative() && !player.isSpectator();
	}
	
	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse() && this.target instanceof Player player && !player.isCreative() && !player.isSpectator();
	}
	
	@Override
	public void start() {
		super.start();
		if (this.mob instanceof ZombifiedPiglin zombifiedPiglin && this.target instanceof Player player) {
			zombifiedPiglin.setTarget(player);
			zombifiedPiglin.maybeAlertOthers();
		}
	}
	
}
