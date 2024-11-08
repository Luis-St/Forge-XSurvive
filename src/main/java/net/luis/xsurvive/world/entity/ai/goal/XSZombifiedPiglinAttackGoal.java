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

package net.luis.xsurvive.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSZombifiedPiglinAttackGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
	
	public XSZombifiedPiglinAttackGoal(@NotNull ZombifiedPiglin zombifiedPiglin, @NotNull Class<T> targetType, boolean mustSee, boolean mustReach) {
		super(zombifiedPiglin, targetType, mustSee, mustReach);
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
