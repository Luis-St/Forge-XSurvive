package net.luis.xsurvive.world.item.crafting;

import net.luis.xsurvive.XSurvive;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraftforge.registries.*;

/**
 *
 * @author Luis-St
 *
 */

@SuppressWarnings("CodeBlock2Expr")
public class XSRecipeSerializers {
	
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, XSurvive.MOD_ID);
	
	public static final RegistryObject<SimpleCookingSerializer<SmeltingRecipe>> SMELTING_RECIPE = RECIPE_SERIALIZERS.register("xsurvive_smelting", () -> {
		return new SimpleCookingSerializer<>(SmeltingRecipe::new, 100);
	});
}
