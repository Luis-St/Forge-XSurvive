package net.luis.xsurvive.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.*;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.luis.xsurvive.world.item.*;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 * @author Luis-St
 *
 */

@JeiPlugin
public class XSJeiPlugin implements IModPlugin {
	
	private final ResourceLocation pluginId;
	
	public XSJeiPlugin() {
		this.pluginId = new ResourceLocation(XSurvive.MOD_ID, "jei");
	}
	
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return this.pluginId;
	}
	
	@Override
	public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
		registration.useNbtForSubtypes(XSItems.ENCHANTED_GOLDEN_BOOK.get());
	}
	
	@Override
	public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SmeltingCategory(registration.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		registration.addRecipes(XSJeiRecipeTypes.SMELTING, XSRecipes.getSmeltingRecipes());
		this.registerGoldenBookAnvilRecipes(registration, registration.getVanillaRecipeFactory());
		this.registerRuneAnvilRecipes(registration, registration.getVanillaRecipeFactory());
	}
	
	private void registerGoldenBookAnvilRecipes(@NotNull IRecipeRegistration registration, @NotNull IVanillaRecipeFactory recipeFactory) {
		List<IJeiAnvilRecipe> recipes = Lists.newArrayList();
		for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS.getValues()) {
			if (enchantment instanceof IEnchantment ench) {
				if (ench.isAllowedOnGoldenBooks()) {
					for (ItemStack stack : XSEnchantmentHelper.getItemsForEnchantment(enchantment)) {
						if (ench.isUpgradeEnchantment()) {
							for (int i = ench.getMinUpgradeLevel(); i < ench.getMaxUpgradeLevel(); i++) {
								ItemStack enchantedStack = this.enchantItem(stack, enchantment, i);
								ItemStack goldenStack = this.enchantItem(stack, enchantment, i + 1);
								recipes.add(recipeFactory.createAnvilRecipe(enchantedStack, Lists.newArrayList(EnchantedGoldenBookItem.createForEnchantment(enchantment)), Lists.newArrayList(goldenStack)));
							}
						}
						ItemStack enchantedStack = this.enchantItem(stack, enchantment, enchantment.getMaxLevel());
						ItemStack goldenStack = this.enchantItem(stack, enchantment, ench.getMinGoldenBookLevel());
						recipes.add(recipeFactory.createAnvilRecipe(enchantedStack, Lists.newArrayList(EnchantedGoldenBookItem.createForEnchantment(enchantment)), Lists.newArrayList(goldenStack)));
					}
				}
			} else {
				XSurvive.LOGGER.error("Enchantment '{}' is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			}
		}
		registration.addRecipes(RecipeTypes.ANVIL, recipes);
	}
	
	private @NotNull ItemStack enchantItem(@NotNull ItemStack stack, @NotNull Enchantment enchantment, int level) {
		ItemStack copy = stack.copy();
		copy.removeTagKey("Enchantments");
		copy.removeTagKey("StoredEnchantments");
		copy.enchant(enchantment, level);
		return copy;
	}
	
	private void registerRuneAnvilRecipes(@NotNull IRecipeRegistration registration, @NotNull IVanillaRecipeFactory recipeFactory) {
		List<IJeiAnvilRecipe> recipes = Lists.newArrayList();
		for (GlintColorItem item : ItemHelper.getRunes()) {
			for (ItemStack stack : ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(ItemStack::isEnchantable).toList()) {
				if (!stack.is(Items.BOOK) && !stack.is(XSItems.ENCHANTED_GOLDEN_BOOK.get())) {
					ItemStack enchantedStack = this.enchantItemRandomly(stack);
					ItemStack glintColorStack = this.createResultForRune(enchantedStack, item.getGlintColor(new ItemStack(item)));
					recipes.add(recipeFactory.createAnvilRecipe(enchantedStack, Lists.newArrayList(new ItemStack(item)), Lists.newArrayList(glintColorStack)));
				}
			}
		}
		registration.addRecipes(RecipeTypes.ANVIL, recipes);
	}
	
	private @NotNull ItemStack enchantItemRandomly(@NotNull ItemStack stack) {
		ItemStack copy = stack.copy();
		copy.removeTagKey("Enchantments");
		copy.removeTagKey("StoredEnchantments");
		if (copy.isDamageableItem()) {
			copy.enchant(Enchantments.UNBREAKING, 3);
			return copy;
		}
		return EnchantmentHelper.enchantItem(RandomSource.create(), copy, 20, false);
	}
	
	private @NotNull ItemStack createResultForRune(@NotNull ItemStack stack, int color) {
		ItemStack result = stack.copy();
		result.setTag(IGlintColor.createGlintTag(result.getOrCreateTag(), color));
		return result;
	}
	
	@Override
	public void registerRecipeTransferHandlers(@NotNull IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(SmeltingFurnaceMenu.class, XSMenuTypes.SMELTING_FURNACE.get(), XSJeiRecipeTypes.SMELTING, 0, 1, 3, 36);
		registration.addRecipeTransferHandler(SmeltingFurnaceMenu.class, XSMenuTypes.SMELTING_FURNACE.get(), RecipeTypes.FUELING, 1, 1, 3, 36);
	}
	
	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(XSBlocks.SMELTING_FURNACE.get()), XSJeiRecipeTypes.SMELTING, RecipeTypes.FUELING);
	}
}
