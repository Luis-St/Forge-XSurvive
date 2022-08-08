package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSurviveBlockTags;
import net.luis.xsurvive.world.level.block.XSurviveBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveBlockTagsProvider extends BlockTagsProvider {

	public XSurviveBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.tag(XSurviveBlockTags.OCEAN_MONUMENT_BLOCKS).add(Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.SEA_LANTERN);
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(XSurviveBlocks.SMELTING_FURNACE.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).add(XSurviveBlocks.HONEY_MELON.get());
	}
	
	@Override
	public String getName() {
		return "XSurvive Block Tags";
	}

}
