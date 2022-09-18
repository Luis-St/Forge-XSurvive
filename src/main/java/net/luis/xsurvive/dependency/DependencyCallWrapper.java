package net.luis.xsurvive.dependency;

import net.luis.xsurvive.dependency.xbackpack.XBackpackCommonSetup;
import net.luis.xsurvive.dependency.xores.XOresHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;

/**
 *
 * @author Luis-st
 *
 */

public class DependencyCallWrapper {
	
	public static void wrapCommonSetup() {
		if (ModList.get().isLoaded("xbackpack")) {
			XBackpackCommonSetup.commonSetup();
		}
	}
	
	public static int wrapBlockLevel(Block block) {
		if (ModList.get().isLoaded("xores")) {
			return XOresHelper.getLevelForBlock(block);
		}
		return -1;
	}
	
	public static int wrapToolLevel(ItemStack stack) {
		if (ModList.get().isLoaded("xores")) {
			return XOresHelper.getLevelForTool(stack);
		}
		return -1;
	}
	
}
