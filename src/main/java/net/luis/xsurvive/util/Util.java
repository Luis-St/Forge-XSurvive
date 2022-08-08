package net.luis.xsurvive.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

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
	
}
