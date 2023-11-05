package net.luis.xsurvive.data.provider.recipe.additions;

import net.luis.xsurvive.data.provider.recipe.XSRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSAdditionsRecipeProvider extends XSRecipeProvider {
	
	public XSAdditionsRecipeProvider(@NotNull DataGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void buildRecipes(@NotNull RecipeOutput output) {
		
		
		
		this.groupAndUnlock(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_INGOT).define('#', Items.NETHERITE_SCRAP).define('?', Items.GOLD_BLOCK).pattern("###").pattern("#?#").pattern("###"), getGroup(Items.NETHERITE_INGOT),
			Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT).save(output);
	}
}
