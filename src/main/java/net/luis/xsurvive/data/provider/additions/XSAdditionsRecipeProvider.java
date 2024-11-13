/*
 * XSurvive
 * Copyright (C) 2024 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.xsurvive.data.provider.additions;

import net.luis.xsurvive.data.provider.base.XSRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Luis-St
 *
 */

public class XSAdditionsRecipeProvider extends XSRecipeProvider {
	
	public XSAdditionsRecipeProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookup) {
		super(generator, lookup);
	}
	
	@Override
	protected void buildRecipes(@NotNull RecipeOutput output) {
		this.groupAndUnlock(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_INGOT).define('#', Items.NETHERITE_SCRAP).define('?', Items.GOLD_BLOCK).pattern("###").pattern("#?#").pattern("###"), getGroup(Items.NETHERITE_INGOT),
			Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT).save(output);
	}
}
