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
	
	public XSSpiderAttackGoal(@NotNull Spider spider) {
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
