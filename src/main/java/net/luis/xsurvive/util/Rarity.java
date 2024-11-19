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

package net.luis.xsurvive.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum Rarity implements StringRepresentable {
	
	COMMON("common"), UNCOMMON("uncommon"), RARE("rare"), VERY_RARE("very_rare"), TREASURE("treasure");
	
	public static final Codec<Rarity> CODEC = StringRepresentable.fromEnum(Rarity::values);
	
	private final String name;
	
	Rarity(@NotNull String name) {
		this.name = name;
	}
	
	public @NotNull String getName() {
		return this.name;
	}
	
	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
