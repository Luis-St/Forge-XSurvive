package net.luis.xsurvive.tag;

import net.luis.xsurvive.XSurvive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * 
 * @author Luis-st
 *
 */

public class XSurviveItemTags {
	
	public static final TagKey<Item> OCEAN_MONUMENT_BLOCKS = bind(new ResourceLocation(XSurvive.MOD_ID, "ocean_monument_blocks"));
	
	private static TagKey<Item> bind(ResourceLocation location) {
		return ItemTags.create(location);
	}
	
}
