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

package net.luis.xsurvive.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSDamageTypeTags {
	
	public static final TagKey<DamageType> DAMAGE_FROM_ABOVE = bind(new ResourceLocation(XSurvive.MOD_ID, "damage_from_above"));
	public static final TagKey<DamageType> DAMAGE_FROM_FRONT = bind(new ResourceLocation(XSurvive.MOD_ID, "damage_from_front"));
	public static final TagKey<DamageType> DAMAGE_FROM_BELOW = bind(new ResourceLocation(XSurvive.MOD_ID, "damage_from_below"));
	public static final TagKey<DamageType> FULL_BODY_DAMAGE = bind(new ResourceLocation(XSurvive.MOD_ID, "full_body_damage"));
	public static final TagKey<DamageType> HEAD_ONLY_DAMAGE = bind(new ResourceLocation(XSurvive.MOD_ID, "head_only_damage"));
	public static final TagKey<DamageType> FEET_ONLY_DAMAGE = bind(new ResourceLocation(XSurvive.MOD_ID, "feet_only_damage"));
	
	public static void register() {}
	
	private static @NotNull TagKey<DamageType> bind(@NotNull ResourceLocation location) {
		return TagKey.create(Registries.DAMAGE_TYPE, location);
	}
}
