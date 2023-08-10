package net.luis.xsurvive.jei;

import com.google.common.collect.Lists;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.item.crafting.XSRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

public class XSRecipes {
	
	public static @NotNull List<SmeltingRecipe> getSmeltingRecipes() {
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null) {
			return level.getRecipeManager().getAllRecipesFor(XSRecipeTypes.SMELTING.get());
		}
		return Lists.newArrayList();
	}
}
