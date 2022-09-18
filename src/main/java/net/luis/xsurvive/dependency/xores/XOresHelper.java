package net.luis.xsurvive.dependency.xores;

import net.luis.xores.world.fixer.ToolFixer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 *
 * @author Luis-st
 *
 */

public class XOresHelper {
	
	public static int getLevelForBlock(Block block) {
		return ToolFixer.INSTANCE.getLevelForBlock(block);
	}
	
	public static int getLevelForTool(ItemStack stack) {
		return ToolFixer.INSTANCE.getLevelForTool(stack.getItem());
	}
	
}
