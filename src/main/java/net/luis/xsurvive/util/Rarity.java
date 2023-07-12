package net.luis.xsurvive.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum Rarity implements StringRepresentable {
	
	COMMON("common"), UNCOMMON("uncommon"), RARE("rare"), VERY_RARE("very_rare"), TREASURE("treasure");
	
	public static final Codec<Rarity> CODEC = StringRepresentable.fromEnum(Rarity::values);
	
	private final String name;
	
	Rarity(String name) {
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
