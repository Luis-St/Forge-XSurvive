package net.luis.xsurvive.dependency;

import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Luis-st
 *
 */

public class DependencyHelper {
	
	@Nullable
	public static Class<?> getDependencyClass(String className) {
		try {
			return Class.forName(className);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean isInstanceOf(String className, Object object) {
		try {
			return getDependencyClass(className).isInstance(object);
		} catch (Exception e) {
			
		}
		return false;
	}
	
}
