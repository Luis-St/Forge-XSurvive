package net.luis.xsurvive.world.level.storage.loot;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.luis.xsurvive.XSurvive;
import net.luis.xsurvive.util.Chance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * 
 * @author Luis-st
 *
 */

public class AdditionalChanceItemModifier extends LootModifier {
	
	public static final Codec<AdditionalChanceItemModifier> CODEC = RecordCodecBuilder.create((instance) -> {
		return LootModifier.codecStart(instance).and(instance.group(ForgeRegistries.ITEMS.getCodec().fieldOf("table_item").forGetter((modifier) -> {
			return modifier.tableItem;
		}), ForgeRegistries.ITEMS.getCodec().fieldOf("additional_item").forGetter((modifier) -> {
			return modifier.additionalItem;
		}), Chance.CODEC.fieldOf("chance").forGetter((modifier) -> {
			return modifier.chance;
		}))).apply(instance, AdditionalChanceItemModifier::new);
	});
	
	private final Item tableItem;
	private final Item additionalItem;
	private final Chance chance;
	
	public AdditionalChanceItemModifier(LootItemCondition[] conditions, Item tableItem, Item additionalItem, Chance chance) {
		super(conditions);
		this.tableItem = tableItem;
		this.additionalItem = additionalItem;
		this.chance = chance;
	}
	
	@Override
	public Codec<AdditionalChanceItemModifier> codec() {
		return XSGlobalLootModifiers.ADDITIONAL_CHANCE_ITEM_MODIFIER.get();
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server != null) {
			if (server.getLootTables() instanceof ILootTables lootTables) {
				List<ResourceLocation> tables = lootTables.getTablesWith(this.tableItem);
				if (tables.contains(context.getQueriedLootTableId()) && this.chance.result()) {
					generatedLoot.add(new ItemStack(this.additionalItem));
				}
			} else {
				XSurvive.LOGGER.error("LootTables is not a instance of ILootTables");
				XSurvive.LOGGER.info("Additional Item {} will not be generated in any LootTable", ForgeRegistries.ITEMS.getKey(this.additionalItem));
			}
		}
		return generatedLoot;
	}
	
}
