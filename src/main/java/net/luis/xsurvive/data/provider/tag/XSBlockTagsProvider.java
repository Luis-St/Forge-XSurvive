package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSBlockTags;
import net.luis.xsurvive.world.level.block.XSBlocks;
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

public class XSBlockTagsProvider extends BlockTagsProvider {

	public XSBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.tag(XSBlockTags.OCEAN_MONUMENT_BLOCKS).add(Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.SEA_LANTERN);
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(XSBlocks.SMELTING_FURNACE.get());
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(XSBlocks.HONEY_MELON.get());
	}
	
	@Override
	public String getName() {
		return "XSurvive Block Tags";
	}

}
