package net.luis.xsurvive.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * 
 * @author Luis-st
 *
 */

public class XSBlockTags {
	
	public static final TagKey<Block> OCEAN_MONUMENT_BLOCKS = bind(new ResourceLocation(XSurvive.MOD_ID, "ocean_monument_blocks"));
	
	private static TagKey<Block> bind(ResourceLocation location) {
		return BlockTags.create(location);
	}
	
}
