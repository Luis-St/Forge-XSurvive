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

package net.luis.xsurvive.util;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class RarityList<T> {
	
	private final Rarity rarity;
	private final List<T> values;
	
	public RarityList(@NotNull Rarity rarity) {
		this(rarity, Lists.newArrayList());
	}
	
	@SafeVarargs
	public RarityList(@NotNull Rarity rarity, T @NotNull ... values) {
		this(rarity, Lists.newArrayList(values));
	}
	
	public RarityList(@NotNull Rarity rarity, @NotNull List<T> values) {
		this.rarity = rarity;
		this.values = Lists.newArrayList(values);
	}
	
	public static <T> @NotNull RarityList<T> of(@NotNull Rarity rarity, @NotNull List<T> values) {
		return new RarityList<>(rarity, values);
	}
	
	public static <T> @NotNull RarityList<T> copy(@NotNull RarityList<T> list) {
		return new RarityList<>(list.rarity, list.values);
	}
	
	public static <T> @NotNull Codec<RarityList<T>> codec(@NotNull Codec<T> codec) {
		return RecordCodecBuilder.create((instance) -> {
			return instance.group(Rarity.CODEC.fieldOf("rarity").forGetter((list) -> {
				return list.rarity;
			}), Codec.list(codec).fieldOf("values").forGetter((list) -> {
				return list.values;
			})).apply(instance, RarityList::new);
		});
	}
	
	public @NotNull Rarity getRarity() {
		return this.rarity;
	}
	
	public @NotNull List<T> getValues() {
		return this.values;
	}
	
	public int size() {
		return this.values.size();
	}
	
	public T get(int index) {
		return this.values.get(index);
	}
	
	public boolean add(T value) {
		return this.values.add(value);
	}
	
	public void addAll(@NotNull RarityList<T> list) {
		this.values.addAll(list.values);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof RarityList<?> list) {
			return this.rarity == list.rarity && this.values.equals(list.values);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "RarityList{rarity=" + this.rarity + ", values=" + this.values + "}";
	}
}
