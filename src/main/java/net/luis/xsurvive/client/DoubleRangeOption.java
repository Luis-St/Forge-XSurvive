package net.luis.xsurvive.client;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.client.OptionInstance;

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
	public Optional<Double> validateValue(Double value) {
		return this.max >= value && value >= this.min ? Optional.of(value) : Optional.empty();
	}

	@Override
	public Codec<Double> codec() {
		return Codec.DOUBLE;
	}

	@Override
	public double toSliderValue(Double value) {
		return value;
	}

	@Override
	public Double fromSliderValue(double value) {
		return value;
	}

}
