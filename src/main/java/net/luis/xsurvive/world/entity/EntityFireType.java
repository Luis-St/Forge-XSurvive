package net.luis.xsurvive.world.entity;

import net.luis.xsurvive.world.level.block.MysticFireBlock;
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
}
