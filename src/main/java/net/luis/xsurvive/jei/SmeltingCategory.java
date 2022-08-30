package net.luis.xsurvive.jei;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.ModIds;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 *
 * @author Luis-st
 *
 */

public class SmeltingCategory implements IRecipeCategory<SmeltingRecipe> {
	
	private static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png");
	
	private final IDrawableStatic staticFlame;
	private final IDrawableAnimated animatedFlame;
	private final IDrawable background;
	private final IDrawable icon;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	
	public SmeltingCategory(IGuiHelper guiHelper) {
		this.staticFlame = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		this.background = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 0, 114, 82, 54);
		this.icon = guiHelper.createDrawableItemStack(new ItemStack(XSBlocks.SMELTING_FURNACE.get()));
		this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>() {
			@Override
			public IDrawableAnimated load(Integer cookTime) {
				return guiHelper.drawableBuilder(RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}
	
	@Override
	public RecipeType<SmeltingRecipe> getRecipeType() {
		return XSJeiRecipeTypes.SMELTING;
	}

	@Override
	public Component getTitle() {
		return XSBlocks.SMELTING_FURNACE.get().getName();
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}
	
	@Override
	public void draw(SmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
		animatedFlame.draw(stack, 1, 20);
		IDrawableAnimated arrow = this.getArrow(recipe);
		arrow.draw(stack, 24, 18);
		this.drawExperience(recipe, stack, 0);
		this.drawCookTime(recipe, stack, 45);
	}
	
	protected IDrawableAnimated getArrow(SmeltingRecipe recipe) {
		int cookTime = recipe.getCookingTime();
		if (cookTime <= 0) {
			cookTime = 100;
		}
		return this.cachedArrows.getUnchecked(cookTime);
	}
	
	protected void drawExperience(SmeltingRecipe recipe, PoseStack stack, int y) {
		float experience = recipe.getExperience();
		if (experience > 0) {
			Component component = Component.translatable("gui.jei.category.smelting.experience", experience);
			Minecraft minecraft = Minecraft.getInstance();
			Font fontRenderer = minecraft.font;
			fontRenderer.draw(stack, component, this.background.getWidth() - fontRenderer.width(component), y, 0xFF808080);
		}
	}
	
	protected void drawCookTime(SmeltingRecipe recipe, PoseStack stack, int y) {
		int cookTime = recipe.getCookingTime();
		if (cookTime > 0) {
			Component component = Component.translatable("gui.jei.category.smelting.time.seconds", (cookTime / 20));
			Minecraft minecraft = Minecraft.getInstance();
			Font fontRenderer = minecraft.font;
			fontRenderer.draw(stack, component, this.background.getWidth() - fontRenderer.width(component), y, 0xFF808080);
		}
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SmeltingRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(INPUT, 1, 1).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(OUTPUT, 61, 19).addItemStack(recipe.getResultItem());
	}
	
	@Override
	public boolean isHandled(SmeltingRecipe recipe) {
		return !recipe.isSpecial();
	}
	
}
