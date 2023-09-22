package net.luis.xsurvive.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
	
	public static @NotNull List<Vec3> circlePoints(double radius, @NotNull Vec3 center, int numOfPoints) {
		List<Vec3> points = Lists.newArrayList();
		double slice = 2 * Math.PI / numOfPoints;
		double offset = Math.random() * (2 * Math.PI);
		for (int i = 0; i < numOfPoints; i++) {
			double angle = slice * i + offset;
			double newX = center.x() + radius * Math.cos(angle);
			double newZ = center.z() + radius * Math.sin(angle);
			points.add(new Vec3(newX, 0.00, newZ));
		}
		return points;
	}
	
	public static @NotNull Vec3 offsetPointCircular(double radius, @NotNull Vec3 center) {
		Random random = new Random();
		double angle = Math.toRadians(random.nextFloat() * 360);
		double newX = center.x() + radius * Math.cos(angle);
		double newZ = center.z() + radius * Math.sin(angle);
		return new Vec3(newX, 0.0, newZ);
	}
	
	public static @NotNull List<Integer> split(int value, int parts) {
		int partValue = value / parts;
		int remaining = value % parts;
		return IntStream.range(0, parts).map(i -> i < remaining ? partValue + 1 : partValue).boxed().collect(Collectors.toList());
	}
	
	public static int getSafeOrElseLast(int @NotNull [] array, int index, int fallback) {
		if (array.length == 0) {
			return fallback;
		}
		return array.length > index && index >= 0 ? array[index] : array[array.length - 1];
	}
	
	public static @NotNull Executor createWorkerExecutor(String threadPrefix) {
		ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory = new ForkJoinPool.ForkJoinWorkerThreadFactory() {
			private final AtomicInteger count = new AtomicInteger(0);
			
			@Override
			public @NotNull ForkJoinWorkerThread newThread(@NotNull ForkJoinPool pool) {
				ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
				worker.setName(threadPrefix + "-Thread-" + this.count.getAndIncrement());
				return worker;
			}
		};
		return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), threadFactory, null, false);
	}
}
