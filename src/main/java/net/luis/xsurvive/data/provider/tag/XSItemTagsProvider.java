package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSBlockTags;
import net.luis.xsurvive.tag.XSItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Luis-st
 *
 */

public class XSItemTagsProvider extends ItemTagsProvider {

	public XSItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(generator, blockTagsProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.copy(XSBlockTags.OCEAN_MONUMENT_BLOCKS, XSItemTags.OCEAN_MONUMENT_BLOCKS);
	}

	@Override
	public String getName() {
		return "XSurvive Item Tags";
	}
	
}
