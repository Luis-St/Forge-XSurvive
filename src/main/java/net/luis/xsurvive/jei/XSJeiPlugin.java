package net.luis.xsurvive.jei;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.world.inventory.SmeltingFurnaceMenu;
import net.luis.xsurvive.world.inventory.XSMenuTypes;
import net.luis.xsurvive.world.item.EnchantedGoldenBookItem;
import net.luis.xsurvive.world.item.GlintColorItem;
import net.luis.xsurvive.world.item.ItemHelper;
import net.luis.xsurvive.world.item.XSItems;
import net.luis.xsurvive.world.item.enchantment.IEnchantment;
import net.luis.xsurvive.world.item.enchantment.XSEnchantmentHelper;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;

/**
 *
 * @author Luis-st
 *
 */

@JeiPlugin
public class XSJeiPlugin implements IModPlugin {
	
	private final ResourceLocation pluginId;
	private SmeltingCategory smeltingCategory;
	
	public XSJeiPlugin() {
		this.pluginId = new ResourceLocation(XSurvive.MOD_ID, "jei");
	}
	
	@Override
	public ResourceLocation getPluginUid() {
		return this.pluginId;
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.useNbtForSubtypes(XSItems.ENCHANTED_GOLDEN_BOOK.get());
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(this.smeltingCategory = new SmeltingCategory(registration.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		XSRecipes recipes = new XSRecipes();
		registration.addRecipes(XSJeiRecipeTypes.SMELTING, recipes.getSmeltingRecipes(this.smeltingCategory));
		registerGoldenBookAnvilRecipes(registration, registration.getVanillaRecipeFactory());
		registerRuneAnvilRecipes(registration, registration.getVanillaRecipeFactory());
	}
	
	private void registerGoldenBookAnvilRecipes(IRecipeRegistration registration, IVanillaRecipeFactory recipeFactory) {
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
				XSurvive.LOGGER.error("Enchantment {} is not a instance of IEnchantment", ForgeRegistries.ENCHANTMENTS.getKey(enchantment));
			}
		}
		registration.addRecipes(RecipeTypes.ANVIL, recipes);
	}
	
	private ItemStack enchantItem(ItemStack stack, Enchantment enchantment, int level) {
		ItemStack copy = stack.copy();
		copy.removeTagKey("Enchantments");
		copy.removeTagKey("StoredEnchantments");
		copy.enchant(enchantment, level);
		return copy;
	}
	
	private void registerRuneAnvilRecipes(IRecipeRegistration registration, IVanillaRecipeFactory recipeFactory) {
		List<IJeiAnvilRecipe> recipes = Lists.newArrayList();
		for (GlintColorItem item : ItemHelper.getRunes()) {
			for (ItemStack stack : ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(ItemStack::isEnchantable).collect(Collectors.toList())) {
				if (!stack.is(Items.BOOK) && !stack.is(XSItems.ENCHANTED_GOLDEN_BOOK.get())) {
					ItemStack enchantedStack = this.enchantItemRandomly(stack);
					ItemStack glintColorStack = this.createResultForRune(enchantedStack, item.getGlintColor(new ItemStack(item)));
					recipes.add(recipeFactory.createAnvilRecipe(enchantedStack, Lists.newArrayList(new ItemStack(item)), Lists.newArrayList(glintColorStack)));
				}
			}
		}
		registration.addRecipes(RecipeTypes.ANVIL, recipes);
	}
	
	private ItemStack enchantItemRandomly(ItemStack stack) {
		ItemStack copy = stack.copy();
		copy.removeTagKey("Enchantments");
		copy.removeTagKey("StoredEnchantments");
		if (copy.isDamageableItem()) {
			copy.enchant(Enchantments.UNBREAKING, 3);
			return copy;
		}
		EnchantmentHelper.enchantItem(RandomSource.create(), copy, 20, false);
		return copy;
	}
	
	private ItemStack createResultForRune(ItemStack stack, int color) {
		ItemStack result = stack.copy();
		CompoundTag tag = result.getOrCreateTag();
		if (tag.contains(XSurvive.MOD_NAME)) {
			CompoundTag modTag = tag.getCompound(XSurvive.MOD_NAME);
			tag.remove(XSurvive.MOD_NAME);
			modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
			tag.put(XSurvive.MOD_NAME, modTag);
		} else {
			CompoundTag modTag = new CompoundTag();
			modTag.putInt(XSurvive.MOD_NAME + "GlintColor", color);
			tag.put(XSurvive.MOD_NAME, modTag);
		}
		result.setTag(tag);
		return result;
	}
	
	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(SmeltingFurnaceMenu.class, XSMenuTypes.SMELTING_FURNACE.get(), XSJeiRecipeTypes.SMELTING, 0, 1, 3, 36);
		registration.addRecipeTransferHandler(SmeltingFurnaceMenu.class, XSMenuTypes.SMELTING_FURNACE.get(), RecipeTypes.FUELING, 1, 1, 3, 36);
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(XSBlocks.SMELTING_FURNACE.get()), XSJeiRecipeTypes.SMELTING, RecipeTypes.FUELING);
	}
	
}
