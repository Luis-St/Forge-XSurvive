package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.level.block.MysticFireBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public enum EntityFireType {
	
	NONE(), FIRE(), SOUL_FIRE(), MYSTIC_FIRE();
	
	private static final Material FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/fire_0"));
	private static final Material FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/fire_1"));
	private static final Material SOUL_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_0"));
	private static final Material SOUL_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_1"));
	private static final Material MYSTIC_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(XSurvive.MOD_ID, "block/mystic_fire_0"));
	private static final Material MYSTIC_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(XSurvive.MOD_ID, "block/mystic_fire_1"));
	
	public static @NotNull EntityFireType byOrdinal(int ordinal, @NotNull EntityFireType fallback) {
		for (EntityFireType fireType : values()) {
			if (fireType.ordinal() == ordinal) {
				return fireType;
			}
		}
		return Objects.requireNonNull(fallback);
	}
	
	public static EntityFireType byBlock(@NotNull Block fireBlock) {
		if (fireBlock instanceof FireBlock) {
			return FIRE;
		} else if (fireBlock instanceof SoulFireBlock) {
			return SOUL_FIRE;
		} else if (fireBlock instanceof MysticFireBlock) {
			return MYSTIC_FIRE;
		}
		return NONE;
	}
	
	public static @NotNull TextureAtlasSprite getFireTextureSprite0(@NotNull Entity entity, EntityFireType entityFireType) {
		EntityFireType blockFireType = EntityFireType.byBlock(entity.getFeetBlockState().getBlock());
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
	
	public static @NotNull TextureAtlasSprite getFireTextureSprite1(@NotNull Entity entity, EntityFireType entityFireType) {
		EntityFireType blockFireType = EntityFireType.byBlock(entity.getFeetBlockState().getBlock());
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
