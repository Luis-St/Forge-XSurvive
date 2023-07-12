package net.luis.xsurvive.util;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-st
 *
 */

public class RarityList<T> {
	
	private final Rarity rarity;
	private final List<T> values;
	
	public RarityList(Rarity rarity) {
		this(rarity, Lists.newArrayList());
	}
	
	@SafeVarargs
	public RarityList(Rarity rarity, T... values) {
		this(rarity, Lists.newArrayList(values));
	}
	
	public RarityList(Rarity rarity, List<T> values) {
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
