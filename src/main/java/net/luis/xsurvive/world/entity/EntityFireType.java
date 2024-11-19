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

package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.world.level.block.MysticFireBlock;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public enum EntityFireType {
	
	NONE(), FIRE(), SOUL_FIRE(), MYSTIC_FIRE();
	
	public static @NotNull EntityFireType byOrdinal(int ordinal, @NotNull EntityFireType fallback) {
		for (EntityFireType fireType : values()) {
			if (fireType.ordinal() == ordinal) {
				return fireType;
			}
		}
		return Objects.requireNonNull(fallback);
	}
	
	public static EntityFireType byBlock(@NotNull Block fireBlock) {
		return switch (fireBlock) {
			case FireBlock block -> FIRE;
			case SoulFireBlock soulFireBlock -> SOUL_FIRE;
			case MysticFireBlock mysticFireBlock -> MYSTIC_FIRE;
			default -> NONE;
		};
	}
}
