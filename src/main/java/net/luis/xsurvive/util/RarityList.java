package net.luis.xsurvive.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

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
	
	public static <T> RarityList<T> of(Rarity rarity, List<T> values) {
		return new RarityList<>(rarity, values);
	}
	
	public static <T> RarityList<T> copy(RarityList<T> list) {
		return new RarityList<>(list.rarity, list.values);
	}
	
	public Rarity getRarity() {
		return this.rarity;
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
	
	public boolean addAll(RarityList<T> list) {
		return this.values.addAll(list.values);
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
		StringBuilder builder = new StringBuilder();
		builder.append("RarityList{rarity=").append(this.rarity).append(", values=").append(this.values).append("}");
		return builder.toString();
	}
	
	public static <T> Codec<RarityList<T>> codec(Codec<T> codec) {
		return RecordCodecBuilder.create((instance) -> {
			return instance.group(Rarity.CODEC.fieldOf("rarity").forGetter((list) -> {
				return list.rarity;
			}), Codec.list(codec).fieldOf("values").forGetter((list) -> {
				return list.values;
			})).apply(instance, RarityList::new);
		});
	}
	
}
