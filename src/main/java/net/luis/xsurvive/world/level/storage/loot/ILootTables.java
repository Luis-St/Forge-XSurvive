package net.luis.xsurvive.world.level.storage.loot;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * 
 * @author Luis-st
 *
 */

public interface ILootTables {
	
	Map<ResourceLocation, LootTable> getTables();
	
	Map<ResourceLocation, JsonElement> getRawTables();
	
	JsonElement getRaw(ResourceLocation location);
	
	List<ResourceLocation> getTablesWith(ItemLike item);
	
}
