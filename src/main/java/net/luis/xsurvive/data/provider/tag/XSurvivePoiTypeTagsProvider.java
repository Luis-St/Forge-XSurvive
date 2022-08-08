package net.luis.xsurvive.data.provider.tag;

import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.BEEKEEPER;
import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.ENCHANTER;
import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.END_TRADER;
import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.LUMBERJACK;
import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.MINER;
import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.MOB_HUNTER;
import static net.luis.xsurvive.world.level.entity.ai.village.poi.XSurvivePoiTypes.NETHER_TRADER;

import net.luis.xsurvive.XSurvive;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurvivePoiTypeTagsProvider extends PoiTypeTagsProvider {

	public XSurvivePoiTypeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(BEEKEEPER.get(), ENCHANTER.get(), END_TRADER.get(), LUMBERJACK.get(), MINER.get(), MOB_HUNTER.get(), NETHER_TRADER.get());
		this.tag(PoiTypeTags.BEE_HOME).add(BEEKEEPER.get());
	}

}
