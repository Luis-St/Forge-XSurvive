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

package net.luis.xsurvive.client;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.entity.EntityFireType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class EntityFireTypeHelper {
	
	private static final Material FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.withDefaultNamespace("block/fire_0"));
	private static final Material FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.withDefaultNamespace("block/fire_1"));
	private static final Material SOUL_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.withDefaultNamespace("block/soul_fire_0"));
	private static final Material SOUL_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.withDefaultNamespace("block/soul_fire_1"));
	private static final Material MYSTIC_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "block/mystic_fire_0"));
	private static final Material MYSTIC_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(XSurvive.MOD_ID, "block/mystic_fire_1"));
	
	public static @NotNull TextureAtlasSprite getFireTextureSprite0(@NotNull Entity entity, @NotNull EntityFireType entityFireType) {
		EntityFireType blockFireType = EntityFireType.byBlock(entity.getInBlockState().getBlock());
		if (blockFireType == EntityFireType.NONE || blockFireType == entityFireType) {
			return switch (entityFireType) {
				case SOUL_FIRE -> SOUL_FIRE_0.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_0.sprite();
				default -> FIRE_0.sprite();
			};
		} else {
			return switch (blockFireType) {
				case SOUL_FIRE -> SOUL_FIRE_0.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_0.sprite();
				default -> FIRE_0.sprite();
			};
		}
	}
	
	public static @NotNull TextureAtlasSprite getFireTextureSprite1(@NotNull Entity entity, @NotNull EntityFireType entityFireType) {
		EntityFireType blockFireType = EntityFireType.byBlock(entity.getInBlockState().getBlock());
		if (blockFireType == EntityFireType.NONE || blockFireType == entityFireType) {
			return switch (entityFireType) {
				case SOUL_FIRE -> SOUL_FIRE_1.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_1.sprite();
				default -> FIRE_1.sprite();
			};
		} else {
			return switch (blockFireType) {
				case SOUL_FIRE -> SOUL_FIRE_1.sprite();
				case MYSTIC_FIRE -> MYSTIC_FIRE_1.sprite();
				default -> FIRE_1.sprite();
			};
		}
	}
}
