package net.luis.xsurvive.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 *
 * @author Luis-st
 *
 */

public class Util {
	
	public static <K, V> List<V> shufflePreferred(K firstKey, Map<K, V> map) {
		List<V> values = Lists.newArrayList();
		V firstValue = map.get(firstKey);
		if (firstValue != null) {
			values.add(firstValue);
			map.remove(firstKey, firstValue);
		}
		List<V> remainingValues = Lists.newArrayList(map.values());
		Collections.shuffle(remainingValues);
		values.addAll(remainingValues);
		return values;
	}
	
	public static <K, T, V> Map<T, V> mapKey(Map<K, V> map, Function<K, T> function) {
		Map<T, V> mapped = Maps.newHashMap();
		for (Entry<K, V> entry : map.entrySet()) {
			mapped.put(function.apply(entry.getKey()), entry.getValue());
		}
		return mapped;
	}
	
}
