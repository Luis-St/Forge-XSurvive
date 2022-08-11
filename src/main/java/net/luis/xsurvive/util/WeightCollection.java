package net.luis.xsurvive.util;

import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * 
 * @author Luis-st
 *
 */

public class WeightCollection<T> {
	
	private final NavigableMap<Integer, T> map;
	private final Random random;
	private int total = 0;
	
	public WeightCollection() {
		this(new Random());
	}
	
	public WeightCollection(Random random) {
		this.map = Maps.newTreeMap();
		this.random = random;
	}
	
	private WeightCollection(List<Pair<Integer, T>> list) {
		this.map = Maps.newTreeMap();
		this.random = new Random();
		list.forEach((pair) -> {
			this.add(pair.getFirst(), pair.getSecond());
		});
	}

	public void add(int weight, T value) {
		if (0 >= weight) {
			throw new IllegalArgumentException("The weight must be larger that 0 but it is " + weight);
		}
		this.total += weight;
		this.map.put(this.total, value);
	}
	
	public T next() {
		int value = (int) (this.random.nextDouble() * this.total);
		return this.map.higherEntry(value).getValue();
	}
	
	private List<Pair<Integer, T>> asList() {
		List<Pair<Integer, T>> list = Lists.newArrayList();
		for (Entry<Integer, T> entry : this.map.entrySet()) {
			list.add(Pair.of(entry.getKey(), entry.getValue()));
		}
		return list;
	}
	
	public static <E> Codec<WeightCollection<E>> codec(Codec<E> codec) {
		return RecordCodecBuilder.create((instance) -> {
			return instance.group(Codec.pair(Codec.INT, codec).listOf().fieldOf("weights").forGetter(WeightCollection::asList)).apply(instance, WeightCollection::new);
		});
	}
	
}
