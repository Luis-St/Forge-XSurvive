package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.tag.XSBiomeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Luis-st
 *
 */

public class XSBiomeTagsProvider extends BiomeTagsProvider {
	
	public XSBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.tag(XSBiomeTags.IS_MOUNTAIN).addTag(BiomeTags.IS_MOUNTAIN).add(Biomes.GROVE).add(Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_GRAVELLY_HILLS);
	}
	
	@Override
	public String getName() {
		return "XSurvive Biome Tags";
	}
	
}
