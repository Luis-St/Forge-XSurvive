package net.luis.xsurvive.dependency;

import net.luis.xsurvive.dependency.xbackpack.XBackpackCommonSetup;
import net.minecraftforge.fml.ModList;

/**
 *
 * @author Luis-St
 *
 */

public class DependencyCallWrapper {
	
	public static void wrapCommonSetup() {
		if (ModList.get().isLoaded("xbackpack")) {
			XBackpackCommonSetup.commonSetup();
		}
	}
}
