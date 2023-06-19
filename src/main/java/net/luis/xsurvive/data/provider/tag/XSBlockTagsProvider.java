package net.luis.xsurvive.data.provider.tag;

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
 * @author Luis-st
 *
 */

public class XSBlockTagsProvider extends BlockTagsProvider {
	
	public XSBlockTagsProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider) {
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
