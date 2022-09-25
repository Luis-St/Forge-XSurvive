package net.luis.xsurvive.data.provider.tag;

import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.BEEKEEPER;
import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.ENCHANTER;
import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.END_TRADER;
import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.LUMBERJACK;
import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.MINER;
import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.MOB_HUNTER;
import static net.luis.xsurvive.world.level.entity.ai.village.XSPoiTypes.NETHER_TRADER;

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

public class XSPoiTypeTagsProvider extends PoiTypeTagsProvider {

	public XSPoiTypeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags() {
		this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(BEEKEEPER.get(), ENCHANTER.get(), END_TRADER.get(), LUMBERJACK.get(), MINER.get(), MOB_HUNTER.get(), NETHER_TRADER.get());
		this.tag(PoiTypeTags.BEE_HOME).add(BEEKEEPER.get());
	}
	
	@Override
	public String getName() {
		return "XSurvive Poi Tags";
	}

}
