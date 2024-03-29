package net.luis.xsurvive.util;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 *
 * @author Luis-St
 *
 */

public class Chance {
	
	public static final Codec<Chance> CODEC = Codec.DOUBLE.xmap(Chance::new, (chance) -> chance.chance);
	
	private final Random rng = new Random();
	private final double chance;
	
	private Chance(double chance) {
		this.chance = chance;
	}
	
	public static @NotNull Chance of(double chance) {
		return new Chance(chance);
	}
	
	public void setSeed(long seed) {
		this.rng.setSeed(seed);
	}
	
	public boolean isTrue() {
		return this.chance >= 1.0;
	}
	
	public boolean isFalse() {
		return 0.0 >= this.chance;
	}
	
	public boolean result() {
		if (this.isTrue()) {
			return true;
		} else if (this.isFalse()) {
			return false;
		} else {
			return this.chance > this.rng.nextDouble();
		}
	}
}
