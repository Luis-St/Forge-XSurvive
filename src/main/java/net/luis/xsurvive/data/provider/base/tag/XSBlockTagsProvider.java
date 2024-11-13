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

package net.luis.xsurvive.data.provider.base.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSBlockTags;
import net.luis.xsurvive.world.level.block.XSBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Luis-St
 *
 */

public class XSBlockTagsProvider extends BlockTagsProvider {
	
	public XSBlockTagsProvider(@NotNull DataGenerator generator, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider, @NotNull ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.@NotNull Provider lookup) {
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(XSBlocks.SMELTING_FURNACE.get());
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(XSBlocks.HONEY_MELON.get());
		this.tag(BlockTags.FIRE).add(XSBlocks.MYSTIC_FIRE.get());
		this.tag(XSBlockTags.OCEAN_MONUMENT_BLOCKS).add(Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.SEA_LANTERN);
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Block Tags";
	}
}
