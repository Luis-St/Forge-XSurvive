package net.luis.xsurvive.client;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 *
 * @author Luis-st
 *
 */

public class DoubleRangeOption implements OptionInstance.SliderableValueSet<Double> {
	
	private final double min;
	private final double max;
	
	public DoubleRangeOption(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public @NotNull Optional<Double> validateValue(@NotNull Double value) {
		return this.max >= value && value >= this.min ? Optional.of(value) : Optional.empty();
	}
	
	@Override
	public @NotNull Codec<Double> codec() {
		return Codec.DOUBLE;
	}
	
	@Override
	public double toSliderValue(@NotNull Double value) {
		return Mth.clamp(value, 0.0, 1.0);
	}
	
	@Override
	public @NotNull Double fromSliderValue(double value) {
		return 1.0 > value ? value : 20.0;
	}
	
}
