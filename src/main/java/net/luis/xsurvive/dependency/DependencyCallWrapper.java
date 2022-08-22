package net.luis.xsurvive.dependency;

import net.luis.xsurvive.dependency.xbackpack.XBackpackCommonSetup;
import net.luis.xsurvive.dependency.xores.XOresHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;

/**
 *
 * @author Luis-st
 *
 */

public class DependencyCallWrapper {
	
	public static void wrapperXBackpackCommonSetup() {
		if (ModList.get().isLoaded("xbackpack")) {
			XBackpackCommonSetup.commonSetup();
		}
	}
	
	public static int wrapperXOresBlockLevel(Block block) {
		if (ModList.get().isLoaded("xores")) {
			return XOresHelper.getLevelForBlock(block);
		}
		return -1;
	}
	
}
