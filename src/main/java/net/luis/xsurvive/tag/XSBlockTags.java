package net.luis.xsurvive.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Luis-St
 *
 */

public class XSBlockTags {
	
	public static void register() {
	
	}
	
	public static final TagKey<Block> OCEAN_MONUMENT_BLOCKS = bind(new ResourceLocation(XSurvive.MOD_ID, "ocean_monument_blocks"));
	
	private static @NotNull TagKey<Block> bind(@NotNull ResourceLocation location) {
		return BlockTags.create(location);
	}
}
