package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSurviveBlockTags;
import net.luis.xsurvive.tag.XSurviveItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveItemTagsProvider extends ItemTagsProvider {

	public XSurviveItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.copy(XSurviveBlockTags.OCEAN_MONUMENT_BLOCKS, XSurviveItemTags.OCEAN_MONUMENT_BLOCKS);
	}

	@Override
	public String getName() {
		return "XSurvive Item Tags";
	}
	
}

