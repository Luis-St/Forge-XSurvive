package net.luis.xsurvive.dependency.xbackpack;

import net.luis.xbackpack.BackpackConstants;
import net.luis.xbackpack.world.extension.BackpackExtensions;
import net.luis.xbackpack.world.inventory.extension.ExtensionMenuRegistry;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;

/**
 *
 * @author Luis-st
 *
 */

public class XBackpackCommonSetup {
	
	public static void commonSetup() {
		BackpackConstants.FURNACE_RECIPE_TYPES.add(XSRecipeTypes.SMELTING.get());
		ExtensionMenuRegistry.registerOverride(BackpackExtensions.ANVIL.get(), XSurvive.MOD_NAME, XSAnvilExtensionMenu::new);
	}
	
}
