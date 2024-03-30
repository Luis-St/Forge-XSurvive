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

package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Luis-St
 *
 */

public class XSBiomeTagsProvider extends BiomeTagsProvider {
	
	public XSBiomeTagsProvider(@NotNull DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider) {
		this.tag(XSBiomeTags.IS_MOUNTAIN).addTag(BiomeTags.IS_MOUNTAIN).add(Biomes.GROVE).add(Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_GRAVELLY_HILLS);
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Biome Tags";
	}
}
