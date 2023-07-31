package net.luis.xsurvive.jei;

import com.google.common.cache.*;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.luis.xsurvive.world.item.crafting.SmeltingRecipe;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static mezz.jei.api.recipe.RecipeIngredientRole.*;

/**
 *
 * @author Luis-St
 *
 */

public class SmeltingCategory implements IRecipeCategory<SmeltingRecipe> {
	
	private static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png");
	
	private final IDrawableAnimated animatedFlame;
	private final IDrawable background;
	private final IDrawable icon;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	
	public SmeltingCategory(@NotNull IGuiHelper guiHelper) {
		this.animatedFlame = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(RECIPE_GUI_VANILLA, 82, 114, 14, 14), 300, IDrawableAnimated.StartDirection.TOP, true);
		this.background = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 0, 114, 82, 54);
		this.icon = guiHelper.createDrawableItemStack(new ItemStack(XSBlocks.SMELTING_FURNACE.get()));
		this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>() {
			@Override
			public @NotNull IDrawableAnimated load(@NotNull Integer cookTime) {
				return guiHelper.drawableBuilder(RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}
	
	@Override
	public @NotNull RecipeType<SmeltingRecipe> getRecipeType() {
		return XSJeiRecipeTypes.SMELTING;
	}
	
	@Override
	public @NotNull Component getTitle() {
		return XSBlocks.SMELTING_FURNACE.get().getName();
	}
	
	@Override
	public @NotNull IDrawable getBackground() {
		return this.background;
	}
	
	@Override
	public @NotNull IDrawable getIcon() {
		return this.icon;
	}
	
	@Override
	public void draw(@NotNull SmeltingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
		this.animatedFlame.draw(graphics, 1, 20);
		IDrawableAnimated arrow = this.getArrow(recipe);
		arrow.draw(graphics, 24, 18);
		this.drawExperience(recipe, graphics);
		this.drawCookTime(recipe, graphics);
	}
	
	protected @NotNull IDrawableAnimated getArrow(@NotNull SmeltingRecipe recipe) {
		int cookTime = recipe.getCookingTime();
		if (cookTime <= 0) {
			cookTime = 100;
		}
		return this.cachedArrows.getUnchecked(cookTime);
	}
	
	protected void drawExperience(@NotNull SmeltingRecipe recipe, @NotNull GuiGraphics graphics) {
		float experience = recipe.getExperience();
		if (experience > 0) {
			Component component = Component.translatable("gui.jei.category.smelting.experience", experience);
			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			graphics.drawString(font, component, this.background.getWidth() - font.width(component), 0, 0xFF808080);
		}
	}
	
	protected void drawCookTime(@NotNull SmeltingRecipe recipe, @NotNull GuiGraphics graphics) {
		int cookTime = recipe.getCookingTime();
		if (cookTime > 0) {
			Component component = Component.translatable("gui.jei.category.smelting.time.seconds", (cookTime / 20));
			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			graphics.drawString(font, component, this.background.getWidth() - font.width(component), 45, 0xFF808080);
		}
	}
	
	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull SmeltingRecipe recipe, @NotNull IFocusGroup focuses) {
		builder.addSlot(INPUT, 1, 1).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(OUTPUT, 61, 19).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
	}
	
	@Override
	public boolean isHandled(@NotNull SmeltingRecipe recipe) {
		return !recipe.isSpecial();
	}
}
