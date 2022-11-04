package net.luis.xsurvive.world.entity;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.luis.xsurvive.world.level.block.MysticFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoulFireBlock;

/**
 *
 * @author Luis-st
 *
 */

public enum EntityFireType {
	
	NONE(),
	FIRE(),
	SOUL_FIRE(),
	MYSTIC_FIRE();
	
	@Nullable
	public static EntityFireType byOrdinal(int ordinal) {
		return byOrdinal(ordinal, null);
	}
	
	@NotNull
	public static EntityFireType byOrdinal(int ordinal, EntityFireType fallback) {
		for (EntityFireType fireType : values()) {
			if (fireType.ordinal() == ordinal) {
				return fireType;
			}
		}
		return Objects.requireNonNull(fallback);
	}
	
	public static EntityFireType byBlock(Block fireBlock) {
		if (fireBlock instanceof FireBlock) {
			return FIRE;
		} else if (fireBlock instanceof SoulFireBlock) {
			return SOUL_FIRE;
		} else if (fireBlock instanceof MysticFireBlock) {
			return MYSTIC_FIRE;
		}
		return NONE;
	}
	
}
