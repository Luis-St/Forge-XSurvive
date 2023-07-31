package net.luis.xsurvive.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 *
 * @author Luis-St
 *
 */

public class Util {
	
	public static <K, V> @NotNull List<V> shufflePreferred(@NotNull K firstKey, @NotNull Map<K, V> map) {
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
	
	public static <K, T, V> @NotNull Map<T, V> mapKey(@NotNull Map<K, V> map, @NotNull Function<K, T> function) {
		Map<T, V> mapped = Maps.newHashMap();
		for (Entry<K, V> entry : map.entrySet()) {
			mapped.put(function.apply(entry.getKey()), entry.getValue());
		}
		return mapped;
	}
}
