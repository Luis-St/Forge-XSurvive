package net.luis.xsurvive.data.provider.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static net.luis.xsurvive.world.entity.ai.village.XSPoiTypes.*;

/**
 *
 * @author Luis-st
 *
 */

public class XSPoiTypeTagsProvider extends PoiTypeTagsProvider {
	
	public XSPoiTypeTagsProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), lookupProvider, XSurvive.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider) {
		this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(BEEKEEPER.getKey(), ENCHANTER.getKey(), END_TRADER.getKey(), LUMBERJACK.getKey(), MINER.getKey(), MOB_HUNTER.getKey(), NETHER_TRADER.getKey());
		this.tag(PoiTypeTags.BEE_HOME).add(Objects.requireNonNull(BEEKEEPER.getKey()));
	}
	
	@Override
	public @NotNull String getName() {
		return "XSurvive Poi Tags";
	}
}
