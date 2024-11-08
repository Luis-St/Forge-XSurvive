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

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class DoubleRangeOption implements OptionInstance.SliderableValueSet<Double> {
	
	private final double min;
	private final double max;
	private final Function<Double, Double> toSliderValue;
	private final Function<Double, Double> fromSliderValue;
	
	private DoubleRangeOption(double min, double max, @NotNull Function<Double, Double> toSliderValue, @NotNull Function<Double, Double> fromSliderValue) {
		this.min = min;
		this.max = max;
		this.toSliderValue = toSliderValue;
		this.fromSliderValue = fromSliderValue;
	}
	
	public static @NotNull DoubleRangeOption forGamma(double min, double max) {
		return new DoubleRangeOption(min, max, (value) -> {
			return Mth.clamp(value, 0.0, 1.0);
		}, (value) -> {
			return 1.0 > value ? value : 20.0;
		});
	}
	
	public static @NotNull DoubleRangeOption forGlint(double min, double max) {
		return new DoubleRangeOption(min, max, (value) -> {
			return Mth.clamp(value / 2, 0.0, 1.0);
		}, (value) -> {
			return value * 2;
		});
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
		return this.toSliderValue.apply(value);
	}
	
	@Override
	public @NotNull Double fromSliderValue(double value) {
		return this.fromSliderValue.apply(value);
	}
}
