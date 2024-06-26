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

package net.luis.xsurvive.world.effect;

import net.luis.xsurvive.world.entity.EntityHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class FrostMobEffect extends MobEffect {
	
	public FrostMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
		if (EntityHelper.isAffectedByFrost(entity)) {
			entity.hurt(entity.damageSources().freeze(), (amplifier + 1) * 2.0F);
		}
	}
	
	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % 30 == 0;
	}
}
