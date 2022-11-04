package net.luis.xsurvive.world.entity;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	
}
