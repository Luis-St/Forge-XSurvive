package net.luis.xsurvive.mixin;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import net.luis.xsurvive.world.level.storage.loot.ILootTables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 
 * @author Luis-st
 *
 */

@Mixin(LootTables.class)
public abstract class LootTablesMixin extends SimpleJsonResourceReloadListener implements ILootTables {
	
	@Shadow
	private Map<ResourceLocation, LootTable> tables;
	private Map<ResourceLocation, JsonElement> rawTables = ImmutableMap.of();
	
	private LootTablesMixin(Gson gson, String directory) {
		super(gson, directory);
	}

	@Inject(method = "apply", at = @At("HEAD"))
	protected void apply(Map<ResourceLocation, JsonElement> lootTables, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo callback) {
		this.rawTables = ImmutableMap.copyOf(lootTables);
	}

	@Override
	public Map<ResourceLocation, LootTable> getTables() {
		return this.tables;
	}

	@Override
	public Map<ResourceLocation, JsonElement> getRawTables() {
		return this.rawTables;
	}

	@Override
	public JsonElement getRaw(ResourceLocation location) {
		return this.rawTables.getOrDefault(location, JsonNull.INSTANCE);
	}

	@Override
	public List<ResourceLocation> getTablesWith(ItemLike item) {
		String itemEntry = "\"name\":\"" + ForgeRegistries.ITEMS.getKey(item.asItem()) + "\"";
		List<ResourceLocation> tables = Lists.newArrayList();
		for (Entry<ResourceLocation, JsonElement> rawTable : this.rawTables.entrySet()) {
			if (rawTable.getValue().toString().contains(itemEntry)) {
				tables.add(rawTable.getKey());
			}
		}
		return tables;
	}
	
}
