package net.luis.xsurvive.util;

import com.mojang.serialization.Codec;

import net.minecraft.util.StringRepresentable;

public enum Rarity implements StringRepresentable {
	
	COMMON("common"),
	UNCOMMON("uncommon"),
	RARE("rare"),
	VERY_RARE("very_rare"),
	TREASURE("treasure");
	
	public static final Codec<Rarity> CODEC = StringRepresentable.fromEnum(Rarity::values);
	
	private final String name;
	
	private Rarity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
