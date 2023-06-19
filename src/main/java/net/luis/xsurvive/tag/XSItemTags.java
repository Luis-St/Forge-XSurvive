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

public class XSItemTags {
	
	public static final TagKey<Item> OCEAN_MONUMENT_BLOCKS = bind(new ResourceLocation(XSurvive.MOD_ID, "ocean_monument_blocks"));
	public static final TagKey<Item> SUB_INGOTS = bind(new ResourceLocation(XSurvive.MOD_ID, "sub_ingots"));
	
	private static TagKey<Item> bind(ResourceLocation location) {
		return ItemTags.create(location);
	}
}
