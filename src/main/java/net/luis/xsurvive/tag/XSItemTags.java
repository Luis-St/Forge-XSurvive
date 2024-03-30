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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSItemTags {
	
	public static final TagKey<Item> OCEAN_MONUMENT_BLOCKS = bind(new ResourceLocation(XSurvive.MOD_ID, "ocean_monument_blocks"));
	public static final TagKey<Item> SUB_INGOTS = bind(new ResourceLocation(XSurvive.MOD_ID, "sub_ingots"));
	
	public static void register() {}
	
	private static @NotNull TagKey<Item> bind(@NotNull ResourceLocation location) {
		return ItemTags.create(location);
	}
}
