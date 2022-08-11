package net.luis.xsurvive.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.google.common.collect.Maps;
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
	
	private WeightCollection(Map<Integer, T> map) {
		this.map = new TreeMap<>();
		this.random = new Random();
		for (Entry<Integer, T> entry : map.entrySet()) {
			this.add(entry.getKey(), entry.getValue());
		}
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
	
	public static <E> Codec<WeightCollection<E>> codec(Codec<E> codec) {
		return RecordCodecBuilder.create((instance) -> {
			return instance.group(Codec.unboundedMap(Codec.INT, codec).fieldOf("weights").forGetter((collection) -> {
				return collection.map;
			})).apply(instance, WeightCollection::new);
		});
	}
	
}
