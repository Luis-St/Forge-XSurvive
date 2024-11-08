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

package net.luis.xsurvive.world.entity.ai.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class CustomZombifiedPiglinAi extends AbstractCustomAi<ZombifiedPiglin> {
	
	public CustomZombifiedPiglinAi(@NotNull ZombifiedPiglin entity, @NotNull ServerLevel level) {
		super(entity, level);
	}
	
	@Override
	public boolean canUse() {
		return this.entity.getTarget() instanceof Player && this.entity.isAngry();
	}
	
	@Override
	public void tick() {
		if (this.entity.getTarget() instanceof Player player) {
			if (player.isCreative() || player.isSpectator()) {
				this.entity.stopBeingAngry();
			}
		}
	}
}
